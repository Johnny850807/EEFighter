package model.sprite;

import java.io.Serializable;

/**
 * @author WaterBall
 *
 */
public class XY implements Cloneable, Serializable{
	private XY lastXY;
	private int x;
	private int y;
	
	public XY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.lastXY = new XY(getX(), getY());
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.lastXY = new XY(getX(), getY());
		this.y = y;
	}
	
	/**
	 * @param x plus x
	 * @param y plus y
	 */
	public void move(int x, int y){
		move(new XY(x, y));
	}
	
	/**
	 * @param xy plus(x,y)
	 */
	public void move(XY xy){
		this.lastXY = new XY(getX(), getY());
		this.x += xy.getX();
		this.y += xy.getY();
	}
	
	/**
	 * move back to the last (x,y)
	 */
	public void rollback(){
		if(lastXY != null)
		{
			XY xy = new XY(lastXY.getX(), lastXY.getY());
			this.x = xy.getX();
			this.y = xy.getY();
		}
	}
	
	/**
	 * @return any of the x or y < 0.
	 */
	public boolean hasNegative(){
		return getX() < 0 || getY() < 0;
	}
	
	public XY getLastXY() {
		return lastXY;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		XY other = (XY) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + getX() + "," + getY() + ")";
	}
	
	public XY clone(){
		try {
			return (XY)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
