package model.packet.dto;

import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;

public class MovementRequest {
	public byte playerNo;
	public Direction direction;
	public Status status;
	public MovementRequest(byte playerNo, Direction direction, Status status) {
		super();
		this.playerNo = playerNo;
		this.direction = direction;
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + playerNo;
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
		MovementRequest other = (MovementRequest) obj;
		if (direction != other.direction)
			return false;
		if (playerNo != other.playerNo)
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	
}
