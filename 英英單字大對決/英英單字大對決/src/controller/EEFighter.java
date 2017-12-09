package controller;

import java.util.ArrayList;
import java.util.List;

import model.QuestionManger;
import model.sprite.GameMap;
import model.sprite.LetterCreateListener;
import model.sprite.LetterManager;
import model.sprite.LetterPool;
import model.sprite.MapDirector;
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
	private MapDirector mapDirector;
	private GameMap gameMap;
	private LetterManager letterManager;
	private QuestionManger questionManger;
	private List<Sprite> letters = new ArrayList<Sprite>();
	private Sprite player1;
	private Sprite player2;
	
	public EEFighter(MapDirector mapDirector) {
		this.mapDirector = mapDirector;
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
		player1 = spritePrototypeFactory.createSprite(SpriteName.PLAYER);
		player2 = spritePrototypeFactory.createSprite(SpriteName.PLAYER);
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
	
	public void nextQuestion() {
		gameView.onNextQuestion(questionManger.getNextQuestion());
	}
	
	public boolean isOver() {
		return false;
	}

	@Override
	public void onCreateLetter(List<Sprite> letters) {
		this.letters = letters;
	}
	
	public void popLetter(Sprite player) {
		Sprite letter = player.getLastLetter();
		if (letter == null)
			gameView.onLetterPopedFailed(player);
		player.removeLetter(letter);
		gameView.onLetterPopedSuccessfuly(player, player.getLetters());
	}
	
	public void isLetterCollide(Sprite player) {
		for (Sprite letter : letters)
			if (letter.isCollisions(player)) {
				player.addLetter(letter);
				letterManager.releaseLettter(letter);
				gameView.onLetterGotten(player, player.getLetters());
			}	
	}
	
}
