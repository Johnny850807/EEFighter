package model.sprite;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import ui.GameView;

public class Sprite implements Cloneable {
	protected XY xy;
	protected int w;
	protected int h;
	protected int biasWithX;
	protected int biasWithY;
	protected int bodyHeight;
	protected int bodyLength;
	protected SpriteName spriteName;
	protected Direction direction = Direction.EAST;
	protected Direction imgDirection = Direction.EAST;
	protected Status status = Status.STOP;
	protected Map<Movement, ImageSequence> imageMap = new HashMap<>();
	protected GameMap gameMap;
	protected GameView gameView;
	protected Movement movement = new Movement(imgDirection, status);

	/**
	 * 
	 * @param w
	 *            width of this sprite
	 * @param h
	 *            height of this sprite
	 * @param biasWithX
	 *            圖片左側與圖片身體的偏差值
	 * @param biasWithY
	 *            圖片上方與圖片身體的偏差值
	 * @param bodyHeight
	 *            圖片的身體部分的高度
	 * @param bodyLength
	 *            圖片的身體部分的長度
	 * @param image
	 *            image of this sprite
	 */
	public Sprite(int w, int h, int biasWithX, int biasWithY, int bodyHeight, int bodyLength, SpriteName spriteName,
			Map<Movement, ImageSequence> imageMap) {
		super();
		this.w = w;
		this.h = h;
		this.biasWithX = biasWithX;
		this.biasWithY = biasWithY;
		this.bodyHeight = bodyHeight;
		this.bodyLength = bodyLength;
		this.spriteName = spriteName;
		this.imageMap = imageMap;
	}

	public Sprite() {
		// TODO Auto-generated constructor stub
	}

	public int getBiasWithX() {
		return biasWithX;
	}

	public void setBiasWithX(int biasWithX) {
		this.biasWithX = biasWithX;
	}

	public int getBiasWithY() {
		return biasWithY;
	}

	public void setBiasWithY(int biasWithY) {
		this.biasWithY = biasWithY;
	}

	public int getBodyHeight() {
		return bodyHeight;
	}

	public void setBodyHeight(int bodyHeight) {
		this.bodyHeight = bodyHeight;
	}

	public int getBodyWidth() {
		return bodyLength;
	}

	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public SpriteName getSpriteName() {
		return spriteName;
	}

	public void setSpriteName(SpriteName spriteName) {
		this.spriteName = spriteName;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		movement.status = status;
		this.status = status;
	}

	public Direction getImgDirection() {
		return imgDirection;
	}

	public void setImgDirection(Direction imgDirection) {
		movement.direction = imgDirection;
		this.imgDirection = imgDirection;
	}

	public XY getXy() {
		return xy;
	}

	public void setXY(int x, int y) {
		this.xy = new XY(x, y);
	}

	public int getX() {
		return xy.getX();
	}

	public void setX(int x) {
		this.xy.setX(x);
	}

	public int getY() {
		return xy.getY();
	}

	public void setY(int y) {
		this.xy.setY(y);
	}

	// Test modify
	public Image getImage() {
		if (status == Status.MOVE)
			return imageMap.get(movement).nextImage();
		return imageMap.get(movement).nextImage();
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public GameView getGameView() {
		return gameView;
	}

	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}

	public synchronized void update() {
		// do nothing as default
	}

	public synchronized boolean isCollisions(Sprite sprite) {
		int xStartSelf = getX() + getBiasWithX();
		int yStartSelf = getY() + getBiasWithY();
		int xEndSelf = xStartSelf + getBodyWidth();
		int yEndSelf = yStartSelf + getBodyWidth();
		int xStartOther = sprite.getX() + sprite.getBiasWithX();
		int yStartOther = sprite.getY() + sprite.getBiasWithY();
		int xEndOther = xStartOther + sprite.getBodyWidth();
		int yEndOther = yStartOther + sprite.getBodyWidth();
		if (xStartSelf < xStartOther && xStartOther < xEndSelf && yStartSelf < yStartOther && yStartOther < yEndSelf)
			return true;
		if (xStartSelf < xStartOther && xStartOther < xEndSelf && yStartSelf < yEndOther && yEndOther < yEndSelf)
			return true;
		if (xStartSelf < xEndOther && xEndOther < xEndSelf && yStartSelf < yStartOther && yStartOther < yEndSelf)
			return true;
		if (xStartSelf < xEndOther && xEndOther < xEndSelf && yStartSelf < yEndOther && yEndOther < yEndSelf)
			return true;
		return false;
	}

	public void move(XY xy) {
		this.xy.move(xy);
	}

	public Movement getMovement() {
		return movement;
	}

	public Sprite clone() {
		try {
			return (Sprite) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	final static class Movement {
		Direction direction;
		Status status;

		Movement(Direction direction, Status status) {
			this.direction = direction;
			this.status = status;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((direction == null) ? 0 : direction.hashCode());
			result = prime * result + ((status == null) ? 0 : status.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Movement other = (Movement) obj;
			if (direction != other.direction)
				return false;
			if (status != other.status)
				return false;
			return true;
		}
	}

	public enum Direction {
		NORTH, WEST, EAST, SOUTH;
	}

	public enum Status {
		MOVE, STOP;
	}

}
