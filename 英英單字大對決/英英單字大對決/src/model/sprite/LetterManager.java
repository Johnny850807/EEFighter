package model.sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.EEFighter;

public class LetterManager {
	
	private List<Sprite> letters = new ArrayList<>();
	private ILetterPool letterPool;
	private EEFighter eeFighter;
	private GameMap gameMap;
	
	public LetterManager(GameMap gameMap, EEFighter eeFighter) {
		letterPool = LetterPool.getInstance();
		this.gameMap = gameMap;
		this.eeFighter = eeFighter;
	}
	
	public void createLetter() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(5000);
						Sprite sprite = letterPool.requireSprite();
						Random random = new Random();
						int x = random.nextInt(gameMap.getBodyHeight());
						int y = random.nextInt(gameMap.getBodyWidth());
						sprite.setXY(x, y);
						letters.add(sprite);
						eeFighter.onCreateLetter(letters);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
}
