package model.sprite;

import model.factory.SpritePrototypeFactory;

public class BasicMapBuilder implements MapBuilder {

	private GameMap gameMap = new GameMap(17, 9);
	private SpritePrototypeFactory spritePrototypeFactory = new SpritePrototypeFactory();

	@Override
	public void buildBarrier(int coordinateX, int coordinateY) {
		gameMap.setBlockSprite(coordinateX, coordinateY, spritePrototypeFactory.createSprite(SpriteName.BARRIER));
	}

	@Override
	public void buildRoad(int coordinateX, int coordinateY) {
		gameMap.setBlockSprite(coordinateX, coordinateY, spritePrototypeFactory.createSprite(SpriteName.GRASS));
	}

	@Override
	public GameMap build() {
		return gameMap;
	}

}
