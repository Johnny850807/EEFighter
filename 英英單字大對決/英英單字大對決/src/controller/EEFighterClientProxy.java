package controller;

import java.io.IOException;
import java.net.Socket;

import model.sprite.PlayerSprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import ui.GameView;

public class EEFighterClientProxy implements EEFighter{
	private final String IP;
	private Socket socket;
	private GameView gameView;
	private boolean gameClosed = false;
	
	public EEFighterClientProxy(String ip) {
		this.IP = ip;
		
		try {
			socket = new Socket(IP, EEFighterSocketServerProxy.SERVER_PORT);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
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
		gameClosed = true;
	}

	@Override
	public boolean isGameClosed() {
		return gameClosed;
	}
}
