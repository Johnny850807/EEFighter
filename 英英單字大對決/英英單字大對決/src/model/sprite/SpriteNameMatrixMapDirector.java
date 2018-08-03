package model.sprite;

public class SpriteNameMatrixMapDirector extends MapDirector{
	private SpriteName[][] spriteNameMatrix;
	
	public SpriteNameMatrixMapDirector(MapBuilder builder, SpriteName[][] spriteNameMatrix) {
		super(builder);
		this.spriteNameMatrix = spriteNameMatrix;
	}

	@Override
	public String[] createMapString() {
		String[] mapString = new String[spriteNameMatrix.length];
		for(int i = 0; i < spriteNameMatrix.length; i ++)
		{
			StringBuilder stringBuilder = new StringBuilder();
			for(int j = 0; j < spriteNameMatrix[0].length; j ++)
				stringBuilder.append((getMapItemNumber(j, i) - 48));
			mapString[i] = stringBuilder.toString();
		}
		return mapString;
	}
	
	private int getMapItemNumber(int x, int y){
		return spriteNameMatrix[y][x] == SpriteName.BARRIER ? MapItemName.BARRIER.getNumber()
				: spriteNameMatrix[y][x] == SpriteName.TERRAIN ? MapItemName.TERRAIN.getNumber()
				: -999;
	}

}
