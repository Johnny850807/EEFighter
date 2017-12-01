package model.sprite;

import java.awt.Image;

public class Sprite {
	private XY xy;
	private int w;
	private int h;
	private Image image;
	
	public Sprite(int x, int y, int w, int h, Image image) {
		super();
		this.xy = new XY(x, y);
		this.w = w;
		this.h = h;
		this.image = image;
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
	
	public void update() {
		
	}
	
	public void move(XY xy){
		this.xy.move(xy);
	}
}
