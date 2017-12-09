package model.sprite;

import java.util.List;

import model.Question;

public interface IGameStartView {
	void onNextQuestion(Question question);
	void onPlayerEatLetter(String player, List<Sprite> letters);
	void onPlayerPopedLetter(String player, List<Sprite> letters);
}
