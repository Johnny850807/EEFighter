package controller;

import java.io.IOException;
import java.util.List;

import model.Secret;
import model.words.Crawler;
import model.words.CrawlerVocabularycom;
import model.words.ITRI_TTS;
import model.words.ReadWordFailedException;
import model.words.TTS;
import model.words.Word;
import model.words.WordRepository;
import model.words.WordXMLRepository;
import ui.EnglishWarehouseView;
import ui.MainView;
import utils.SoundPlayer;

/**
 * @author Joanna (張書瑄)
 */
public class EnglishWarehouseController {

	private EnglishWarehouseView englishWarehouseView;
	private WordRepository wordRepository;
	private Crawler crawler;
	private TTS tts;
	
	public EnglishWarehouseController(WordRepository wordRepository, Crawler crawler, TTS tts) {
		this.wordRepository = wordRepository;  
		this.crawler = crawler;
		this.tts = tts;
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
				} catch (Exception e) {  //TODO 不要直接處理Exception 仔細去查看 每個例外被拋出的原因 仔細思考 哪些是可以一起處理的 哪些要分開
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
		EnglishWarehouseController controller = new EnglishWarehouseController(new WordXMLRepository("words"), new CrawlerVocabularycom(), new ITRI_TTS(Secret.TTS_ACCOUNT, Secret.TTS_PASSWORD));
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
