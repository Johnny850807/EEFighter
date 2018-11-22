package model.packet.dto;

import java.awt.Point;
import java.util.Arrays;

import model.sprite.SpriteName;

public class GameStartEvent {
	public SpriteName[][] spriteNameMapMatrix;
	public Point player1Point;
	public Point player2Point;
	
	public GameStartEvent(SpriteName[][] spriteNameMapMatrix, Point player1Point, Point player2Point) {
		super();
		this.spriteNameMapMatrix = spriteNameMapMatrix;
		this.player1Point = player1Point;
		this.player2Point = player2Point;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player1Point == null) ? 0 : player1Point.hashCode());
		result = prime * result + ((player2Point == null) ? 0 : player2Point.hashCode());
		result = prime * result + Arrays.deepHashCode(spriteNameMapMatrix);
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
		GameStartEvent other = (GameStartEvent) obj;
		if (player1Point == null) {
			if (other.player1Point != null)
				return false;
		} else if (!player1Point.equals(other.player1Point))
			return false;
		if (player2Point == null) {
			if (other.player2Point != null)
				return false;
		} else if (!player2Point.equals(other.player2Point))
			return false;
		if (!Arrays.deepEquals(spriteNameMapMatrix, other.spriteNameMapMatrix))
			return false;
		return true;
	}

	
	
}
