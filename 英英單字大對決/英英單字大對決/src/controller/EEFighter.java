package controller;

import java.util.ArrayList;
import java.util.List;

import model.factory.SpritePrototypeFactory;
import model.sprite.GameMap;
import model.sprite.MapDirector;
import model.sprite.Sprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import model.sprite.SpriteName;
import ui.GameView;


/**
 * @author Joanna (±i®ÑÞ±)
 */
public class EEFighter {

	private GameView gameView;
	private MapDirector mapDirector;
	private GameMap gameMap;
	private List<Sprite> letters = new ArrayList<Sprite>();
	private Sprite player1;
	private Sprite player2;
	
	public EEFighter(MapDirector mapDirector) {
		this.mapDirector = mapDirector;
		gameMap = mapDirector.buildMap();
		createPlayers();
	}
	
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
		player1.setGameView(gameView);
		player2.setGameView(gameView);
	}
	
	private void createPlayers() {
		SpritePrototypeFactory spritePrototypeFactory = SpritePrototypeFactory.getInstance();
		player1 = spritePrototypeFactory.createSprite(SpriteName.PLAYER);
		player2 = spritePrototypeFactory.createSprite(SpriteName.PLAYER);
		player1.setGameMap(gameMap);
		player2.setGameMap(gameMap);
		player1.setXY(128, 128);
		player2.setXY(256, 128);
	}
	
	public void startGame() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(17);
						player1.update();
						player2.update();
						gameView.onDraw(gameMap, letters, player1, player2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}	
	
	public void move(Sprite sprite, Direction direction, Status status) {
		sprite.setDirection(direction);
		sprite.setStatus(status);
		gameView.onMovedSuccessfuly(sprite, direction, status);
	}
	
	public boolean isOver() {
		return false;
	}
}
