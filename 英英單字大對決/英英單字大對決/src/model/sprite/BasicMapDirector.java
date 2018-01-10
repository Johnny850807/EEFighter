package model.sprite;

public class BasicMapDirector extends MapDirector{
	
	public BasicMapDirector(MapBuilder builder) {
		super(builder);
	}

	@Override
	public String[] createMapString() {
		return new String[] {"0000000000000000000000000",
							"0101010101010101010101010",
							"0000000000000000000000000",
							"1010101010101010101010101",
							"0000000000000000000000000",
							"0101010101010101010101010",
							"0000000000000000000000000",
							"1010101010101010101010101",
							"0000000000000000000000000"};
	}
	
}
