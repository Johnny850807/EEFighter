package model.packet.dto;

import javax.swing.text.Position;

import model.sprite.SpriteName;

public class GameStartEvent {
	public SpriteName[][] spriteNameMapMatrix;
	public Position player1Position;
	public Position player2Position;
	
	public GameStartEvent(int width, int height){
		spriteNameMapMatrix = new SpriteName[height][width];
	}
}
