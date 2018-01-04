package model.words;

import java.util.List;

public interface WordRepository {
	void addWord(Word word);

	Word readWord(String wordtext) throws ReadWordFailedException;

	List<Word> readAllWord() throws ReadWordFailedException;

	void removeWord(Word word);
}
