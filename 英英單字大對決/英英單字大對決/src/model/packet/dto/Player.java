package model.packet.dto;

import java.awt.Point;

import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;

public class Player {
	public Point point;
	public Direction direction;
	public Status status;
	
	public Player(Point point, Direction direction, Status status) {
		super();
		this.point = point;
		this.direction = direction;
		this.status = status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((point == null) ? 0 : point.hashCode());
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
		Player other = (Player) obj;
		if (direction != other.direction)
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	
}
