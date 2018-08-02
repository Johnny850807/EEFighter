package model.packet.dto;

import java.awt.Point;

import model.sprite.SpriteName;

public class Letter{
	public Point point;
	public SpriteName letterName;
	
	
	public Letter() {
		super();
	}


	public Letter(Point point, SpriteName letterName) {
		super();
		this.point = point;
		this.letterName = letterName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((letterName == null) ? 0 : letterName.hashCode());
		result = prime * result + ((point == null) ? 0 : point.hashCode());
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
		Letter other = (Letter) obj;
		if (letterName != other.letterName)
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}
	
	
	
}
