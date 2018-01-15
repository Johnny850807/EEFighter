package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.sprite.PlayerSprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import ui.GameView;

public class EEFighterSocketServerProxy implements EEFighter {
	public static final int SERVER_PORT = 5678;
	private EEFighter eefighterController;
	private ServerSocket serverSocket;
	private Socket opponentClient;
	private ProtocolMappingExecutor protocolMappingExecutor;
	private DataOutputStream clientOutput;
	private DataInputStream clientInput;
	private InputListenerWorker inputListenerWorker = new InputListenerWorker();
	
	public EEFighterSocketServerProxy(EEFighter eefighterController) {
		this.eefighterController = eefighterController;
		this.protocolMappingExecutor = new ProtocolMappingExecutor(this);
		
		try{
			initSocket();
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void initSocket() throws IOException {
		serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("The server is accepting another player's socket..");
		opponentClient = serverSocket.accept();
		clientInput = new DataInputStream(opponentClient.getInputStream());
		clientOutput = new DataOutputStream(opponentClient.getOutputStream());
		inputListenerWorker.start();
	}

	private class InputListenerWorker extends Thread{
		@Override
		public void run() {
			try{
				System.out.println("The input listener worker is starting.");
				while(isGameClosed())
				{
					String protocol = clientInput.readUTF();
					protocolMappingExecutor.executeProtocol(protocol);
				}
			}catch (IOException e) {
				//TODO notify and shutdown the game.
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setGameView(GameView gameView) {
		eefighterController.setGameView(gameView);
	}

	@Override
	public void startGame() {
		
	}

	@Override
	public void move(PlayerSprite player, Direction direction, Direction imgDirection, Status status) {
		
	}

	@Override
	public void nextQuestion() {
		
	}

	@Override
	public void popLetter(PlayerSprite player) {
		
	}

	@Override
	public void pickUp(PlayerSprite player) {
		
	}

	@Override
	public void checkAnswer(PlayerSprite player) {
		
	}

	@Override
	public void closeGame() {
		
	}

	@Override
	public boolean isGameClosed() {
		return eefighterController.isGameClosed();
	}
}
