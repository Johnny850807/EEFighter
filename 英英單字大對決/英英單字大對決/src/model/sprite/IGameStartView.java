package model.sprite;

import model.Question;

public interface IGameStartView {
	void onNextQuestion(Question question);
	void onPlayerEatLetter(String player, Sprite letter);
	void onPlayerPopedLetter(String player, Sprite letter);
}
