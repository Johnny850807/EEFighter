package model.sprite;

public enum MapItemName {
	GRASS('0'), BLOCK('1');

	private char number;

	private MapItemName(char number) {
		this.number = number;
	}

	public char getNumber() {
		return number;
	}

}
