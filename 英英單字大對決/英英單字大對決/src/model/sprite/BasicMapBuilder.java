package model.sprite;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BasicMapBuilder implements MapBuilder {
	
	private static final String BLOCK_PATH = "pic/block.png";
	private static final String GRASS_PATH = "pic/grass.png";
	private GameMap gameMap = new GameMap(17, 9);

	@Override
	public void buildBarrier(int coordinateX, int coordinateY) {
		Image block = null;
		try {
			block = ImageIO.read(new File(BLOCK_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sprite sprite = new Sprite(coordinateX, coordinateY, 64, 64, block);
		gameMap.setBlockSprite(coordinateX, coordinateY, sprite);
	}

	@Override
	public void buildRoad(int coordinateX, int coordinateY) {
		Image road = null;
		try {
			road = ImageIO.read(new File(GRASS_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sprite sprite = new Sprite(coordinateX, coordinateY, 64, 64, road);
		gameMap.setBlockSprite(coordinateX, coordinateY, sprite);
	}

	@Override
	public GameMap build() {
		return gameMap;
	}

}
