package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import factory.ComponentAbstractFactory;
import model.Question;
import model.QuestionManager;
import model.QuestionManager.QuestionListener;
import model.sprite.GameMap;
import model.sprite.LetterManager;
import model.sprite.LetterManager.LetterCreateListener;
import model.sprite.LetterPool;
import model.sprite.PlayerSprite;
import model.sprite.Sprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import model.sprite.SpriteName;
import model.sprite.SpritePrototypeFactory;
import ui.GameView;


/**
 * @author Joanna (±i®ÑÞ±)
 */
public class EEFighterImp implements EEFighter, LetterCreateListener, QuestionListener {
	private ComponentAbstractFactory componentAbstractFactory;
	private GameView gameView;
	private GameMap gameMap;
	private LetterManager letterManager;
	private QuestionManager questionManager;
	private List<Sprite> letters = new ArrayList<Sprite>();
	private PlayerSprite player1;
	private PlayerSprite player2;
	private SoundPlayTimer soundPlayTimer;
	private boolean windowClosed;
	private boolean gameOver;
	
	public EEFighterImp(ComponentAbstractFactory componentAbstractFactory) {
		gameMap = componentAbstractFactory.createMapDirector().buildMap();
		questionManager = new QuestionManager(componentAbstractFactory.getWordRepository());
		questionManager.addListener(this);
		createPlayers();
		letterManager = new LetterManager(gameMap, new LetterPool(70), player1, player2);
		letterManager.setLetterCreateListener(this);
	}
	
	@Override
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

	@Override
	public void startGame() {
		new Thread() {
			public void run() {
				questionManager.prepareQuestions();
				while (!gameOver && !windowClosed) {
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

	@Override
	public void onQuestionPrepareFinish() {
		gameView.onGameStarted();
		letterManager.createLetter();
	}

	@Override
	public void move(PlayerSprite player, Direction direction, Status status) {
		player.setDirection(direction);
		player.setStatus(status);
		player.addOnePicIndex();
		gameView.onMovedSuccessfuly(player, direction, status);
	}

	@Override
	public void nextQuestion() {
		cleanMapAndPlayerLetters();
		if (questionManager.hasNext()) {
			Question question = questionManager.getNextQuestion();
			System.out.println(questionManager.getIndex() + " " + question.getNumber());
			letterManager.onNextQuestion(question);
			gameView.onNextQuestion(question);
			playQuestionWord(question);
		}
		else {
			gameView.onNoMoreQuestion();
			gameOver();
		}
	}
	
	private void cleanMapAndPlayerLetters() {
		letterManager.releaseLetters(letters);
		letters.removeAll(letters);
		player1.removeAllLetters();
		player2.removeAllLetters();
	}

	public void gameOver() {
		gameOver = true;
		letterManager.gameOver();
		soundPlayTimer.over();
		PlayerSprite player = (player1.getScore() > player2.getScore())? player1 : player2;
		gameView.onGameOver(player);
	}

	@Override
	public void onCreateLetters(List<Sprite> letters) {
		this.letters = letters;
	}

	@Override
	public void popLetter(PlayerSprite player) {
		Sprite letter = player.popLetter();
		if (letter != null) 
			gameView.onLetterPoppedSuccessfuly(player, player.getLetters());
		else 
			gameView.onLetterPoppedFailed(player);
	}

	@Override
	public void isLetterCollided(PlayerSprite player) {
		for (Sprite letter : letters)
			if (letter.isCollisions(player)) {
				player.addLetter(letter);
				letters.remove(letter);
				letterManager.releaseLetter(letter);
				gameView.onLetterGotten(player, player.getLetters());
				break;
			}
	}

	@Override
	public void checkAnswer(PlayerSprite player) {
		String words = questionManager.getNowQuestion().getWord().toUpperCase();
		System.out.println(words);
		List<Sprite> letters = player.getLetters();
		if (words.length() == letters.size() && compareLetters(words, letters)) {
			player.setScore(player.getScore() + 1);
			gameView.onAnswerCorrect(player);
		}
		else 
			gameView.onAnswerWrong(player);
	}
	
	private boolean compareLetters(String words, List<Sprite> playerLetters) {
		boolean[] check = new boolean[words.length()];
		for (Sprite letter : playerLetters) 
			for (int i = 0; i < check.length; i++) 
				if (!check[i] && letter.getSpriteName().toString().charAt(0) == words.charAt(i)) {
					System.out.println("correct:" + words.charAt(i));
					check[i] = true;
					break;
				}				
		for (int i = 0; i < check.length; i++)
			if (!check[i]) 
				return false;
		return true;
	}
	
	private void playQuestionWord(Question question) {
		if (soundPlayTimer != null)
			soundPlayTimer.over();
		soundPlayTimer = new SoundPlayTimer(gameView, question);
		soundPlayTimer.start();
	}

	@Override
	public void windowClosed() {
		windowClosed = true;
		letterManager.windowClosed();
		soundPlayTimer.windowClosed();
	}

}
