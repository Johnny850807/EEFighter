package controller;

import model.sprite.PlayerSprite;
import model.sprite.Sprite.Direction;
import model.sprite.Sprite.Status;
import ui.GameView;

public interface EEFighter {
	void setGameView(GameView gameView);
	void startGame();
	void move(PlayerSprite player, Direction direction, Status status);
	void nextQuestion();
	void popLetter(PlayerSprite player);
	void isLetterCollided(PlayerSprite player);
	void checkAnswer(PlayerSprite player);
}