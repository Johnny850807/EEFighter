package utils;

import java.util.List;

import model.sprite.Sprite;

public class SpriteLogHelper {
	public static String toString(List<Sprite> sprites) {
		StringBuilder strb = new StringBuilder();
		for (Sprite letter : sprites)
			strb.append(letter.getSpriteName() + " ");
		return strb.toString();
	}
}
