package model.sprite;

import model.factory.SpritePrototypeFactory;

public class BasicMapBuilder implements MapBuilder {

	private GameMap gameMap = new GameMap(17, 9);
	private SpritePrototypeFactory spritePrototypeFactory = SpritePrototypeFactory.getInstance();

	@Override
	public void buildBarrier(int coordinateX, int coordinateY) {
		gameMap.setBlockSprite(coordinateX, coordinateY, spritePrototypeFactory.createSprite(SpriteName.TERRAIN));
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
