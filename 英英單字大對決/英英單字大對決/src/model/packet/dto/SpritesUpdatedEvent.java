package model.packet.dto;

import java.util.List;

import javax.swing.text.Position;

import model.sprite.SpriteName;

public class SpritesUpdatedEvent {
	public Position player1Position;
	public Position player2Position;
	public List<PlacedLetter> placedLetters;
	
	public SpritesUpdatedEvent(){}
	
	public SpritesUpdatedEvent(Position player1Position, Position player2Position, List<PlacedLetter> placedLetters) {
		super();
		this.player1Position = player1Position;
		this.player2Position = player2Position;
		this.placedLetters = placedLetters;
	}



	public static class PlacedLetter{
		public Position position;
		public SpriteName letterName;
	}
}
