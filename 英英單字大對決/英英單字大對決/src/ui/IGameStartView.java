package ui;

import java.util.List;

import model.Question;
import model.sprite.PlayerSprite;
import model.sprite.Sprite;

public interface IGameStartView {
	void onNextQuestion(Question question);
	void onPlayerEatLetter(String player, List<Sprite> letters);
	void onPlayerPopedLetter(String player, List<Sprite> letters);
	void onQuestionCorrect(PlayerSprite player);
	void onAnswerCorrectCleanLettersView();
	void showPlayerScore(String player, int Score);
}
