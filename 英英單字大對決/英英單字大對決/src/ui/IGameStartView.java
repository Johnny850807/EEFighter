package ui;

import java.util.List;

import model.Question;
import model.sprite.Sprite;

public interface IGameStartView {
	void onNextQuestion(Question question);

	void onAnswerCorrectCleanLettersView();

	void showPlayerScore(String player, int Score);

	void onPlayerEatLetter(String answer, String string, int score, List<Sprite> letter);

	void onPlayerPopedLetter(String answer, String string, int score, List<Sprite> letter);
}
