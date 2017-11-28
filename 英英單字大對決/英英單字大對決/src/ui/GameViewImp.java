package ui;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.sprite.BasicMapBuilder;
import model.sprite.GameMap;
import model.sprite.MapBuilder;
import model.sprite.MapDirector;
import model.sprite.BasicMapDirector;
import model.sprite.MapSpriteEnum;
import model.sprite.Sprite;

/*
 * The game view where showing the playing game.
 */
public class GameViewImp extends JPanel implements GameView {

	private BasicMapDirector mapDirector;
	private GameMap gameMap;

	public GameViewImp(MapDirector mapDirector) {
		gameMap = mapDirector.buildMap();
		setBounds(0, 0, 1110, 700);
		setupViews();
		setupLayout();
		drawBasicMap();
	}

	private void drawBasicMap() {
		draw();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(int x = 0; x < gameMap.getWidth(); x++)
			for (int y = 0; y < gameMap.getHeight(); y++) {
				Sprite sprite = gameMap.getSprite(x, y);
				Image image = sprite.getImage();
				g.drawImage(image, sprite.getX() * sprite.getW(), sprite.getY() * sprite.getH(), null);
			}
				
	}

	private void setupLayout() {
		setLayout(new FlowLayout());
	}

	private void setupViews() {
	}

	@Override
	public void close() {
		
	}

	@Override
	public void draw() {
		
	}

}
