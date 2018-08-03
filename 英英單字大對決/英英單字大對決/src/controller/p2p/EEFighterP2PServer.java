package controller.p2p;

import static model.packet.Packets.PacketIds.PID_REQ_CHECK_ANSWER;
import static model.packet.Packets.PacketIds.PID_REQ_MOVEMENT;
import static model.packet.Packets.PacketIds.PID_REQ_PICK_UP;
import static model.packet.Packets.PacketIds.PID_REQ_PLAYER_READY;
import static model.packet.Packets.PacketIds.PID_REQ_THROW_LATEST_WORD;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import controller.EEFighterImp;
import factory.ComponentAbstractFactory;
import model.Question;
import model.packet.EEPacket;
import model.packet.Packets;
import model.packet.dto.AnswerCommitResultEvent;
import model.packet.dto.CheckAnswerRequest;
import model.packet.dto.GameOverEvent;
import model.packet.dto.GameStartEvent;
import model.packet.dto.Letter;
import model.packet.dto.LettersUpdatedEvent;
import model.packet.dto.MovementRequest;
import model.packet.dto.NextQuestionEvent;
import model.packet.dto.PickUpRequest;
import model.packet.dto.PlayVoiceEvent;
import model.packet.dto.PlayerLettersUpdatedEvent;
import model.packet.dto.PlayerReadyRequest;
import model.packet.dto.PlayerUpdatedEvent;
import model.packet.dto.ThrowLastestWordRequest;
import model.sprite.GameMap;
import model.sprite.PlayerSprite;
import model.sprite.Sprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import ui.GameView;

public class EEFighterP2PServer extends EEFighterImp{
	public static final int PORT = 11111;
	public static final byte serverPlayerNo = 1;  // server's always the player 1
	private Executor threadpool = Executors.newFixedThreadPool(30);
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private GameView clientObserver;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Map<Byte, Consumer<DataInputStream>> packetHandlers = new HashMap<>();
	
	public EEFighterP2PServer(ServerSocket serverSocket, 
			Socket clientSocket, ComponentAbstractFactory componentAbstractFactory) {
		super(componentAbstractFactory);
		this.serverSocket = serverSocket;
		this.clientSocket = clientSocket;
		this.clientObserver = new ClientObserver();
		initIoStreams();
		initRequestHandlers();
		listenToClientInput();
	}

	private void initIoStreams() {
		try {
			inputStream = new DataInputStream(clientSocket.getInputStream());
			outputStream = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initRequestHandlers() {
		packetHandlers.put(PID_REQ_PLAYER_READY, (in)-> handlePlayerReadyRequest(in));
		packetHandlers.put(PID_REQ_CHECK_ANSWER, (in)-> handleCheckAnswerRequest(in));
		packetHandlers.put(PID_REQ_MOVEMENT, (in)-> handleMovementRequest(in));
		packetHandlers.put(PID_REQ_PICK_UP, (in)-> handlePickUpLetterRequest(in));
		packetHandlers.put(PID_REQ_THROW_LATEST_WORD, (in)-> handleThrowLatestLetterRequest(in));
	}

	private void handlePlayerReadyRequest(DataInputStream inputStream){
		PlayerReadyRequest playerReadyRequest = Packets.parsePlayerReadyRequest(inputStream);
		startGame();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void handleMovementRequest(DataInputStream inputStream){
		MovementRequest movementRequest = Packets.parseMovementRequest(inputStream);
		this.move(player2, movementRequest.direction, movementRequest.status);
	}
	
	private void handlePickUpLetterRequest(DataInputStream inputStream){
		PickUpRequest pickUpRequest = Packets.parsePickUpRequest(inputStream);
		this.pickUp(player2);
	}

	private void handleThrowLatestLetterRequest(DataInputStream inputStream){
		ThrowLastestWordRequest throwLastestWordRequest = Packets.parseThrowLastestWordRequest(inputStream);
		this.popLetter(player2);
	}
	
	private void handleCheckAnswerRequest(DataInputStream inputStream){
		CheckAnswerRequest checkAnswerRequest = Packets.parseCheckAnswerRequest(inputStream);
		this.checkAnswer(player2);
	}
	
	private void listenToClientInput(){
		new Thread(()->{
			while(!isGameClosed())
			{
				try {
					byte requestId = inputStream.readByte();
					packetHandlers.get(requestId).accept(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void notifyGameViews(Consumer<GameView> notifyAction) {
		//notify remote client asynchronously first followed by updating UI
		threadpool.execute(()-> notifyAction.accept(clientObserver)); 
		notifyAction.accept(gameView);
	}
	
	private void sendPacket(EEPacket packet){
		try {
			outputStream.write(packet.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public byte getPlayerNo(PlayerSprite playerSprite){
		return playerSprite == player1 ? (byte)1 : (byte)2; 
	}
	
	private class ClientObserver implements GameView{

		@Override
		public void onDraw(GameMap gameMap, List<Sprite> letters, PlayerSprite player1, PlayerSprite player2) {
			LettersUpdatedEvent lettersUpdatedEvent = new LettersUpdatedEvent(letterSpritesToLetterArray(letters));
			sendPacket(Packets.parse(lettersUpdatedEvent));
		}

		@Override
		public void onGameStarted() {
			GameStartEvent gameStartEvent = new GameStartEvent(gameMap.toSpriteNameMatrix(), 
					player1.getPoint(), player2.getPoint());
			sendPacket(Packets.parse(gameStartEvent));
		}

		@Override
		public void onGameOver(PlayerSprite player) {
			GameOverEvent gameOverEvent = new GameOverEvent(getPlayerNo(player), 
					(byte)player1.getScore(), (byte)player2.getScore());
			sendPacket(Packets.parse(gameOverEvent));
		}

		@Override
		public void onGameClose() {
			try {
				clientSocket.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}

		@Override
		public void start() {
			// not invoked
		}

		@Override
		public void onMovedSuccessfuly(Sprite sprite, Direction direction, Status status) {
			PlayerSprite playerSprite = (PlayerSprite) sprite;
			PlayerUpdatedEvent playerUpdatedEvent = new PlayerUpdatedEvent(getPlayerNo(playerSprite), playerSprite.toPlayer());
			sendPacket(Packets.parse(playerUpdatedEvent));
		}

		@Override
		public void onHitWall(Sprite sprite) {}

		@Override
		public void onNextQuestion(Question question) {
			NextQuestionEvent nextQuestionEvent = new NextQuestionEvent(question);
			sendPacket(Packets.parse(nextQuestionEvent));
		}

		@Override
		public void onNoMoreQuestion() {}

		@Override
		public void onLetterPoppedSuccessfuly(PlayerSprite player, List<Sprite> letters) {
			sendPlayerLettersUpdatedEvent(player, letters);
		}
		
		@Override
		public void onLetterPoppedFailed(PlayerSprite player) {}

		@Override
		public void onLetterGotten(PlayerSprite player, List<Sprite> letters) {
			sendPlayerLettersUpdatedEvent(player, letters);
		}

		@Override
		public void onNoLetterGotten(PlayerSprite player, List<Sprite> letter) {
			// TODO Auto-generated method stub
			
		}

		private void sendPlayerLettersUpdatedEvent(PlayerSprite playerSprite, List<Sprite> letters){
			PlayerLettersUpdatedEvent playerLettersUpdatedEvent = new PlayerLettersUpdatedEvent(
					getPlayerNo(playerSprite), letterSpritesToLetterArray(letters));
			sendPacket(Packets.parse(playerLettersUpdatedEvent));
		}
		
		@Override
		public void onAnswerCorrect(PlayerSprite player) {
			AnswerCommitResultEvent answerCommitResultEvent = new AnswerCommitResultEvent(getPlayerNo(player), true);
			sendPacket(Packets.parse(answerCommitResultEvent));
		}

		@Override
		public void onAnswerWrong(PlayerSprite player) {
			AnswerCommitResultEvent answerCommitResultEvent = new AnswerCommitResultEvent(getPlayerNo(player), false);
			sendPacket(Packets.parse(answerCommitResultEvent));
		}

		@Override
		public void onQuestionWordSoundPlay(String word, String soundPath) {
			PlayVoiceEvent playVoiceEvent = new PlayVoiceEvent(word, soundPath);
			sendPacket(Packets.parse(playVoiceEvent));
		}
		
		private Letter[] letterSpritesToLetterArray(List<Sprite> letters){
			return letters.stream()
					.map(sprite -> new Letter(sprite.getPoint(), sprite.getSpriteName()))
					.toArray(Letter[]::new);
		}
	}
}
