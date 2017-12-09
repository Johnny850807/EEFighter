package model.sprite;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.sprite.Sprite.Direction;
import ui.GameView;

public class PlayerSprite extends Sprite{
	
	private List<Sprite> letters = new ArrayList<>();  //TODO 換更適合的資料結構
	
	public PlayerSprite(int w, int h, int biasWithX, int biasWithY, int bodyHeight, int bodyLength,
			SpriteName spriteName, Map<Direction, Image> imageMap) {
		super(w, h, biasWithX, biasWithY, bodyHeight, bodyLength, spriteName, imageMap);
	}
	
	@Override
	public synchronized void update() {
		if (status == Status.MOVE) {
			switch (direction) {
				case NORTH:
					xy.move(0, -4);
					break;
				case SOUTH:
					xy.move(0, 4);
					break;
				case WEST:
					xy.move(-4, 0);
					break;
				case EAST:
					xy.move(4, 0);
					break;
				default:
					break;
			}	
		}
		if (moveFailed(gameMap)) {
			xy.rollback();
			gameView.onHitWall(this);
		}
	}
	
	private boolean moveFailed(GameMap gameMap){
		if (gameMap.outOfMap(this))
			return true;
		for (Sprite terrain : gameMap.getAllTerrains()) 
			if (terrain.isCollisions(this))
				return true;
		return false;
	}

	public void addLetter(Sprite sprite) {
		letters.add(sprite);
	}
	
	public void removeLetter(Sprite sprite) {
		letters.remove(sprite);
	}
	
	public Sprite getLastLetter() {
		if (!letters.isEmpty()) 
			return letters.get(letters.size() - 1);
		return null;
	}
	
	public List<Sprite> getLetters() {
		return letters;
	}
	
}
