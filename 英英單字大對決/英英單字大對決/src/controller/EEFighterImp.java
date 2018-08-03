package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.Consumer;

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
 * @author Joanna (±i®ÑÞ±)
 */
public class EEFighterImp implements EEFighter, LetterCreateListener, QuestionListener {
	protected GameView gameView;
	protected GameMap gameMap;
	protected LetterPlacingManager letterPlacingManager;
	protected QuestionManager questionManager;
	protected List<Sprite> letters = new ArrayList<Sprite>();
	protected PlayerSprite player1;
	protected PlayerSprite player2;
	protected SoundPlayTimer soundPlayTimer;
	protected boolean gameClosed;
	protected boolean gameOver;
	
	public EEFighterImp(ComponentAbstractFactory componentAbstractFactory) {
		gameMap = componentAbstractFactory.createMapDirector().buildMap();
		questionManager = new QuestionManager(componentAbstractFactory.getWordRepository());
		questionManager.addListener(this);
		createPlayers();
		letterPlacingManager = new LetterPlacingManager(gameMap, new LetterPool(70, false), player1, player2);
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
		List<Sprite> grasses = gameMap.getAllTerrain();
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
				while (!gameOver && !gameClosed) {
					try {
						Thread.sleep(30);
						player1.update();
						player2.update();
						notifyGameViews((gameView)-> gameView.onDraw(gameMap, letters, player1, player2));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}	

	@Override
	public void onQuestionPrepareFinish() {
		notifyGameViews((gameView)-> gameView.onGameStarted());
		letterPlacingManager.createLetter();
	}

	@Override
	public void move(PlayerSprite player, Direction direction, Status status) {
		player.setDirection(direction);
		player.setStatus(status);
		notifyGameViews((gameView)-> gameView.onMovedSuccessfuly(player, direction, status));
	}

	@Override
	public void nextQuestion() {
		cleanMapAndPlayerLetters();
		if (questionManager.hasNext()) {
			Question question = questionManager.getNextQuestion();
			letterPlacingManager.onNextQuestion(question);
			notifyGameViews((gameView)-> gameView.onNextQuestion(question));
			playQuestionWord(question);
		}
		else {
			notifyGameViews((gameView)-> gameView.onNoMoreQuestion());
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
		notifyGameViews((gameView)-> gameView.onGameOver(player));
	}

	@Override
	public void onCreateLetters(List<Sprite> letters) {
		this.letters = letters;
	}

	@Override
	public void popLetter(PlayerSprite player) {
		Sprite letter = player.popLetter(questionManager.getNowQuestion().getWord());
		if (letter != null) 
			notifyGameViews((gameView)-> gameView.onLetterPoppedSuccessfuly(player, player.getLetters()));
		else 
			notifyGameViews((gameView)-> gameView.onLetterPoppedFailed(player));
	}

	protected boolean isLetterCollided(PlayerSprite player) {
		for (Sprite letter : letters)
			if (letter.hasCollision(player)) {
				player.addLetter(letter);
				letters.remove(letter);
				letterPlacingManager.releaseLetter(letter);
				return true;
			}
		return false;
	}
	
	@Override
	public void pickUp(PlayerSprite player) {
		if (isLetterCollided(player)) 
			notifyGameViews((gameView)-> gameView.onLetterGotten(player, player.getLetters()));
		else 
			notifyGameViews((gameView)-> gameView.onNoLetterGotten(player, player.getLetters()));	
	}

	@Override
	public void checkAnswer(PlayerSprite player) {
		String words = questionManager.getNowQuestion().getWord().toUpperCase();
		System.out.println(words);
		List<Sprite> letters = player.getLetters();
		if (words.length() == letters.size() && compareLetters(words, letters)) {
			player.setScore(player.getScore() + 1);
			notifyGameViews((gameView)-> gameView.onAnswerCorrect(player));
		}
		else 
			notifyGameViews((gameView)-> gameView.onAnswerWrong(player));
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
		soundPlayTimer = new SoundPlayTimer(gameView, question, 
				(q) -> notifyGameViews((gameView)-> gameView.onQuestionWordSoundPlay(q.getWord(), q.getSoundPath())));
		soundPlayTimer.start();
	}

	@Override
	public void closeGame() {
		gameClosed = true;
		letterPlacingManager.stopPlacing();
		soundPlayTimer.stopCounting();
	}
	
	@Override
	public boolean isGameClosed() {
		return gameClosed;
	}
	
	public void notifyGameViews(Consumer<GameView> notifyAction){
		notifyAction.accept(this.gameView);
	}
}

