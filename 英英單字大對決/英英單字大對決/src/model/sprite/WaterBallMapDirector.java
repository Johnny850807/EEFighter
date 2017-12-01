package model.sprite;


/**
 * @author WaterBall
 */
public class WaterBallMapDirector extends MapDirector{

	public WaterBallMapDirector(MapBuilder builder) {
		super(builder);
	}

	@Override
	public String[] createMapString() {
		return new String[] {"10100011011100000",
							 "10111000000101010",
							 "10000011010101011",
							 "11101011011000000",
							 "01101001011011101",
							 "00001111011011001",
							 "11011000000001100",
							 "00000011101010001",
							 "11011011111010101"};
	}

}
