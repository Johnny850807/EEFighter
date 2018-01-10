package model.sprite;

import java.util.ArrayList;
import java.util.List;

import model.MapConstants;

public class BasicMapDirector extends MapDirector{
	
	public BasicMapDirector(MapBuilder builder) {
		super(builder);
	}

	@Override
	public String[] createMapString() {
		List<String> mapString = new ArrayList<>();
		boolean startFromGrass = false;
		for (int i = 0 ; i < MapConstants.MAPHEIGHT ; i ++)
		{
			if (i % 2 == 0)
				mapString.add(produceGrassLine(MapConstants.MAPWIDTH));
			else
			{
				startFromGrass = !startFromGrass;
				if (startFromGrass)
					mapString.add(productCrossLine(MapItemName.GRASS, MapItemName.BLOCK, MapConstants.MAPWIDTH));
				else
					mapString.add(productCrossLine(MapItemName.BLOCK, MapItemName.GRASS, MapConstants.MAPWIDTH));
			}
		}
		return mapString.toArray(new String[MapConstants.MAPHEIGHT]);
	}
	
	private String produceGrassLine(int length){
		StringBuilder strb = new StringBuilder();
		for (int i = 0 ; i < length ; i ++)
			strb.append(MapItemName.GRASS.getNumber());
		return strb.toString();
	}
	
	private String productCrossLine(MapItemName startFrom, MapItemName another, int length){
		StringBuilder strb = new StringBuilder();
		for (int i = 0 ; i < length ; i ++)
		{
			MapItemName itemName = i % 2 == 0 ? startFrom : another;
			strb.append(itemName.getNumber());
		}
		return strb.toString();
	}
}
