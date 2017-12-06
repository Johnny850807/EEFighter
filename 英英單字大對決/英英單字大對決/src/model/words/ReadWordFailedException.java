package model.words;

public class ReadWordFailedException extends Exception {
	
	public ReadWordFailedException(){}
	
	public ReadWordFailedException(Exception e) {
		super(e);
	}

	@Override
	public String getMessage() {
		return "read word failed";
	}
}
