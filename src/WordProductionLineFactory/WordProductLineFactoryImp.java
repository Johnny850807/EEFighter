package WordProductionLineFactory;

import model.words.Crawler;
import model.words.VocabularycomCrawler;
import model.words.ReadWordFailedException;
import model.words.TTS;
import model.words.TTSException;
import model.words.Word;
import model.words.WordNotExistException;
import model.words.WordProductionLineFactory;
import model.words.WordRepository;

public class WordProductLineFactoryImp extends WordProductionLineFactory{
	private TTS tts;
	private Crawler crawler;
	private WordRepository wordRepository;
	
	public WordProductLineFactoryImp(TTS tts, Crawler crawler, WordRepository wordRepository) {
		super();
		this.tts = tts;
		this.crawler = crawler;
		this.wordRepository = wordRepository;
	}

	@Override
	protected String createTtsFileAndGetPath(String wordTxt) throws TTSException{
		return tts.saveWordTTS("sounds/vocabulary", wordTxt);
	}

	@Override
	protected Word crawlWord(String wordTxt) throws WordNotExistException{
		return crawler.crawlWord(wordTxt);
	}

	@Override
	protected Word storeWord(Word word){
		wordRepository.addWord(word);
		return word;
	}
	
	@Override
	protected boolean hasTTS() {
		return true;
	}

}
