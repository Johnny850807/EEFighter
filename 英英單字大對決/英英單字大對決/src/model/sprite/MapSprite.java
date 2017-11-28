package model.sprite;

public enum MapSprite {
	GRASS('0'), BLOCK('1');

	private char number;

	private MapSprite(char number) {
		this.number = number;
	}

	public char getNumber() {
		return number;
	}
	
}
