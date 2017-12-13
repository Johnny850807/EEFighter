package controller;

import model.Question;
import ui.GameView;

public class SoundPlayTimer extends Thread {
	
	private GameView gameView;
	private int timeCount;
	private boolean isOver;
	private Question question;
	
	public SoundPlayTimer(GameView gameView, Question question) {
		this.gameView = gameView;
		this.question = question;
		timeCount = 0;
		isOver = false;
	}
	

	@Override
	public void run() {
		while (!isOver && timeCount < 50) {
			try {
				Thread.sleep(1000);
				if (timeCount == 50)
					gameView.onQuestionWordSoundPlay(question);
				timeCount++;
				System.out.println(timeCount);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void questionChange() {
		isOver = true;
	}
	
}
