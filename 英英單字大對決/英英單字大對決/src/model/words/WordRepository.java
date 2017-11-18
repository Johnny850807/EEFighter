package model.words;
public interface WordRepository {
	void addWord(Word word);
	Word readWord(String wordtext);
	void removeWord(Word word);
}
