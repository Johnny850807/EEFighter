package controller;

import model.Question;
import ui.GameView;

public class SoundPlayTimer extends Thread {
	
	private GameView gameView;
	private boolean isOver;
	private boolean windowClosed;
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
			if (!windowClosed && !isOver)
				gameView.onQuestionWordSoundPlay(question);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void questionChange() {
		isOver = true;
	}
	
	public void windowClosed() {
		windowClosed = true;
	}
	
}
