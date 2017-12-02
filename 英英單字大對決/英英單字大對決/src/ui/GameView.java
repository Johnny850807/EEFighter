package ui;

import model.sprite.GameMap;
import model.sprite.Sprite;

public interface GameView {
	void onDraw(GameMap gameMap, Sprite[] letters, Sprite player1, Sprite player2);
	void onClose();
}
