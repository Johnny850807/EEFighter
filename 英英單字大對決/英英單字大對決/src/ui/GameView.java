package ui;

import java.util.List;

import model.sprite.GameMap;
import model.sprite.Sprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;

public interface GameView {
	void onDraw(GameMap gameMap, List<Sprite> letters, Sprite player1, Sprite player2);
	void onGameStarted();
	void onGameOver();
	void onGameClose();
	void start();
	void onMovedSuccessfuly(Sprite sprite, Direction direction, Status status);
}
