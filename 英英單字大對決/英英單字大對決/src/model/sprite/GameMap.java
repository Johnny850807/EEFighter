package model.sprite;
//todo ®œ®‰Iterable
public class GameMap {
	
	private final int WIDTH;
	private final int HEIGHT;
	
	public GameMap(int wIDTH, int hEIGHT) {
		super();
		WIDTH = wIDTH;
		HEIGHT = hEIGHT;
		blockSprites = new Sprite[HEIGHT][WIDTH];
	}

	private Sprite[][] blockSprites;

	public void setBlockSprite(int x, int y, Sprite sprite) {
		blockSprites[y][x] = sprite;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public Sprite getSprite(int x, int y) {
		return blockSprites[y][x];
	}
	
}
