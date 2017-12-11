package model.sprite;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.QuestionManger;

public class LetterPool {
	protected int maxSize;
	protected int minSize;
	protected int created = 0;
	protected boolean log;
	protected OutputStream outputStream;
	protected Set<Sprite> available = new HashSet<>();
	private QuestionManger questionManger;

	public LetterPool(int minSize, int maxSize, QuestionManger questionManger) {
		this(minSize, maxSize, true);
		this.questionManger = questionManger;
	}

	public LetterPool(int minSize, int maxSize, boolean log) {
		this(minSize, maxSize, log, System.out);
	}

	/**
	 * @param minSize
	 *            the minSize determines the initially created object amount.
	 * @param maxSize
	 *            the maxSize constrains the object pool holding specific number of
	 *            reusable objects.
	 * @param log
	 *            pass true if you want to enable the object pool logging.
	 * @param outputStream
	 *            where the logging be sent to.
	 */
	public LetterPool(int minSize, int maxSize, boolean log, OutputStream outputStream) {
		if (minSize > maxSize)
			throw new IllegalArgumentException(
					"The minSize of the object pool should not be greater than the maxSize.");
		this.maxSize = maxSize;
		this.minSize = minSize;
		this.log = log;
		this.outputStream = outputStream;

//		prepareAvailableObjects();
	}

//	private void prepareAvailableObjects() {
//		List<Integer> workers = new ArrayList<>();
//		for (int i = 0; i < minSize; i++) // parallelly creating
//			workers.add(i);
//		workers.parallelStream().forEach(i -> available.add(createNewOne()));
//	}

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
			if (available.isEmpty()) {
				if (created < maxSize)
					return createNewOne();
				waitForReleasing();
			}
			return getAvailableObject();
		} catch (InterruptedException err) {
			return null;
		}
	}

	private Sprite createNewOne() {
		Sprite newProduct = create();
		created++;
		assert created <= maxSize;
		log("New object created: " + newProduct);
		return newProduct;
	}

	private void waitForReleasing() throws InterruptedException {
		log("Object pool is empty, waiting for releasing.");
		this.wait(); // wait for another thread releasing the object.
	}

	private Sprite getAvailableObject() {
		Sprite nextObject = available.iterator().next();
		log("Available object returned: " + nextObject);
		available.remove(nextObject);
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

	protected Sprite create() {
		SpriteName[] spriteNames = SpriteName.getLetterNames();
		Sprite sprite = SpritePrototypeFactory.getInstance().createSprite(spriteNames[(int) (Math.random() * 26)]);
		available.add(sprite);
		return sprite;
	}

	public synchronized void release(Sprite t) {
		log("Object released: " + t);
		available.add(t);
		this.notify();
	}
}