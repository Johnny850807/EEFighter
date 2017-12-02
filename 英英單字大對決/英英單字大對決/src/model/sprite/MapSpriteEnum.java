package model.sprite;

public enum MapSpriteEnum {
	GRASS('0'), BLOCK('1');

	private char number;

	private MapSpriteEnum(char number) {
		this.number = number;
	}

	public char getNumber() {
		return number;
	}

}
