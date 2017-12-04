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

/**
 * @author Joanna (張書瑄)
 */
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
					System.out.println(e.getMessage());  //TODO printStackTrace 就會印出來了! 不用再這樣印~~
					englishWarehouseView.onWordCreateFailed(wordtxt);  //TODO 例外也要給他喔~ 也許它需要
				}
			}
		}.start();
	}
	
	public Word readWord(String wordtext) throws ReadWordFailedException {
		return wordRepository.readWord(wordtext);  //TODO 這個也是要時間的唷!! 所以一樣要開執行緒跟呼叫觀察者~~~ 你可以開始了解 所有有延遲的操作都要非同步!
	}
	
	public void removeWord(Word word) {
		wordRepository.removeWord(word);  //TODO 非同步~~~ crud基本上都要對應到一個觀察者函數!!! 不要客氣!!
		englishWarehouseView.onWordRemoveSuccessfully(word);
	}
	
	public static void main(String[] argv) {
		new EnglishWarehouseController(new EnglishWarehouseViewImp()).addWord("apple");
	}
	
}
