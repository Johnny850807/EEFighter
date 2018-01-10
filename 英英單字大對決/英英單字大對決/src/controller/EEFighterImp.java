package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.jsoup.select.Evaluator.Id;

import factory.ComponentAbstractFactory;
import factory.ReleasedComponentAbstractFactory;
import model.Question;
import model.QuestionManager;
import model.QuestionManager.QuestionListener;
import model.sprite.GameMap;
import model.sprite.LetterPlacingManager;
import model.sprite.LetterPlacingManager.LetterCreateListener;
import model.sprite.LetterPool;
import model.sprite.PlayerSprite;
import model.sprite.Sprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import model.sprite.SpriteName;
import model.sprite.SpritePrototypeFactory;
import ui.GameView;


/**
 * @author Joanna (�i��ޱ)
 */
public class EEFighterImp implements EEFighter, LetterCreateListener, QuestionListener {
	private GameView gameView;
	private GameMap gameMap;
	private LetterPlacingManager letterPlacingManager;
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
		letterPlacingManager = new LetterPlacingManager(gameMap, new LetterPool(70), player1, player2);
		letterPlacingManager.setLetterCreateListener(this);
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
		Sprite grass0 = grasses.get(0);
		Sprite grass1 = grasses.get(1);
		player1.setXY(grass0.getX()+ grass0.getW()/4 - player1.getBiasWithX(), grass0.getY()+ grass0.getH()/4 - player1.getBiasWithY());
		player2.setXY(grass1.getX()+ grass1.getW()/4 - player2.getBiasWithX(), grass1.getY()+ grass1.getH()/4 - player2.getBiasWithY());
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
		letterPlacingManager.createLetter();
	}

	@Override
	public void move(PlayerSprite player, Direction direction, Direction imgDirection, Status status) {
		player.setImgDirection(imgDirection);
		player.setDirection(direction);
		player.setStatus(status);
		gameView.onMovedSuccessfuly(player, direction, status);
	}

	@Override
	public void nextQuestion() {
		cleanMapAndPlayerLetters();
		if (questionManager.hasNext()) {
			Question question = questionManager.getNextQuestion();
			System.out.println(questionManager.getIndex() + " " + question.getNumber());
			letterPlacingManager.onNextQuestion(question);
			gameView.onNextQuestion(question);
			playQuestionWord(question);
		}
		else {
			gameView.onNoMoreQuestion();
			gameOver();
		}
	}
	
	private void cleanMapAndPlayerLetters() {
		letterPlacingManager.releaseLetters(letters);
		letters.removeAll(letters);
		player1.removeAllLetters();
		player2.removeAllLetters();
	}

	public void gameOver() {
		gameOver = true;
		letterPlacingManager.gameOver();
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
	public boolean isLetterCollided(PlayerSprite player) {
		for (Sprite letter : letters)
			if (letter.isCollisions(player)) {
				player.addLetter(questionManager.getNowQuestion().getWord().toUpperCase(), letter);
				letters.remove(letter);
				letterPlacingManager.releaseLetter(letter);
				return true;
			}
		return false;
	}
	
	@Override
	public void pickUp(PlayerSprite player) {
		if (isLetterCollided(player)) 
			gameView.onLetterGotten(player, player.getLetters());
		else 
			gameView.onNoLetterGotten(player, player.getLetters());	
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
		letterPlacingManager.windowClosed();
		soundPlayTimer.windowClosed();
	}
}

