package ui;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.sprite.GameMap;

/*
 * The game view where showing the playing game.
 */
public class GameViewImp extends JPanel implements GameView {
	private static final String BLOCK_PATH = "pic/block.png";
	private static final String GRASS_PATH = "pic/grass.png";

	public GameViewImp() {
		setBounds(0, 0, 1100, 700);
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
		Image image = null;
		try {
			image = ImageIO.read(new File(BLOCK_PATH));
			System.out.println(g.drawImage(image, 0, 0, null));
		} catch (IOException e) {
			e.printStackTrace();
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
