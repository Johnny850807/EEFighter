package controller.p2p;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import controller.EEFighter;
import model.packet.EEPacket;
import model.packet.Packets;
import model.packet.dto.CheckAnswerRequest;
import model.packet.dto.MovementRequest;
import model.packet.dto.PickUpRequest;
import model.packet.dto.PlayerReadyRequest;
import model.packet.dto.ThrowLastestWordRequest;

import static model.packet.Packets.PacketIds.*;
import model.sprite.GameMap;
import model.sprite.PlayerSprite;
import model.sprite.Sprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import ui.GameView;

/**
 * @author Waterball
 */
public class EEFighterP2PClient implements EEFighter{
	public static byte clientPlayerNo = 2;  // client's always the player 2
	private Executor executor = Executors.newScheduledThreadPool(18);
	private GameView gameView;
	private GameMap gameMap;
	private List<Sprite> letterSprites;
	private PlayerSprite player1Sprite;
	private PlayerSprite player2Sprite;
	
	private Socket socket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private boolean gameStopped = true;
	private HashMap<Byte, Consumer<DataInputStream>> packetHandler = new HashMap<>();
	
	
	public EEFighterP2PClient(Socket socket) {
		try {
			this.socket = socket;
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
			createThreadForlisteningOnInputStream();
			prepareAllPacketHandlers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createThreadForlisteningOnInputStream(){
		new Thread(()->{
			while (!gameStopped)
			{
				try {
					int packedId = inputStream.readInt();
					packetHandler.get(packedId).accept(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void prepareAllPacketHandlers(){
		packetHandler.put(PID_EV_GAME_START, (in) -> handleGameStarted(in));
		packetHandler.put(PID_EV_NEXT_QUESTION, (in) -> handleNextQuestion(in));
		packetHandler.put(PID_EV_POSITION_UPDATED, (in) -> handleModelPositionUpdated(in));
		packetHandler.put(PID_EV_PLAYER_LETTERS_UPDATED, (in) -> handlePlayerLettersUdpated(in));
		packetHandler.put(PID_EV_PLAY_VOICE, (in) -> handlePlayWordVoice(in));
		packetHandler.put(PID_EV_ANSWER_COMMIT_RESULT, (in) -> handleAnswerCommitResult(in));
		packetHandler.put(PID_EV_GAME_OVER, (in) -> handleGameOver(in));
	}
	
	private void handleGameStarted(DataInputStream inputStream){
		
	}
	
	private void handleNextQuestion(DataInputStream inputStream){
		
	}
	
	private void handleModelPositionUpdated(DataInputStream inputStream){
		
	}
	
	private void handlePlayerLettersUdpated(DataInputStream inputStream){
		
	}
	
	private void handlePlayWordVoice(DataInputStream inputStream){
		
	}
	
	private void handlePlayerLettersUpdated(DataInputStream inputStream){
		
	}
	
	private void handleAnswerCommitResult(DataInputStream inputStream){
		
	}

	private void handleGameOver(DataInputStream inputStream){
		
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
		EEPacket packet = Packets.parse(new PlayerReadyRequest(clientPlayerNo));
		sendBytes(packet.getBytes());
	}

	@Override
	public void move(PlayerSprite player, Direction direction, Direction imgDirection, Status status) {
		EEPacket packet = Packets.parse(new MovementRequest(clientPlayerNo, direction, status));
		sendBytes(packet.getBytes());
	}

	@Override
	public void nextQuestion() {		
		//There is No need to ask for the next question by client
	}

	@Override
	public void popLetter(PlayerSprite player) {
		EEPacket packet = Packets.parse(new ThrowLastestWordRequest(clientPlayerNo));
		sendBytes(packet.getBytes());
	}

	@Override
	public void pickUp(PlayerSprite player) {
		EEPacket packet = Packets.parse(new PickUpRequest(clientPlayerNo));
		sendBytes(packet.getBytes());
	}

	@Override
	public void checkAnswer(PlayerSprite player) {
		EEPacket packet = Packets.parse(new CheckAnswerRequest(clientPlayerNo));
		sendBytes(packet.getBytes());
	}

	private void sendBytes(byte[] bytes){ 
		executor.execute(()->{
			try{
				outputStream.write(bytes);
				outputStream.flush();
			}catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public void closeGame() {
		try {
			socket.close();
			gameStopped = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isGameClosed() {
		return gameStopped;
	}
	
	public int getPlayerNo(PlayerSprite playerSprite){
		return playerSprite == player1Sprite ? 1 : 2;
	}
}
