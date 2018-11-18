package model.words;

public abstract class WordProductionLineFactory {
	public Word createWord(String wordTxt) throws WordNotExistException, TTSException {
		Word word = crawlWord(wordTxt);
		if (hasTTS()) {
			String ttsPath = createTtsFileAndGetPath(wordTxt);
			word.setSoundPath(ttsPath);
		}
		return storeWord(word);
	}

	protected abstract String createTtsFileAndGetPath(String wordTxt) throws TTSException;
	protected abstract Word crawlWord(String wordTxt) throws WordNotExistException;
	protected abstract Word storeWord(Word word);
	
	protected boolean hasTTS(){
		/*hook*/
		return true;
	}
}
