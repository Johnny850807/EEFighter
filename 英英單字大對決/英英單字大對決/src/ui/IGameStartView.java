package ui;

import java.util.List;

import model.Question;
import model.sprite.PlayerSprite;
import model.sprite.Sprite;

public interface IGameStartView {
	void onNextQuestion(Question question);

	void onAnswerCorrectCleanLettersView(PlayerSprite player1, PlayerSprite player2);

	void showPlayerScore(String player, int Score);

	void onPlayerEatLetter(String answer, String string, int score, List<Sprite> letter);

	void onPlayerPopLetter(String answer, String string, int score, List<Sprite> letter);
}
