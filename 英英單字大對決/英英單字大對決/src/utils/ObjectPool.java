package utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ObjectPool<T> {
	protected int maxSize;
	protected int minSize;
	protected int created = 0;
	protected boolean log;
	protected OutputStream outputStream;
	protected Set<T> available = new HashSet<>();
	
	
	public ObjectPool(int minSize, int maxSize){
		this(minSize, maxSize, true);
	}
	
	public ObjectPool(int minSize, int maxSize, boolean log){
		this(minSize, maxSize, log, System.out);
	}
	
	/**
	 * @param minSize the minSize determines the initially created object amount.
	 * @param maxSize the maxSize constrains the object pool holding specific number of reusable objects.
	 * @param log pass true if you want to enable the object pool logging.
	 * @param outputStream where the logging be sent to.
	 */
	public ObjectPool(int minSize, int maxSize, boolean log, OutputStream outputStream){
		if(minSize > maxSize)
			throw new IllegalArgumentException("The minSize of the object pool should not be greater than the maxSize.");
		this.maxSize = maxSize;
		this.minSize = minSize;
		this.log = log;
		this.outputStream = outputStream;
		
		prepareAvailableObjects();
	}
	
	private void prepareAvailableObjects() {
		List<Runnable> workers = new ArrayList<>();
		for (int i = 0 ; i < minSize ; i ++)  // parallelly creating
			workers.add(() -> available.add(createNewOne()));
		workers.forEach(r -> new Thread(r).start());
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
	
	public synchronized T get(){
		try{
			if (available.isEmpty())
			{
				if (created < maxSize)
					return createNewOne();
				waitForReleasing();
			}
			return getAvailableObject();
		}catch (InterruptedException err) {
			return null;
		}
	}
	
	private T createNewOne(){
		T newProduct = create();
		created ++;
		assert created <= maxSize;
		log("New object created: " + newProduct);
		return newProduct;
	}
	
	private void waitForReleasing() throws InterruptedException{
		log("Object pool is empty, waiting for releasing.");
		this.wait();  // wait for another thread releasing the object.
	}
	
	private T getAvailableObject(){
		T nextObject = available.iterator().next();
		log("Available object returned: " + nextObject);
		available.remove(nextObject);
		return nextObject;
	}
	
	protected void log(String message) {
		if (log)
			try {
				outputStream.write((message+"\n").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	protected abstract T create(); 
	
	public synchronized void release(T t){
		log("Object released: " + t);
		available.add(t);
		this.notify();
	}
}
