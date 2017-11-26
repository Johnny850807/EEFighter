package model.sprite;

public class GameMap {
	private int row = 10;
	private int columns = 10;
	private int[][] mapNumber = new int[10][10];
	
	public void recordMap() {
		for (int i = 0; i < row; i++)
			for(int j = 0; j < columns; j++)
				mapNumber[i][j] = (int)(Math.random() * 2);
	}

	public int[][] getMapNumber() {
		return mapNumber;
	}
	
}
