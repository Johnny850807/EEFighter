package model.sprite;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Question;

public class LetterPool {
	private int maxSize;
	private boolean log;
	private OutputStream outputStream;
	private List<Sprite> available = new ArrayList<>();

	public LetterPool(int maxSize) {
		this(maxSize, true);
	}

	public LetterPool(int maxSize, boolean log) {
		this(maxSize, log, System.out);
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
	public LetterPool(int maxSize, boolean log, OutputStream outputStream) {
		this.maxSize = maxSize;
		this.log = log;
		this.outputStream = outputStream;

		prepareSprite();
	}

	private void prepareSprite() {
		prepareBasicLetters();
		prepareExtraLetters();
	}

	private void prepareBasicLetters() {
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

	public void shuffleObjects(Question question) {
		System.out.println(question.getWord());
		Collections.shuffle(available);
		String[] questionLetters = question.getWord().split("");
		for (int i = 0; i < questionLetters.length; i++)
			for (int j = 0; j < available.size(); j++)
				if (available.get(j).spriteName.toString().equals(questionLetters[i])) {
					Sprite sprite = available.get(j);
					int index = (int) (Math.random() * (questionLetters.length * 2 + 10));
					available.set(j, available.get(index));
					available.set(index, sprite);
				}
	}

	private Sprite getAvailableObject() {
		Sprite nextObject = available.get(0);
		log("Available object returned: " + nextObject);
		available.remove(0);
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