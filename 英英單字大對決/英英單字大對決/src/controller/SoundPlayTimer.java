package controller;

import model.Question;
import ui.GameView;

public class SoundPlayTimer extends Thread {
	
	private GameView gameView;
	private boolean isOver;
	private Question question;
	
	public SoundPlayTimer(GameView gameView, Question question) {
		this.gameView = gameView;
		this.question = question;
		isOver = false;
	}
	

	@Override
	public void run() {
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (!isOver) {
			gameView.onQuestionWordSoundPlay(question);
		}
	}
	
	public void questionChange() {
		isOver = true;
	}
	
}
