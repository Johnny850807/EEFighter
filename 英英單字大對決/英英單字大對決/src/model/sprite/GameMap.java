package model.sprite;

import java.util.Iterator;

/*
 * TODO ¹ê§@ Composite pattern ½Æ¼g  update() ¸ò  isConflict(sprite) ¨ç¼Æ
 */
public class GameMap extends Sprite implements Iterable<Sprite> {
	public static final int ITEM_SIZE = 64;
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
		sprite.setXY(x * ITEM_SIZE, y * ITEM_SIZE);
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
	public synchronized void update() {
		// TODO Auto-generated method stub
		super.update();
	}
	
	@Override
	public synchronized boolean isCollisions() {
		// TODO Auto-generated method stub
		return super.isCollisions();
	}
	
	public boolean outOfMap(Sprite sprite) {
		return sprite.getXy().hasNegative() || sprite.getY() + sprite.getBiasWithY() + sprite.getBodyHeight() > HEIGHT * ITEM_SIZE || sprite.getX() + sprite.getBiasWithX() + sprite.getBodyWidth() > WIDTH * ITEM_SIZE;
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
