package ui;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.EEFighter;
import factory.ComponentAbstractFactory;
import model.Question;
import model.sprite.GameMap;
import model.sprite.PlayerSprite;
import model.sprite.Sprite;
import model.sprite.SpriteName;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import utils.SoundPlayer;

/**
 * @author Lin The game view where showing the playing game.
 */
public class GameViewImp extends JPanel implements GameView, KeyListener {
	private static final String ERROR_SOUND_PATH = "sounds/error.wav";
	private static final String CORRECT_SOUND_PATH = "sounds/correct.wav";
	private GameMap gameMap;
	private EEFighter eeFighter;
	private PlayerSprite spriteP1;
	private PlayerSprite spriteP2;
	private List<Sprite> letters;
	private IGameStartView gameStartView;

	public GameViewImp(GameStartView gameStartView, EEFighter eeFighter,
			ComponentAbstractFactory componentAbstractFactory) {
		this.eeFighter = eeFighter;
		this.gameStartView = gameStartView;
		eeFighter.setGameView(this);
	}

	@Override
	public void start() {
		setBounds(0, 0, 1110, 700);
		setupViews();
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(this);
		setupLayout();

		eeFighter.startGame();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGameMap(g);
		drawLetters(g);
		drawBothPlayers(g);
	}

	private void drawGameMap(Graphics g) {
		if (gameMap != null)
			for (Sprite sprite : gameMap)
				drawSprite(g, sprite);
	}

	private void drawLetters(Graphics g) {
		if (letters != null)
			for (int i = 0; i < letters.size(); i++)
				drawSprite(g, letters.get(i));
	}

	private void drawBothPlayers(Graphics g) {
		if (spriteP1 != null)
			drawSprite(g, spriteP1);
		if (spriteP2 != null)
			drawSprite(g, spriteP2);
	}

	private void drawSprite(Graphics g, Sprite sprite) {
		g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), null);
	}

	private void setupLayout() {
		setLayout(new FlowLayout());
	}

	private void setupViews() {

	}

	@Override
	public void onGameClose() {

	}

	@Override
	public void onDraw(GameMap gameMap, List<Sprite> letters, PlayerSprite player1, PlayerSprite player2) {
		this.gameMap = gameMap;
		this.letters = letters;
		this.spriteP1 = player1;
		this.spriteP2 = player2;
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			eeFighter.move(spriteP1, spriteP1.getDirection(), Status.MOVE);
			break;
		case KeyEvent.VK_DOWN:
			eeFighter.move(spriteP1, spriteP1.getDirection(), Status.MOVE);
			break;
		case KeyEvent.VK_LEFT:
			eeFighter.move(spriteP1, Direction.WEST, Status.MOVE);
			break;
		case KeyEvent.VK_RIGHT:
			eeFighter.move(spriteP1, Direction.EAST, Status.MOVE);
			break;
		case KeyEvent.VK_K:
			eeFighter.popLetter(spriteP1);
			break;
		case KeyEvent.VK_L:
			eeFighter.checkAnswer(spriteP1);
			break;
		case KeyEvent.VK_COLON:
			// Todo player1 pickUp letter
			break;
		case KeyEvent.VK_T:
			eeFighter.move(spriteP2, spriteP2.getDirection(), Status.MOVE);
			break;
		case KeyEvent.VK_G:
			eeFighter.move(spriteP2, spriteP2.getDirection(), Status.MOVE);
			break;
		case KeyEvent.VK_F:
			eeFighter.move(spriteP2, Direction.WEST, Status.MOVE);
			break;
		case KeyEvent.VK_H:
			eeFighter.move(spriteP2, Direction.EAST, Status.MOVE);
			break;
		case KeyEvent.VK_Z:
			eeFighter.popLetter(spriteP2);
			break;
		case KeyEvent.VK_X:
			eeFighter.checkAnswer(spriteP2);
			break;
		case KeyEvent.VK_C:
			// player2 pickUp letter
			break;
		default:
			break;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// set the corresponding action bit as 0.
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_COLON:
			eeFighter.move(spriteP1, spriteP1.getDirection(), Status.STOP);
			break;
		case KeyEvent.VK_T:
		case KeyEvent.VK_G:
		case KeyEvent.VK_F:
		case KeyEvent.VK_H:
		case KeyEvent.VK_C:
			eeFighter.move(spriteP2, spriteP2.getDirection(), Status.STOP);
			break;
		}
	}

	@Override
	public void onGameStarted() {
		eeFighter.nextQuestion();
	}

	@Override
	public void onGameOver(PlayerSprite player) {
		JOptionPane.showMessageDialog(null, player.getSpriteName().toString() + " win the game!!!");
	}

	@Override
	public void onMovedSuccessfuly(Sprite sprite, Direction direction, Status status) {

	}

	@Override
	public void onHitWall(Sprite sprite) {

	}

	@Override
	public void onNextQuestion(Question question) {
		gameStartView.onNextQuestion(question);
	}

	@Override
	public void onLetterPoppedSuccessfuly(PlayerSprite player, List<Sprite> letter) {
		if (spriteP1 == player)
			gameStartView.onPlayerPopedLetter("player1", player.getScore(), letter);
		else if (spriteP2 == player)
			gameStartView.onPlayerPopedLetter("player2", player.getScore(), letter);
	}

	@Override
	public void onLetterPoppedFailed(PlayerSprite player) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onLetterGotten(PlayerSprite player, List<Sprite> letter) {
		if (spriteP1 == player)
			gameStartView.onPlayerEatLetter("player1", player.getScore(), letter);
		else
			gameStartView.onPlayerEatLetter("player2", player.getScore(), letter);
	}

	@Override
	public void onAnswerCorrect(PlayerSprite player) {
		SoundPlayer.getInstance().playSound(CORRECT_SOUND_PATH);
		eeFighter.nextQuestion();
		gameStartView.onAnswerCorrectCleanLettersView();
		if (spriteP1.getSpriteName() == player.getSpriteName())
			gameStartView.showPlayerScore("player1", player.getScore());
		else
			gameStartView.showPlayerScore("player2", player.getScore());
		System.out.println("correct");
	}

	@Override
	public void onAnswerWrong(PlayerSprite player) {
		SoundPlayer.getInstance().playSound(ERROR_SOUND_PATH);
	}

	@Override
	public void onNoMoreQuestion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onQuestionWordSoundPlay(Question question) {
		SoundPlayer.getInstance().playSound(question.getSoundPath());
	}

}
