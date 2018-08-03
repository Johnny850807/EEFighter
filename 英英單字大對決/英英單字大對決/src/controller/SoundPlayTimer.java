package controller;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import model.Question;
import ui.GameView;

public class SoundPlayTimer extends Thread {
	private Consumer<Question> soundPlayObserver;
	private boolean isOver;
	private boolean windowClosed;
	private Question question;

	public SoundPlayTimer(GameView gameView, Question question, 
			Consumer<Question> soundPlayObserver) {
		this.soundPlayObserver = soundPlayObserver;
		this.question = question;
		isOver = false;
	}

	@Override
	public void run() {
		try {
			do {
				Thread.sleep(30000);
				soundPlayObserver.accept(question);
			} while (!windowClosed && !isOver);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void over() {
		isOver = true;
	}

	public void stopCounting() {
		windowClosed = true;
	}

}
