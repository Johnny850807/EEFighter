package model.sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LetterManager {
	
	private List<Sprite> letters = new ArrayList<>();
	private LetterCreateListener letterCreateListener;
	private ILetterPool letterPool;
	private GameMap gameMap;
	
	public LetterManager(GameMap gameMap) {
		letterPool = LetterPool.getInstance();
		this.gameMap = gameMap;
	}
	
	public void setLetterCreateListener(LetterCreateListener letterCreateListener) {
		this.letterCreateListener = letterCreateListener;
	}
	
	public void createLetter() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
						Sprite sprite = getLetter();
						letters.add(sprite);
						letterCreateListener.onCreateLetters(letters);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	private Sprite getLetter() {
		Sprite sprite = letterPool.requireSprite();
		Random random = new Random();
		int x, y;
		do {
			x = random.nextInt(gameMap.getWidth());
			y = random.nextInt(gameMap.getHeight());
		} while (hasLetter(x, y) || !hasGrass(x, y));
		sprite.setXY(x * GameMap.ITEM_SIZE, y * GameMap.ITEM_SIZE);
		return sprite;
	}
	
	private boolean hasLetter(int x, int y) {
		for (Sprite letter : letters)
			if (letter.getX() == x * GameMap.ITEM_SIZE && letter.getY() == y * GameMap.ITEM_SIZE)
				return true;
		return false;
	}
	
	private boolean hasGrass(int x, int y) {
		return gameMap.getSprite(x, y).getSpriteName() == SpriteName.GRASS;
	}
	
	public void releaseLetter(Sprite sprite) {
		letterPool.releaseSprite(sprite);
	}
}
