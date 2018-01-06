package model.words;

public class WordNotExistException extends Exception {
	public WordNotExistException(String word) {
		super("word: " + word + " doesn't exist");
	}
}