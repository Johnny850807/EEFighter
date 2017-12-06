package controller;

import java.io.IOException;
import java.util.List;

import model.Secret;
import model.words.CrawlerVocabularycom;
import model.words.ITRI_TTS;
import model.words.ReadWordFailedException;
import model.words.TTS;
import model.words.Word;
import model.words.WordRepository;
import model.words.WordRepositoryImp;
import model.words.WordXMLRepository;
import ui.EnglishWarehouseView;
import ui.EnglishWarehouseViewImp;
import ui.MainView;
import utils.SoundPlayer;

/**
 * @author Joanna (張書瑄)
 */
public class EnglishWarehouseController {

	private EnglishWarehouseView englishWarehouseView;
	private WordRepository wordRepository;
	private CrawlerVocabularycom crawler;
	private TTS tts;
	
	public EnglishWarehouseController() {
		wordRepository = new WordXMLRepository("words");  //TODO 這些都要依賴注入喔!!感覺到抽象工廠的重要性了吧~~ 
		crawler = new CrawlerVocabularycom();
		tts = new ITRI_TTS(Secret.TTS_ACCOUNT, Secret.TTS_PASSWORD);
	}
	
	public EnglishWarehouseController(EnglishWarehouseView englishWarehouseView) {
		this();
		this.englishWarehouseView = englishWarehouseView;
	}
	
	public void setEnglishWarehouseView(EnglishWarehouseView englishWarehouseView) {
		this.englishWarehouseView = englishWarehouseView;
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
					englishWarehouseView.onWordCreateFailed(wordtxt, e); 
				}
			}
		}.start();
	}
	
	public void readWord(String wordtext) throws ReadWordFailedException {
		new Thread() {
			@Override
			public void run() {
				Word word;
				try {
					word = wordRepository.readWord(wordtext);
					englishWarehouseView.onWordReadSuccessfully(word);
				} catch (ReadWordFailedException e) {
					e.printStackTrace();
					englishWarehouseView.onWordReadFailed(wordtext, e);
				}
			}
		}.start();
	}
	

	public void readAllWord(){
		new Thread() {
			@Override
			public void run() {
				List<Word> words;
				try {
					words = wordRepository.readAllWord();
					englishWarehouseView.onWordReadSuccessfully(words);
				} catch (ReadWordFailedException e) {
					e.printStackTrace();
					englishWarehouseView.onWordReadFailed(e);
				}
			}
		}.start();
	}
	
	public void removeWord(Word word) {
		new Thread() {
			@Override
			public void run() {
				wordRepository.removeWord(word);  
				englishWarehouseView.onWordRemoveSuccessfully(word);
			}
		}.start();
	}
	
	public static void main(String[] argv) throws IOException{
		String[] words = {"apple", "banana", "vocabulary", "subtropical", "several", "genus", "have", "terminal", "egg", 
				"small", "elephant",  "elder", "control", "model", "view", "stick", "fade", "still",
				"visible", "search", "customer", "play", "sound", "voice"};
		new MainView().setVisible(true);  //for playing sounds
		EnglishWarehouseController controller = new EnglishWarehouseController();
		controller.setEnglishWarehouseView(new EnglishWarehouseView() {
			int success = 0;
			@Override
			public void onWordRemoveSuccessfully(Word word) {}
			
			@Override
			public void onWordReadSuccessfully(List<Word> words) {
				for (Word word : words)
				{
					SoundPlayer.getInstance().playSound(word.getSoundPath());
					try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
			
			@Override
			public void onWordReadSuccessfully(Word word) {}
			
			@Override
			public void onWordReadFailed(Exception exception) {}
			
			@Override
			public void onWordReadFailed(String word, Exception exception) {
				System.out.println(word + ", read failed.");
			}
			
			@Override
			public void onWordCreateSuccessfully(Word word) {
				System.out.println("Successfully added : " + word);
				if(++success == words.length)  // all created completed
					controller.readAllWord();
			}
			
			@Override
			public void onWordCreateFailed(String word, Exception exception) {}
		}); 
		for(String word : words)
			controller.addWord(word);
	}
	
}
