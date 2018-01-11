package model.sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import model.Question;

public class LetterPlacingManager {
	private List<Sprite> letters = Collections.synchronizedList(new ArrayList<>());
	private LetterCreateListener letterCreateListener;
	private LetterPool letterPool;
	private GameMap gameMap;
	private PlayerSprite player1;
	private PlayerSprite player2;
	private boolean windowClosed;
	private boolean gameOver;
	private Timer timer = new Timer();
	
	public LetterPlacingManager(GameMap gameMap, LetterPool letterPool, PlayerSprite player1, PlayerSprite player2) {
		this.letterPool = letterPool;
		this.gameMap = gameMap;
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public void setLetterCreateListener(LetterCreateListener letterCreateListener) {
		this.letterCreateListener = letterCreateListener;
	}
	
	public void onNextQuestion(Question question) {
		letterPool.shuffleObjects(question);
	}
	
	public void createLetter() {
		new Thread() {
			public void run() {
				while (!windowClosed && !gameOver) {
					try {
						Thread.sleep(550);
						Sprite sprite = getLetter();
						letters.add(sprite);
						releaseLetterAfter20Secs(sprite);
						letterCreateListener.onCreateLetters(letters);
					} catch (ConcurrentModificationException e) {
						//
					} catch (InterruptedException e) {
						// 
					}
				}
			}
		}.start();
	}
	
	private void releaseLetterAfter20Secs(Sprite letter) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (letters.contains(letter))
				{
					letterPool.release(letter);
					letters.remove(letter);
					letterCreateListener.onCreateLetters(letters);
				}
			}
		}, TimeUnit.SECONDS.toMillis(20));
	}
	
	private Sprite getLetter() {
		Sprite sprite = letterPool.get();
		Random random = new Random();
		int x, y;
		do {
			x = random.nextInt(gameMap.getWidth());
			y = random.nextInt(gameMap.getHeight());
		} while (hasLetter(x, y) || !hasGrass(x, y) || hasNearByPlayer(x, y));
		sprite.setXY(x * GameMap.ITEM_SIZE, y * GameMap.ITEM_SIZE);
		return sprite;
	}
	
	private boolean hasNearByPlayer(int x, int y) {
		int range = GameMap.ITEM_SIZE * 3;
		int X = x * GameMap.ITEM_SIZE;
		int Y = y * GameMap.ITEM_SIZE;
		int player1X = player1.getX();
		int player1Y = player1.getY();
		int player2X = player2.getX();
		int player2Y = player2.getY();
		if ((X > player1X - range && X < player1X + range && Y > player1Y - range && Y < player1Y + range) ||
			(X > player2X - range && X < player2X + range && Y > player2Y - range && Y < player2Y + range))
			return true;
		return false;
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
		letterPool.release(sprite);
	}
	
	public void releaseLetters(List<Sprite> sprites) {
		for (Sprite sprite : sprites) 
			letterPool.release(sprite);
	}
	
	public void windowClosed() {
		windowClosed = true;
	}
	
	public void gameOver() {
		gameOver = true;
	}
	
	public interface LetterCreateListener {
		void onCreateLetters(List<Sprite> letters);	
	}
	
}
