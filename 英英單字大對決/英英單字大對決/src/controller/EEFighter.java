package controller;

import java.util.ArrayList;
import java.util.Collections;
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
	private boolean isNextQuestion = false;
	
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
		player1 = (PlayerSprite) spritePrototypeFactory.createSprite(SpriteName.PLAYER1);
		player2 = (PlayerSprite) spritePrototypeFactory.createSprite(SpriteName.PLAYER2);
		player1.setGameMap(gameMap);
		player2.setGameMap(gameMap);
		List<Sprite> grasses = gameMap.getAllGrasses();
		Collections.shuffle(grasses);
		player1.setXY(grasses.get(0).getX(), grasses.get(0).getY());
		player2.setXY(grasses.get(1).getX(), grasses.get(1).getY());
	}
	
	public void startGame() {
		letterManager.createLetter();
		new Thread() {
			public void run() {
				while (!isNextQuestion) {
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
		new Thread() {
			public void run() {
				try {
					Thread.sleep(50000);
					playQuestionWord();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}	
	
	public void move(PlayerSprite player, Direction direction, Status status) {
		player.setDirection(direction);
		player.setStatus(status);
		gameView.onMovedSuccessfuly(player, direction, status);
	}
	
	public void nextQuestion() {
		letters.removeAll(letters);
		player1.removeAllLetters();
		player2.removeAllLetters();
		if (questionManger.hasNext()) 
			gameView.onNextQuestion(questionManger.getNextQuestion());
		else
			gameView.onNoMoreQuestion();
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
		if (letter != null) {
			letterManager.releaseLetter(letter);
			gameView.onLetterPoppedSuccessfuly(player, player.getLetters());
		}
		else 
			gameView.onLetterPoppedFailed(player);
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
		String words = questionManger.getNowQuestion().getWord().toUpperCase();
		System.out.println(words);
		List<Sprite> letters = player.getLetters();
		if (words.length() == letters.size() && compareLetters(words, letters)) 
				gameView.onAnswerCorrect(player);
		else 
			gameView.onAnswerWrong(player);
	}
	
	private boolean compareLetters(String words, List<Sprite> playerLetters) {
		boolean[] check = new boolean[words.length()];
		for (int i = 0; i < check.length; i++)
			check[i] = false;
		for (Sprite letter : playerLetters) {
			for (int i = 0; i < check.length; i++) 
				if (!check[i] && letter.getSpriteName().toString().charAt(0) == words.charAt(i)) {
					System.out.println("correct:" + words.charAt(i));
					check[i] = true;
					break;
				}		
		}				
		for (int i = 0; i < check.length; i++)
			if (!check[i]) 
				return false;
		return true;
	}
	
	private void playQuestionWord() {
		gameView.onQuestionWordSoundPlay(questionManger.getNowQuestion());
	}
	
}
