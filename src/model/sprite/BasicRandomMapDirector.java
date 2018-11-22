package model.sprite;

import static model.MapConstants.MAPHEIGHT;
import static model.MapConstants.MAPWIDTH;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasicRandomMapDirector extends TemplateRandomMapDirector{
	private Random random = new Random();
	private List<XY> traceRoadXYs = new ArrayList<>();
	
	public BasicRandomMapDirector(MapBuilder builder) {
		super(builder);
	}
	
	
	@Override
	protected Mouse createMouse() {
		XY xy = new XY(random.nextInt(MAPWIDTH), random.nextInt(MAPHEIGHT));
		traceRoadXYs.add(xy);
		return new Mouse(xy);
	}
	

	@Override
	protected void startMove(Mouse mouse) {
		do{
			System.out.println("Reselect entry.");
			mouse.setXy(getNextRandomAliveXY()); 
			mouse.sculpture('0');
			while(!isDieEntry(mouse.getXy()))
			{
				XY randomDiff = getRandomAvailableDirectionDiff(mouse.getXy());
				mouse.move(randomDiff);
				traceRoadXYs.add(mouse.getXy().clone());
				mouse.sculpture('0');
			}
			filterAndRemoveDieEntries();
		}while(traceRoadXYs.size() > 0);
		
	}
	
	private XY getNextRandomAliveXY(){
		int randInt = random.nextInt(traceRoadXYs.size());
		return traceRoadXYs.get(randInt);
	}
	
	public XY getRandomDirectionDiff(){
		int randInt = new Random().nextInt(directionDiffs.length);
		return directionDiffs[randInt];
	}
	
	public XY getRandomAvailableDirectionDiff(XY xy){
		List<XY> diffs = getAvailableDirectionDiffs(xy);
		int size = diffs.size();
		if (size == 0)
			throw new IllegalStateException("No available direction.");
		return diffs.get(random.nextInt(size));
	}
	
	public List<XY> getAvailableDirectionDiffs(XY xy){
		List<XY> diffs = new ArrayList<>();
		for (XY diff : directionDiffs)
		{
			XY test = xy.clone();
			test.move(diff);
			if(!nearRoad(test, xy))
				diffs.add(diff);
		}
		return diffs;
	}
	
	
	private void filterAndRemoveDieEntries(){
		List<XY> removeList = new ArrayList<>();
		for (XY xy : traceRoadXYs)
			if (isDieEntry(xy))
				removeList.add(xy);
		this.traceRoadXYs.removeAll(removeList);
	}
	
	private boolean isDieEntry(XY xy){
		List<XY> besideXYs = getBesideXYs(xy);
		for (XY eachBesideXY : besideXYs)
			if (!outOfMap(eachBesideXY) && !isRoad(eachBesideXY) && !nearRoad(eachBesideXY, xy))
				return false;
		return true;
	}
	
	/**
	 * @param xy
	 * @return return false if the place (X,Y) does not near any sculptured road but the place where XY moved from.
	 */
	private boolean nearRoad(XY xy, XY from){
		List<XY> besideXYs = getBesideXYs(xy);
		
		/*Continue to the next XY if it's out of map, otherwise first check if it the place that the XY moved from,
		if not then check whether there's a sculptured road.*/
		for (XY eachBesideXY : besideXYs)
			if (!outOfMap(eachBesideXY) && !eachBesideXY.equals(from) && isRoad(eachBesideXY))
				return true;
		return false;
	}
	
	private List<XY> getBesideXYs(XY xy){
		List<XY> besideXYs = new ArrayList<>();
		XY north = xy.clone();  // get four XYs beside the passed xy upon four directions.
		north.move(directionDiffs[NORTH]);
		XY east = xy.clone();
		east.move(directionDiffs[EAST]);
		XY south = xy.clone();
		south.move(directionDiffs[SOUTH]);
		XY west = xy.clone();
		west.move(directionDiffs[WEST]);
		besideXYs.add(north);
		besideXYs.add(east);
		besideXYs.add(south);
		besideXYs.add(west);
		return besideXYs;
	}
	
	public boolean isRoad(XY xy){
		return mapString[xy.getY()][xy.getX()] == '0';
	}
	
	public boolean outOfMap(XY xy){
		return xy.hasNegative() || xy.getX() >= MAPWIDTH  || xy.getY() >= MAPHEIGHT;
	}
}
