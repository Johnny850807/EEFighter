package model.words;

import java.util.HashMap;
import java.util.Map;

public class WordRepositoryImp implements WordRepository {
	private WordFileManager wordFileManager = WordFileManager.getInstance();
	private Map<String, Word> words = new HashMap<String, Word>();

	@Override
	public void addWord(Word word) {
		addWordToFile(word);
	}

	@Override
	public void removeWord(Word word) {
		deleteWordToFile(word);
	}

	@Override
	public Word readWord(String wordtext) {
		return readWordFromFile(wordtext);
	}

	private boolean wordExistedOrNot(String wordtext) {
		if (words.containsKey(wordtext))
			return true;
		return false;
	}
	
	public void addWordToFile(Word word) {
		if (wordExistedOrNot(word.getWord()))
			return;
		words.put(word.getWord(), word);
		wordFileManager.writeFile(words);
	}
	
	public void deleteWordToFile(Word word) {
		if (!wordExistedOrNot(word.getWord()))
			return;
		words.remove(word.getWord());
		wordFileManager.writeFile(words);
	}
	
	public Word readWordFromFile(String wordtext) {
		if (wordExistedOrNot(wordtext))
			return words.get(wordtext);
		return null;
	}
	
}
