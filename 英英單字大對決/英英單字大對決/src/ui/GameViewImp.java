package ui;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JPanel;

import org.omg.CORBA.PRIVATE_MEMBER;

import controller.EEFighter;
import model.Question;
import model.sprite.GameMap;
import model.sprite.IGameStartView;
import model.sprite.LetterPool;
import model.sprite.Sprite;
import model.sprite.SpriteName;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;

/*
 * The game view where showing the playing game.
 */
public class GameViewImp extends JPanel implements GameView, KeyListener {

	private GameMap gameMap;
	private EEFighter eeFighter;
	private Sprite spriteP1;
	private Sprite spriteP2;
	private List<Sprite> letters;
	private IGameStartView gameStartView;

	public GameViewImp(EEFighter eeFighter, IGameStartView gameStartView) {
		this.eeFighter = eeFighter;
		this.gameStartView = gameStartView;
	}

	@Override
	public void start() {
		eeFighter.setGameView(this);

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
	
	private void drawGameMap(Graphics g){
		if (gameMap != null)
			for (Sprite sprite : gameMap)
				drawSprite(g, sprite);
	}
	
	private void drawLetters(Graphics g){
		if (letters != null)
			for (int i = 0 ; i < letters.size() ; i ++ )
				drawSprite(g, letters.get(i));
	}
	
	private void drawBothPlayers(Graphics g){
		if (spriteP1 != null)
			drawSprite(g, spriteP1);
		if (spriteP2 != null)
			drawSprite(g, spriteP2);
	}
	
	
	
	
	private void drawSprite(Graphics g, Sprite sprite){
		g.drawImage(sprite.getImage(sprite.getDirection()), sprite.getX(), sprite.getY(), null);
	}

	private void setupLayout() {
		setLayout(new FlowLayout());
	}

	private void setupViews() {

	}

	@Override
	public void onGameClose() {

	}

	public void nextQuestion() {
		try {
			Thread.sleep(1000);
			eeFighter.nextQuestion();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDraw(GameMap gameMap, List<Sprite> letters, Sprite player1, Sprite player2) {
		this.gameMap = gameMap;
		this.letters = letters;
		this.spriteP1 = player1;
		this.spriteP2 = player2;
		repaint();
	}

	/**
	 * Use 8-bit to store the key pressing status. Each bit corresponding action key
	 * from left to right is: None, None, None, pop the eaten letter, move up, move
	 * down, move left, move right. If the bit showing 1 means the corresponding
	 * action key should be pressing, and the bit should be set as 0 once the key is
	 * released. Due to this strategy, we can perform composite moving keys such as
	 * 'move top-left' by simply storing 0b00001010.
	 */
	private int keyInputP1 = 0b00000000;
	private int keyInputP2 = 0b00000000;

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			keyInputP1 |= 0b00001000;
			spriteP1.setDirection(Direction.NORTH);
			break;
		case KeyEvent.VK_DOWN:
			keyInputP1 |= 0b00000100;
			spriteP1.setDirection(Direction.SOUTH);
			break;
		case KeyEvent.VK_LEFT:
			keyInputP1 |= 0b00000010;
			spriteP1.setDirection(Direction.WEST);
			break;
		case KeyEvent.VK_RIGHT:
			keyInputP1 |= 0b00000001;
			spriteP1.setDirection(Direction.EAST);
			break;
		case KeyEvent.VK_T:
			keyInputP2 |= 0b00001000;
			spriteP2.setDirection(Direction.NORTH);
			break;
		case KeyEvent.VK_G:
			keyInputP2 |= 0b00000100;
			spriteP2.setDirection(Direction.SOUTH);
			break;
		case KeyEvent.VK_F:
			keyInputP2 |= 0b00000010;
			spriteP2.setDirection(Direction.WEST);
			break;
		case KeyEvent.VK_H:
			keyInputP2 |= 0b00000001;
			spriteP2.setDirection(Direction.EAST);
			break;
		default:
			break;
		}

		if ((keyInputP1 & 0b001000) != 0)
			eeFighter.move(spriteP1, Direction.NORTH, Status.MOVE);
		if ((keyInputP1 & 0b000100) != 0)
			eeFighter.move(spriteP1, Direction.SOUTH, Status.MOVE);
		if ((keyInputP1 & 0b000010) != 0)
			eeFighter.move(spriteP1, Direction.WEST, Status.MOVE);
		if ((keyInputP1 & 0b000001) != 0)
			eeFighter.move(spriteP1, Direction.EAST, Status.MOVE);
		if ((keyInputP2 & 0b001000) != 0)
			eeFighter.move(spriteP2, Direction.NORTH, Status.MOVE);
		if ((keyInputP2 & 0b000100) != 0)
			eeFighter.move(spriteP2, Direction.SOUTH, Status.MOVE);
		if ((keyInputP2 & 0b000010) != 0)
			eeFighter.move(spriteP2, Direction.WEST, Status.MOVE);
		if ((keyInputP2 & 0b000001) != 0)
			eeFighter.move(spriteP2, Direction.EAST, Status.MOVE);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// set the corresponding action bit as 0.
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			keyInputP1 &= 0b11110111;
			break;
		case KeyEvent.VK_DOWN:
			keyInputP1 &= 0b11111011;
			break;
		case KeyEvent.VK_LEFT:
			keyInputP1 &= 0b11111101;
			break;
		case KeyEvent.VK_RIGHT:
			keyInputP1 &= 0b11111110;
			break;
		case KeyEvent.VK_T:
			keyInputP2 &= 0b11110111;
			break;
		case KeyEvent.VK_G:
			keyInputP2 &= 0b11111011;
			break;
		case KeyEvent.VK_F:
			keyInputP2 &= 0b11111101;
			break;
		case KeyEvent.VK_H:
			keyInputP2 &= 0b11111110;
			break;
		}

		if (((keyInputP1 & 0b001000) == 0) || ((keyInputP1 & 0b000100) == 0) || ((keyInputP1 & 0b000010) == 0)
				|| ((keyInputP1 & 0b000001) == 0))
			eeFighter.move(spriteP1, spriteP1.getDirection(), Status.STOP);
		if (((keyInputP2 & 0b001000) == 0) || ((keyInputP2 & 0b000100) == 0) || ((keyInputP2 & 0b000010) == 0)
				|| ((keyInputP2 & 0b000001) == 0))
			eeFighter.move(spriteP2, spriteP2.getDirection(), Status.STOP);
	}

	@Override
	public void onGameStarted() {

	}

	@Override
	public void onGameOver() {

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
	public void onLetterPopedSuccessfuly(Sprite player, Sprite letter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLetterPopedFailed(Sprite player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLetterGotten(Sprite player, Sprite letter) {
		// TODO Auto-generated method stub
		
	}

}
