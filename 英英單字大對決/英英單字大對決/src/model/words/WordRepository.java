package model.words;
public interface WordRepository {
	void addWord(Word word);
	Word readWord(String wordtext) throws ReadWordFailedException;
	void removeWord(Word word);
}
