package model.sprite;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Question;

public class LetterPool {
	private int maxSize;
	private boolean log;
	private OutputStream outputStream;
	private List<Sprite> available = new ArrayList<>();
	private Question question;

	public LetterPool(int maxSize, Question question) {
		this(maxSize, question, true);
	}

	public LetterPool(int maxSize, Question question, boolean log) {
		this(maxSize, question, log, System.out);
	}

	/**
	 * @param maxSize
	 *            the maxSize constrains the object pool holding specific number of
	 *            reusable objects.
	 * @param log
	 *            pass true if you want to enable the object pool logging.
	 * @param outputStream
	 *            where the logging be sent to.
	 */
	public LetterPool(int maxSize, Question question, boolean log, OutputStream outputStream) {
		this.maxSize = maxSize;
		this.question = question;
		this.log = log;
		this.outputStream = outputStream;

		prepareSprite();
	}

	private void prepareSprite() {
		prepareBasicLetters();
		prepareExtraLetters();
	}

	private void prepareBasicLetters() {
		System.out.println(question.getWord());
		SpriteName[] spriteNames = SpriteName.getLetterNames();
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < spriteNames.length; j++) {
				available.add(SpritePrototypeFactory.getInstance().createSprite(spriteNames[j]));
			}
	}

	private void prepareExtraLetters() {
		for (int i = 0; i < 2; i++) {
			Sprite sprite = SpritePrototypeFactory.getInstance().createSprite(SpriteName.A);
			available.add(sprite);
			sprite = SpritePrototypeFactory.getInstance().createSprite(SpriteName.E);
			available.add(sprite);
			sprite = SpritePrototypeFactory.getInstance().createSprite(SpriteName.I);
			available.add(sprite);
			sprite = SpritePrototypeFactory.getInstance().createSprite(SpriteName.O);
			available.add(sprite);
			sprite = SpritePrototypeFactory.getInstance().createSprite(SpriteName.T);
			available.add(sprite);
			sprite = SpritePrototypeFactory.getInstance().createSprite(SpriteName.N);
			available.add(sprite);
			sprite = SpritePrototypeFactory.getInstance().createSprite(SpriteName.S);
			available.add(sprite);
			sprite = SpritePrototypeFactory.getInstance().createSprite(SpriteName.H);
			available.add(sprite);
			sprite = SpritePrototypeFactory.getInstance().createSprite(SpriteName.R);
			available.add(sprite);
		}
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public synchronized Sprite get() {
		try {
			if (available.isEmpty())
				waitForReleasing();
			return getAvailableObject();
		} catch (InterruptedException err) {
			return null;
		}
	}

	private void waitForReleasing() throws InterruptedException {
		log("Object pool is empty, waiting for releasing.");
		this.wait(); // wait for another thread releasing the object.
	}

	private Sprite getAvailableObject() {
		Sprite nextObject = available.get((int) (Math.random() * available.size()));
		log("Available object returned: " + nextObject);
		available.remove(nextObject);
		System.out.println(available.size());
		return nextObject;
	}

	protected void log(String message) {
		if (log)
			try {
				outputStream.write((message + "\n").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public synchronized void release(Sprite t) {
		log("Object released: " + t);
		available.add(t);
		this.notify();
	}
}