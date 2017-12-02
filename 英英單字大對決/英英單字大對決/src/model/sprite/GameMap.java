package model.sprite;

import java.util.Iterator;


public class GameMap implements Iterable<Sprite> {

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
		sprite.setXY(x, y);
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

	@Override
	public Iterator<Sprite> iterator() {
		return new InnerIterator();
	}

	private class InnerIterator implements Iterator<Sprite> {
		private int x = 0;
		private int y = 0;

		@Override
		public boolean hasNext() {
			return ((x + 1) + y) != (WIDTH + HEIGHT);
		}

		@Override
		public Sprite next() {
			if (x == WIDTH) {
				x = 0;
				y++;
			}
			Sprite sprite = getSprite(x, y);
			x++;

			return sprite;
		}

	}

}
