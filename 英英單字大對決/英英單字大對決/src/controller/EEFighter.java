package controller;

import model.factory.SpritePrototypeFactory;
import model.sprite.BasicMapBuilder;
import model.sprite.BasicMapDirector;
import model.sprite.GameMap;
import model.sprite.MapDirector;
import model.sprite.Sprite;
import ui.GameView;

public class EEFighter {

	private GameView gameView;
	private MapDirector mapDirector;
	
	public EEFighter(MapDirector mapDirector) {
		this.mapDirector = mapDirector;
	}
	
	public void startGame() {
		GameMap gameMap = mapDirector.buildMap();
		SpritePrototypeFactory spritePrototypeFactory = SpritePrototypeFactory.getInstance();
		Sprite[] letters = null;
		Sprite player1 = null;
		Sprite player2 = null;
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
	
	public boolean isOver() {
		return false;
	}
}
