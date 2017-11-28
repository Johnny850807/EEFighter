package model.sprite;

import org.ietf.jgss.Oid;

/**
 * @author WaterBall
 */
public class BasicRandomMapDirector extends MapDirector{
	private final static int NORTH = 0;
	private final static int EAST = 1;
	private final static int SOUTH = 2;
	private final static int WEST = 3;
	private XY[] directionDiffs = new XY[]{new XY(0, -1), new XY(1, 0), new XY(0, 1), new XY(-1, 0)}; // N-E-S-W
	private Mouse mouse;  // the (x,y) where the mouse starts.
	
	public BasicRandomMapDirector(MapBuilder builder) {
		super(builder);
	}
	
	
	
	@Override
	public String[] createMapString() {
		String[] mapString = createFullBarriersMapString();
		mouse = createMouse(mapString);
		startMove(mouse);
		return mapString;
	}
	
	protected Mouse createMouse(String[] mapString){
		return new Mouse(new XY(0, 0), mapString);
	}
	
	protected void startMove(Mouse mouse){
		
	}

	private String[] createFullBarriersMapString(){
		String[] mapString = new String[9];
		for (int i = 0 ; i < 9 ; i ++)
			mapString[i] = "11111111111111111";
		return mapString;
	}
	
	protected class Mouse{
		private XY xy;
		private String[] mapString;
		
		public Mouse(XY xy, String[] mapString) {
			this.xy = xy;
			this.mapString = mapString;
		}

		public void move(XY xy){
			this.xy.move(xy);
		}
		
		public void sculpture(String type){
			//mapString[xy.getY()][xy.getX()] = type;
		}
	}
}
