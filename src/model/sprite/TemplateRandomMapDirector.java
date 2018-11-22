package model.sprite;

import static model.MapConstants.MAPHEIGHT;
import static model.MapConstants.MAPWIDTH;

/**
 * @author WaterBall
 */
public abstract class TemplateRandomMapDirector extends MapDirector{
	protected final static int NORTH = 0;
	protected final static int EAST = 1;
	protected final static int SOUTH = 2;
	protected final static int WEST = 3;
	protected XY[] directionDiffs = new XY[]{new XY(0, -1), new XY(1, 0), new XY(0, 1), new XY(-1, 0)}; // N-E-S-W
	protected char[][] mapString;
	private Mouse mouse;  // the (x,y) where the mouse starts.
	
	public TemplateRandomMapDirector(MapBuilder builder) {
		super(builder);
	}
	
	
	@Override
	public String[] createMapString() {
		mapString = createFullBarriersMapString();
		mouse = createMouse();
		startMove(mouse);
		String[] product =  mouse.buildNormalizedMap();
		return product;
	}
	
	protected abstract Mouse createMouse();
	
	protected abstract void startMove(Mouse mouse);

	protected char[][] createFullBarriersMapString(){
		char[][] mapString = new char[MAPHEIGHT][MAPWIDTH];
		for (int i = 0 ; i < MAPHEIGHT ; i ++)
			for(int j = 0 ; j < MAPWIDTH ; j ++)
				mapString[i][j] = '1';
		return mapString;
	}
	
	protected class Mouse{
		private XY xy;
		
		public Mouse(XY xy) {
			this.xy = xy;
		}

		/**
		 * @param xy the difference of the move on x and y
		 * @return if the move successfully, return false means the move will make the mouse out of the map.
		 */
		public boolean move(XY xy){
			this.xy.move(xy);
			if (outOfMap(this.xy))
			{
				this.xy.rollback();
				return false;
			}
			System.out.println(this.xy);
			return true;
		}
		
		public boolean backToSamePlace(XY xy) {
			return mapString[xy.getY()][xy.getX()] == '0';
		}
		
		public boolean outOfMap(XY xy){
			return xy.hasNegative() || xy.getX() >= MAPWIDTH  || xy.getY() >= MAPHEIGHT;
		}
		
		public Mouse sculpture(char type){
			mapString[xy.getY()][xy.getX()] = type;
			return this;
		}
		
		
		public String[] buildNormalizedMap(){
			String[] product = new String[MAPHEIGHT];
			for (int i = 0 ; i < MAPHEIGHT ; i ++)
			{
				StringBuilder strb = new StringBuilder();
				for(int j = 0 ; j < MAPWIDTH ; j ++)
					strb.append(mapString[i][j]);
				product[i] = strb.toString();
			}
			return product;
		}
		
		public void setXy(XY xy){
			this.xy = xy;
		}
		
		public XY getXy() {
			return xy;
		}
	}
}
