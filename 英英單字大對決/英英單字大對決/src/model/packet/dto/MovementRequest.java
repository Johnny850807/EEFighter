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
	
	
}
