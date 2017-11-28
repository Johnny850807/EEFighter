package model.sprite;

public class BasicMapDirector extends MapDirector{
	
	public BasicMapDirector(MapBuilder builder) {
		super(builder);
	}

	@Override
	public String[] createMapString() {
		return new String[] {"00000000000000000",
							"01010101010101010",
							"00000000000000000",
							"10101010101010101",
							"00000000000000000",
							"01010101010101010",
							"00000000000000000",
							"10101010101010101",
							"00000000000000000"};
	}
	
}
