package model.sprite;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ui.GameView;
import javax.imageio.ImageIO;

/*TODO
 * (1) Image ���ӭn�O�@��Map<��V, �Ϥ�>�A�C�Ӥ�V������@�i�Ϥ��A�����쫬�u�t�]�n�������ѨC��Sprite��Map<��V, �Ϥ�>�A�{���c���A�Ю������Ƴ����C
 * (2) ���� isConflict(Sprite sprite) ���  �P�_��sprite�O�_���I���o�� �^�� boolean
 */
public class Sprite implements Cloneable {
	protected XY xy;
	protected int w;
	protected int h;
	protected int biasWithX;
	protected int biasWithY;
	protected int bodyHeight;
	protected int bodyLength;
	protected SpriteName spriteName;
	protected Direction direction = Direction.UP;
	protected Status status = Status.STOP;
	protected Map<Direction, Image> imageMap = new HashMap<>();
	protected Image image;

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
	public Sprite(int w, int h, int biasWithX, int biasWithY, int bodyHeight, int bodyLength,SpriteName spriteName, Image image) {
		super();
		this.w = w;
		this.h = h;
		this.biasWithX = biasWithX;
		this.biasWithY = biasWithY;
		this.bodyHeight = bodyHeight;
		this.bodyLength = bodyLength;
		this.spriteName = spriteName;
		this.image = image;
		try {
			prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prepare() throws IOException{
		imageMap.put(Direction.UP, ImageIO.read(new File("pic/North_T0.png")));
		imageMap.put(Direction.LEFT, ImageIO.read(new File("pic/West_T0.png")));
		imageMap.put(Direction.DOWN, ImageIO.read(new File("pic/South_T0.png")));
		imageMap.put(Direction.RIGHT, ImageIO.read(new File("pic/East_T0.png")));
	}

	public Image getDirectionImage(Direction direction) {
		return imageMap.get(direction);
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
		this.status = status;
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	//TODO ���n�b�I�supdate���ɭԤ~�ǤJ gameMap��gameView�A�󤣥�setter ����set�i�o��sprite���ݩʩO? �S���]�N�C
	public synchronized void update(GameMap gameMap, GameView gameView) {
		//do nothing as default
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
