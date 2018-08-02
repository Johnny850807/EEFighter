package model.packet.dto;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.Position;

import model.sprite.SpriteName;

public class SpritesUpdatedEvent {
	public Point player1Point;
	public Point player2Point;
	public Letter[] letters;
	
	public SpritesUpdatedEvent(){}
	
	public SpritesUpdatedEvent(Point player1Point, Point player2Point, Letter[] letters) {
		super();
		this.player1Point = player1Point;
		this.player2Point = player2Point;
		this.letters = letters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(letters);
		result = prime * result + ((player1Point == null) ? 0 : player1Point.hashCode());
		result = prime * result + ((player2Point == null) ? 0 : player2Point.hashCode());
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
		SpritesUpdatedEvent other = (SpritesUpdatedEvent) obj;
		if (!Arrays.equals(letters, other.letters))
			return false;
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
		return true;
	}
	
	
}
