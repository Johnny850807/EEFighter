package model.sprite;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/*TODO
 * (1) SpriteName �ݩ�
 * (2) ��V�B���A
 * (3) Image ���ӭn�O�@��Map<��V, �Ϥ�>�A�C�Ӥ�V������@�i�Ϥ��A�����쫬�u�t�]�n�������ѨC��Sprite��Map<��V, �Ϥ�>�A�{���c���A�Ю������Ƴ����C
 */
public class Sprite implements Cloneable {
	private XY xy;
	private int w;
	private int h;
	private int biasWithX;
	private int biasWithY;
	private int bodyHeight;
	private int bodyLength;
	private SpriteName spriteName;
	private Direction direction;
	private Status status;
	private Map<Direction, Image> imageMap = new HashMap<>();
	private Image image;

	/**
	 * 
	 * @param w width of this sprite
	 * @param h height of this sprite
	 * @param biasWithX �Ϥ������P�Ϥ����骺���t��
	 * @param biasWithY �Ϥ��W��P�Ϥ����骺���t��
	 * @param bodyHeight �Ϥ������鳡��������
	 * @param bodyLength �Ϥ������鳡��������
	 * @param image image of this sprite
	 */
	public Sprite(int w, int h, int biasWithX, int biasWithY, int bodyHeight, int bodyLength, Image image) {
		super();
		this.w = w;
		this.h = h;
		this.biasWithX = biasWithX;
		this.biasWithY = biasWithY;
		this.bodyHeight = bodyHeight;
		this.bodyLength = bodyLength;
		this.image = image;
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

	public int getBodyLength() {
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
		this.status = status;
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	//�C17�L�� �̷ӥL����V�B���A�h��s�y��
	public void update() {

	}

	public void move(XY xy) {
		this.xy.move(xy);
	}

	public Sprite clone() {
		try {
			return (Sprite) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public enum Direction {
		UP, LEFT, RIGHT, DOWN;
	}
	
	public enum Status {
		MOVE, STOP;
	}

}
