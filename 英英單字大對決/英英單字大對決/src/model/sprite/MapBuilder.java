package model.sprite;

public interface MapBuilder {
	void buildBarrier(int coordinateX, int coordinateY);
	
	void buildRoad(int coordinateX, int coordinateY);
	
	GameMap build();
}
