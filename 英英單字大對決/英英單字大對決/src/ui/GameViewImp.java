package ui;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.factory.SpritePrototypeFactory;
import model.sprite.BasicMapDirector;
import model.sprite.GameMap;
import model.sprite.MapDirector;
import model.sprite.Sprite;
import model.sprite.XY;

/*
 * The game view where showing the playing game.
 */
public class GameViewImp extends JPanel implements GameView, KeyListener {

	private BasicMapDirector mapDirector;
	private GameMap gameMap;
	private Sprite testSpriteP1;
	private Sprite testSpriteP2;
	private SpritePrototypeFactory prototypeFactory = new SpritePrototypeFactory();

	public GameViewImp(MapDirector mapDirector) {
		gameMap = mapDirector.buildMap();

		
		setBounds(0, 0, 1110, 700);
		setupViews();
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(this);
		setupLayout();
		drawBasicMap();
		
	}

	private void drawBasicMap() {
		//onDraw();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Sprite sprite : gameMap)
			g.drawImage(sprite.getImage(), sprite.getX() * sprite.getW(), sprite.getY() * sprite.getH(), null);
		
		for(int x = 0; x < gameMap.getWidth(); x++)
			for (int y = 0; y < gameMap.getHeight(); y++) {
				Sprite sprite = gameMap.getSprite(x, y);
				Image image = sprite.getImage();
				g.drawImage(image, sprite.getX() * sprite.getW(), sprite.getY() * sprite.getH(), sprite.getW(), sprite.getH(), null);
			}
		testDrawAllLetters(g);  //TESTING
		//testDrawTwoRoleSprites(g);
	}
	
	private void testDrawAllLetters(Graphics g){
		String[] testMap = new String[] {"00000000000000000",
				"01010101010101010",
				"00000000000000000",
				"10101010101010101",
				"00000000000000000",
				"01010101010101010",
				"00000000000000000",
				"10101010101010101",
				"00000000000000000"};
		try {
			int ascii = 65;
			for(int i = 0 ; i < 9 ; i ++)
				for (int j = 0 ; j < 17 ; j ++)
					if(testMap[i].charAt(j) == '0'&& new Random().nextBoolean())
					{
						ascii = ascii + 1 > 90 ? 65 : ascii + 1;
						g.drawImage(ImageIO.read(new File("D:\\NativeGit\\OOD-Design-Pattern-Course\\英英單字大對決\\素材區\\small a-z\\" + (char)ascii + ".png")), j*64, i*64, null);
					}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void testDrawTwoRoleSprites(Graphics g){
		try{
			g.drawImage(testSpriteP1.getImage(), testSpriteP1.getX(), testSpriteP1.getY(), 64, 64, null);
			g.drawImage(testSpriteP2.getImage(), testSpriteP2.getX(), testSpriteP2.getY(), 64, 64, null);
		}catch (Exception e) {}  //TESTING

	}

	private void setupLayout() {
		setLayout(new FlowLayout());
	}

	private void setupViews() {
	}

	@Override
	public void onClose() {

	}

	@Override
	public void onDraw(GameMap gameMap, Sprite[] letters, Sprite player1, Sprite player2) {

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
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			keyInputP1 |= 0b00001000;
			break;
		case KeyEvent.VK_DOWN:
			keyInputP1 |= 0b00000100;
			break;
		case KeyEvent.VK_LEFT:
			keyInputP1 |= 0b00000010;
			break;
		case KeyEvent.VK_RIGHT:
			keyInputP1 |= 0b00000001;
			break;
		case KeyEvent.VK_T:
			keyInputP2 |= 0b00001000;
			break;
		case KeyEvent.VK_G:
			keyInputP2 |= 0b00000100;
			break;
		case KeyEvent.VK_F:
			keyInputP2 |= 0b00000010;
			break;
		case KeyEvent.VK_H:
			keyInputP2 |= 0b00000001;
			break;
		default:
			break;
		}

		if ((keyInputP1 & 0b001000) != 0)
			moveRoleSprite(testSpriteP1, new XY(0, -4));
		if ((keyInputP1 & 0b000100) != 0)
			moveRoleSprite(testSpriteP1, new XY(0, 4));
		if ((keyInputP1 & 0b000010) != 0)
			moveRoleSprite(testSpriteP1, new XY(-4, 0));
		if ((keyInputP1 & 0b000001) != 0)
			moveRoleSprite(testSpriteP1, new XY(4, 0));
		if ((keyInputP2 & 0b001000) != 0)
			moveRoleSprite(testSpriteP2, new XY(0, -4));
		if ((keyInputP2 & 0b000100) != 0)
			moveRoleSprite(testSpriteP2, new XY(0, 4));
		if ((keyInputP2 & 0b000010) != 0)
			moveRoleSprite(testSpriteP2, new XY(-4, 0));
		if ((keyInputP2 & 0b000001) != 0)
			moveRoleSprite(testSpriteP2, new XY(4, 0));
		repaint();
	}

	private void moveRoleSprite(Sprite sprite, XY xy) {
		// TODO send a moving request to the controller
		sprite.move(xy); // this is testing, please remove.
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
		default:
			break;
		}
	}

}
