package controller;

import java.util.ArrayList;
import java.util.List;

import model.factory.SpritePrototypeFactory;
import model.sprite.BasicMapBuilder;
import model.sprite.BasicMapDirector;
import model.sprite.GameMap;
import model.sprite.MapDirector;
import model.sprite.Sprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import ui.GameView;

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
	}
	
	public void startGame() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(17);
						//sprite update
						gameView.onDraw(gameMap, letters, player1, player2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}
	
	public void move(Sprite sprite, Direction direction, Status status) {
		
	}
	
	
	public boolean isOver() {
		return false;
	}
}
