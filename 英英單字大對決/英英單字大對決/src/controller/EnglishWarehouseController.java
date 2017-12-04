package controller;

import model.Secret;
import model.words.CrawlerVocabularycom;
import model.words.ITRI_TTS;
import model.words.ReadWordFailedException;
import model.words.TTS;
import model.words.Word;
import model.words.WordRepository;
import model.words.WordRepositoryImp;
import ui.EnglishWarehouseView;
import ui.EnglishWarehouseViewImp;

public class EnglishWarehouseController {

	private EnglishWarehouseView englishWarehouseView;
	private WordRepository wordRepository;
	private CrawlerVocabularycom crawler;
	private TTS tts;
	
	public EnglishWarehouseController(EnglishWarehouseView englishWarehouseView) {
		this.englishWarehouseView = englishWarehouseView;
		wordRepository = new WordRepositoryImp();
		crawler = new CrawlerVocabularycom();
		tts = new ITRI_TTS(Secret.TTS_ACCOUNT, Secret.TTS_PASSWORD);
	}

	public void addWord(String wordtxt) {
		new Thread() {
			@Override
			public void run() {
				Word word;
				try {
					word = crawler.crawlWordAndGetSentence(wordtxt);
					String path = tts.saveWordTTS("sounds/vocabulary", wordtxt);
					word.setSoundPath(path);
					wordRepository.addWord(word);
					englishWarehouseView.onWordCreateSuccessfully(word);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
					englishWarehouseView.onWordCreateFailed(wordtxt);
				}
			}
		}.start();
	}
	
	public Word readWord(String wordtext) throws ReadWordFailedException {
		return wordRepository.readWord(wordtext);
	}
	
	public void removeWord(Word word) {
		wordRepository.removeWord(word);
		englishWarehouseView.onWordRemoveSuccessfully(word);
	}
	
	public static void main(String[] argv) {
		new EnglishWarehouseController(new EnglishWarehouseViewImp()).addWord("apple");
	}
	
}
