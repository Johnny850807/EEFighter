package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Question;
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
public class EEFighterImp implements EEFighter, LetterCreateListener {
	private GameView gameView;
	private GameMap gameMap;
	private LetterManager letterManager;
	private QuestionManger questionManger;
	private List<Sprite> letters = new ArrayList<Sprite>();
	private PlayerSprite player1;
	private PlayerSprite player2;
	private SoundPlayTimer soundPlayTimer;
	private boolean isNextQuestion = false;
	
	public EEFighterImp(MapDirector mapDirector) {
		gameMap = mapDirector.buildMap();
		questionManger = new QuestionManger(new WordXMLRepository("wordwarehouse"));
		letterManager = new LetterManager(gameMap, new LetterPool(70));
		letterManager.setLetterCreateListener(this);
		createPlayers();
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
				while (!isOver()) {
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
	public void move(PlayerSprite player, Direction direction, Status status) {
		player.setDirection(direction);
		player.setStatus(status);
		gameView.onMovedSuccessfuly(player, direction, status);
	}

	@Override
	public void nextQuestion() {
		letterManager.createLetter();
		letters.removeAll(letters);
		player1.removeAllLetters();
		player2.removeAllLetters();
		Question question = questionManger.getNextQuestion();
		if (questionManger.hasNext()) {
			gameView.onNextQuestion(question);
			playQuestionWord(question);
		}
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

	@Override
	public void popLetter(PlayerSprite player) {
		Sprite letter = player.popLetter();
		if (letter != null) {
			letterManager.releaseLetter(letter);
			gameView.onLetterPoppedSuccessfuly(player, player.getLetters());
		}
		else 
			gameView.onLetterPoppedFailed(player);
	}

	@Override
	public void isLetterCollided(PlayerSprite player) {
		for (Sprite letter : letters)
			if (letter.isCollisions(player)) {
				player.addLetter(letter);
				letters.remove(letter);
				gameView.onLetterGotten(player, player.getLetters());
				break;
			}
	}

	@Override
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
			soundPlayTimer.questionChange();
		soundPlayTimer = new SoundPlayTimer(gameView, question);
		soundPlayTimer.start();
	}
	
}
