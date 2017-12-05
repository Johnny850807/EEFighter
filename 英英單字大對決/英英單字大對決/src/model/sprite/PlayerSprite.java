package model.sprite;

import java.awt.Image;
import java.util.Map;

import model.sprite.Sprite.Direction;
import ui.GameView;

public class PlayerSprite extends Sprite{
	
	public PlayerSprite(int w, int h, int biasWithX, int biasWithY, int bodyHeight, int bodyLength,
			SpriteName spriteName, Map<Direction, Image> imageMap) {
		super(w, h, biasWithX, biasWithY, bodyHeight, bodyLength, spriteName, imageMap);
	}
	
	@Override
	public synchronized void update() {
		System.out.println(direction.toString() + "," + status.toString());
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
	
	@Override
	public synchronized boolean isCollisions() {
		return super.isCollisions();
	}
	
	private boolean moveFailed(GameMap gameMap){
		return gameMap.outOfMap(this) || gameMap.getSprite(xy.getX() / gameMap.ITEM_SIZE, xy.getY() / gameMap.ITEM_SIZE).getSpriteName() == spriteName.TERRAIN;
	}

}
