package model.sprite;

public abstract class MapDirector {
	protected static final int MAPWIDTH = 17;
	protected static final int MAPHEIGHT = 9;
	private MapBuilder builder;

	public MapDirector(MapBuilder builder) {
		this.builder = builder;
	}

	public abstract String[] createMapString();

	public GameMap buildMap() {
		String[] mapString = createMapString();

		for (int i = 0; i < mapString.length; i++)
			for (int j = 0; j < mapString[i].length(); j++) {
				char number = mapString[i].charAt(j);
				if (number == MapItemName.BLOCK.getNumber())
					builder.buildBarrier(j, i);
				else if (number == MapItemName.GRASS.getNumber())
					builder.buildRoad(j, i);
			}
		return builder.build();
	};

}
