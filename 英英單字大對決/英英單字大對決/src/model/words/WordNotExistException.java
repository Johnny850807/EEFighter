package model.words;

public class WordNotExistException extends Exception {

	@Override
	public String getMessage() {
		return "word doesn't exist";
	}
}