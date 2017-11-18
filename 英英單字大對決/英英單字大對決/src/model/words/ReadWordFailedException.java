package model.words;

public class ReadWordFailedException extends Exception {

	@Override
	public String getMessage() {
		return "read word failed";
	}
}
