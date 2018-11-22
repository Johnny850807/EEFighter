package model.sprite;

public enum MapItemName {
	TERRAIN('0'), BARRIER('1');

	private char number;

	private MapItemName(char number) {
		this.number = number;
	}

	public char getNumber() {
		return number;
	}

}
