package controller;

import java.util.ArrayList;
import java.util.List;

import model.QuestionManger;
import model.sprite.GameMap;
import model.sprite.LetterCreateListener;
import model.sprite.LetterManager;
import model.sprite.LetterPool;
import model.sprite.MapDirector;
import model.sprite.PlayerSprite;
import model.sprite.Sprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import model.sprite.SpriteName;
import model.sprite.SpritePrototypeFactory;
import model.words.WordXMLRepository;
import ui.GameView;


/**
 * @author Joanna (±i®ÑÞ±)
 */
public class EEFighter implements LetterCreateListener {
	private GameView gameView;
	private GameMap gameMap;
	private LetterManager letterManager;
	private QuestionManger questionManger;
	private List<Sprite> letters = new ArrayList<Sprite>();
	private PlayerSprite player1;
	private PlayerSprite player2;
	
	public EEFighter(MapDirector mapDirector) {
		gameMap = mapDirector.buildMap();
		questionManger = new QuestionManger(new WordXMLRepository("words"));
		letterManager = new LetterManager(gameMap, new LetterPool());
		letterManager.setLetterCreateListener(this);
		createPlayers();
	}
	
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
		player1.setGameView(gameView);
		player2.setGameView(gameView);
	}
	
	private void createPlayers() {
		SpritePrototypeFactory spritePrototypeFactory = SpritePrototypeFactory.getInstance();
		player1 = (PlayerSprite) spritePrototypeFactory.createSprite(SpriteName.PLAYER);
		player2 = (PlayerSprite) spritePrototypeFactory.createSprite(SpriteName.PLAYER);
		player1.setGameMap(gameMap);
		player2.setGameMap(gameMap);
		player1.setXY(128, 128);
		player2.setXY(256, 128);
	}
	
	public void startGame() {
		letterManager.createLetter();
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(17);
						player1.update();
						player2.update();
						isLetterCollided(player1);
						isLetterCollided(player2);
						gameView.onDraw(gameMap, letters, player1, player2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}	
	
	public void move(PlayerSprite player, Direction direction, Status status) {
		player.setDirection(direction);
		player.setStatus(status);
		gameView.onMovedSuccessfuly(player, direction, status);
	}
	
	public void nextQuestion() {
		gameView.onNextQuestion(questionManger.getNextQuestion());
	}
	
	public boolean isOver() {
		return false;
	}

	@Override
	public void onCreateLetters(List<Sprite> letters) {
		this.letters = letters;
	}
	
	public void popLetter(PlayerSprite player) {
		Sprite letter = player.popLetter();
		letterManager.releaseLetter(letter);
		gameView.onLetterPoppedSuccessfuly(player, player.getLetters());
	}
	
	public void isLetterCollided(PlayerSprite player) {
		Sprite letter = null;
		for (Sprite l : letters)
			if (l.isCollisions(player)) {
				player.addLetter(l);
				letter = l;
				break;
			}
		if (letter != null) {
			letters.remove(letter);
			gameView.onLetterGotten(player, player.getLetters());
		}
	}
	
	public void checkAnswer(PlayerSprite player) {
		// TODO
	}
	
}
