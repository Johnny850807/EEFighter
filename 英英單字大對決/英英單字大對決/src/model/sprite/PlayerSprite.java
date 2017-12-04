package model.sprite;

import java.awt.Image;

import ui.GameView;

public class PlayerSprite extends Sprite{

	public PlayerSprite(int w, int h, int biasWithX, int biasWithY, int bodyHeight, int bodyLength,
			SpriteName spriteName, Image image) {
		super(w, h, biasWithX, biasWithY, bodyHeight, bodyLength, spriteName, image);
	}
	
	@Override
	public synchronized void update(GameMap gameMap, GameView gameView) {
		if (status == Status.MOVE) {
			switch (direction) {
				case UP:
					xy.move(0, -4);
					break;
				case DOWN:
					xy.move(0, 4);
					break;
				case LEFT:
					xy.move(-4, 0);
					break;
				case RIGHT:
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
		return gameMap.outOfMap(this) || gameMap.getSprite(xy.getX() / gameMap.ITEM_SIZE, xy.getY() / gameMap.ITEM_SIZE).getSpriteName() == spriteName.BARRIER;
	}

}
