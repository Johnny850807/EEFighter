package controller.p2p;

import static model.packet.Packets.PacketIds.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import controller.EEFighter;
import factory.ComponentAbstractFactory;
import model.packet.EEPacket;
import model.packet.Packets;
import model.packet.dto.*;
import model.sprite.GameMap;
import model.sprite.PlayerSprite;
import model.sprite.Sprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import model.sprite.SpriteName;
import model.sprite.SpriteNameMatrixMapDirector;
import model.sprite.SpritePrototypeFactory;
import ui.GameView;

/**
 * @author Waterball
 */
public class EEFighterP2PClient implements EEFighter{
	public static byte clientPlayerNo = 2;  // client's always the player 2
	private ComponentAbstractFactory abstractFactory;
	private Executor executor = Executors.newScheduledThreadPool(18);
	private GameView gameView;
	private GameMap gameMap;
	private List<Sprite> letterSprites;
	private PlayerSprite player1Sprite;
	private PlayerSprite player2Sprite;
	
	private Socket socket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private HashMap<Byte, Consumer<DataInputStream>> packetHandler = new HashMap<>();
	
	
	public EEFighterP2PClient(Socket socket, ComponentAbstractFactory abstractFactory) {
		try {
			this.socket = socket;
			this.abstractFactory = abstractFactory;
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
			prepareAllPacketHandlers();
			createThreadForlisteningOnInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void prepareAllPacketHandlers(){
		packetHandler.put(PID_EV_GAME_START, (in) -> handleGameStarted(in));
		packetHandler.put(PID_EV_NEXT_QUESTION, (in) -> handleNextQuestion(in));
		packetHandler.put(PID_EV_PLAYER_UPDATED, (in) -> handlePlayerUpdated(in));
		packetHandler.put(PID_EV_LETTERS_PLACED_UPDATED, (in) -> handleLettersPlacedUpdated(in));
		packetHandler.put(PID_EV_PLAYER_LETTERS_UPDATED, (in) -> handlePlayerLettersUpdated(in));
		packetHandler.put(PID_EV_PLAY_VOICE, (in) -> handlePlayWordVoice(in));
		packetHandler.put(PID_EV_ANSWER_COMMIT_RESULT, (in) -> handleAnswerCommitResult(in));
		packetHandler.put(PID_EV_GAME_OVER, (in) -> handleGameOver(in));
	}
	
	private void createThreadForlisteningOnInputStream(){
		new Thread(()->{
			while (socket.isConnected())
			{
				try {
					byte packedId = inputStream.readByte();
					packetHandler.get(packedId).accept(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void handleGameStarted(DataInputStream inputStream){
		gameView.onGameStarted();
	
		GameStartEvent gameStartEvent = Packets.parseGameStartEvent(inputStream);
		gameMap = new SpriteNameMatrixMapDirector(abstractFactory.createMapBuilder(), 
				gameStartEvent.spriteNameMapMatrix).buildMap();
		player1Sprite = (PlayerSprite) SpritePrototypeFactory.getInstance().createSprite(SpriteName.PLAYER1);
		player2Sprite =  (PlayerSprite) SpritePrototypeFactory.getInstance().createSprite(SpriteName.PLAYER2);
		
		player1Sprite.setXY(gameStartEvent.player1Point);
		player2Sprite.setXY(gameStartEvent.player2Point);

		gameView.onDraw(gameMap, Collections.EMPTY_LIST, player1Sprite, player2Sprite);
	}
	
	private void handleNextQuestion(DataInputStream inputStream){
		NextQuestionEvent nextQuestionEvent = Packets.parseNextQuestionEvent(inputStream);
		gameView.onNextQuestion(nextQuestionEvent.question);
	}
	
	private void handlePlayerUpdated(DataInputStream inputStream){
		PlayerUpdatedEvent playerUpdatedEvent = Packets.parsePlayerUpdatedEvent(inputStream);
		PlayerSprite playerSprite = getPlayerSprite(playerUpdatedEvent.playerNo);
		playerSprite.setXY(playerUpdatedEvent.player.point);
		playerSprite.setDirection(playerUpdatedEvent.player.direction);
		playerSprite.setStatus(playerUpdatedEvent.player.status);
	}

	private void handleLettersPlacedUpdated(DataInputStream inputStream){
		LettersUpdatedEvent spritesUpdatedEvent = Packets.parseLettersUpdatedEvent(inputStream);
		this.letterSprites = SpritePrototypeFactory.getInstance()
				.createLetters(spritesUpdatedEvent.letters);
		gameView.onDraw(gameMap, letterSprites, player1Sprite, player2Sprite);
	}
	
	private void handlePlayerLettersUpdated(DataInputStream inputStream){
		PlayerLettersUpdatedEvent playerLettersUpdatedEvent = Packets.parsePlayerLettersUpdatedEvent(inputStream);
		PlayerSprite playerSprite = getPlayerSprite(playerLettersUpdatedEvent.playerNo);
		playerSprite.setLetters(SpritePrototypeFactory.getInstance()
				.createLetters(playerLettersUpdatedEvent.letters));
		gameView.onLetterGotten(playerSprite, playerSprite.getLetters());
	}
	
	private void handlePlayWordVoice(DataInputStream inputStream){
		PlayVoiceEvent playVoiceEvent = Packets.parseEventPlayWordVoice(inputStream);
		gameView.onQuestionWordSoundPlay(playVoiceEvent.word, playVoiceEvent.soundPath);
	}
	
	
	private void handleAnswerCommitResult(DataInputStream inputStream){
		AnswerCommitResultEvent answerCommitResultEvent = Packets.parseAnswerCommitResultEvent(inputStream);
		PlayerSprite playerSprite = getPlayerSprite(answerCommitResultEvent.playerNo);
		if (answerCommitResultEvent.answerCorrect)
			gameView.onAnswerCorrect(playerSprite);
		else
			gameView.onAnswerWrong(playerSprite);
	}

	private void handleGameOver(DataInputStream inputStream){
		GameOverEvent gameOverEvent = Packets.parseGameOverEvent(inputStream);
		PlayerSprite winnerSprite = getPlayerSprite(gameOverEvent.winnerNo);
		gameView.onGameOver(winnerSprite);
	}
	
	
	@Override
	public void setGameView(GameView gameView) {
		this.gameView = gameView;		
	}

	/**
	 * In P2P Client, startGame() will notify the server that 'the client is ready for starting game.'.
	 */
	@Override
	public void startGame() {
		executor.execute(()->{
			EEPacket packet = Packets.parse(new PlayerReadyRequest(clientPlayerNo));
			sendBytes(packet.getBytes());
		});
	}

	@Override
	public void move(PlayerSprite player, Direction direction, Status status) {
		executor.execute(()->{
			EEPacket packet = Packets.parse(new MovementRequest(clientPlayerNo, direction, status));
			sendBytes(packet.getBytes());
		});
	}

	@Override
	public void nextQuestion() {		
		//There is No need to ask for the next question by client
	}

	@Override
	public void popLetter(PlayerSprite player) {
		executor.execute(()->{
			EEPacket packet = Packets.parse(new ThrowLastestWordRequest(clientPlayerNo));
			sendBytes(packet.getBytes());
		});
	}

	@Override
	public void pickUp(PlayerSprite player) {
		executor.execute(()->{
			EEPacket packet = Packets.parse(new PickUpRequest(clientPlayerNo));
			sendBytes(packet.getBytes());
		});
	}

	@Override
	public void checkAnswer(PlayerSprite player) {
		executor.execute(()->{
			EEPacket packet = Packets.parse(new CheckAnswerRequest(clientPlayerNo));
			sendBytes(packet.getBytes());
		});
	}

	private void sendBytes(byte[] bytes){ 
		try{
			outputStream.write(bytes);
			outputStream.flush();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void closeGame() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isGameClosed() {
		return socket.isClosed();
	}
	
	public PlayerSprite getPlayerSprite(byte playerNo){
		return playerNo == (byte)1 ? player1Sprite : player2Sprite;
	}
	
	public int getPlayerNo(PlayerSprite playerSprite){
		return playerSprite == player1Sprite ? 1 : 2;
	}
}
