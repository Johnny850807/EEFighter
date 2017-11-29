package model.sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ietf.jgss.Oid;

/**
 * @author WaterBall
 */
public abstract class TemplateRandomMapDirector extends MapDirector{
	private final static int NORTH = 0;
	private final static int EAST = 1;
	private final static int SOUTH = 2;
	private final static int WEST = 3;
	protected XY[] directionDiffs = new XY[]{new XY(0, -1), new XY(1, 0), new XY(0, 1), new XY(-1, 0)}; // N-E-S-W
	private char[][] mapString;
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
	
	protected Mouse createMouse(){
		return new Mouse(new XY(0, 0));
	}
	
	protected abstract void startMove(Mouse mouse);

	protected char[][] createFullBarriersMapString(){
		char[][] mapString = new char[9][17];
		for (int i = 0 ; i < 9 ; i ++)
			for(int j = 0 ; j < 17 ; j ++)
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
			if (outOfMap(this.xy) || hasRoadInRound())
			{
				this.xy.rollback();
				return false;
			}
			return true;
		}
		
		public boolean outOfMap(XY xy){
			return this.xy.hasNegative() || this.xy.getX() >= 17  || this.xy.getY() >= 9;
		}
		
		public boolean hasRoadInRound(){
			XY origin = xy.clone();
			XY north = xy.clone();
			north.move(directionDiffs[NORTH]);
			XY east = xy.clone();
			east.move(directionDiffs[EAST]);
			XY south = xy.clone();
			south.move(directionDiffs[SOUTH]);
			XY west = xy.clone();
			west.move(directionDiffs[WEST]);
			XY[] roundsXY = new XY[]{origin, north, east, south, west};
			for (XY xy : roundsXY)
				if (!xy.hasNegative() && outOfMap(xy))
				if (!this.xy.getLastXY().equals(xy) && mapString[xy.getY()][xy.getX()] == '0')
					return true;
			return false;
		}
		
		public XY getRandomDirectionDiff(){
			int randInt = new Random().nextInt(directionDiffs.length);
			return directionDiffs[randInt];
		}
		
		public boolean successfullyRandomMove(boolean opposite){
			boolean success = false;
			int count = 0;
			XY randomXY = null;
			do{
				XY oppositeXY = xy.getLastXY() == null ? null : new XY(xy.getLastXY().getX() * -1, xy.getLastXY().getY() * -1);
				randomXY = getRandomDirectionDiff();
				if (opposite && oppositeXY != null && oppositeXY.equals(randomXY))
					continue;
				success = move(randomXY);
			} while (!success && ++count < 1000);
			System.out.println(randomXY);
			return true;
		}
		
		public Mouse sculpture(char type){
			mapString[xy.getY()][xy.getX()] = type;
			return this;
		}
		
		public boolean goNorth(){
			return move(directionDiffs[NORTH]);
		}
		
		public boolean goEast(){
			return move(directionDiffs[EAST]);
		}
		
		public boolean goSouth(){
			return move(directionDiffs[SOUTH]);
		}
		
		public boolean goWest(){
			return move(directionDiffs[WEST]);
		}
		
		public String[] buildNormalizedMap(){
			String[] product = new String[9];
			for (int i = 0 ; i < 9 ; i ++)
			{
				StringBuilder strb = new StringBuilder();
				for(int j = 0 ; j < 17 ; j ++)
					strb.append(mapString[i][j]);
				product[i] = strb.toString();
			}
			return product;
		}
	}
}
