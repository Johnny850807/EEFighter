package controller;

import java.io.IOException;
import java.util.List;

import factory.ComponentAbstractFactory;
import factory.ReleasedComponentAbstractFactory;
import model.Secret;
import model.words.Crawler;
import model.words.CrawlerVocabularycom;
import model.words.ITRI_TTS;
import model.words.ReadWordFailedException;
import model.words.TTS;
import model.words.TTSException;
import model.words.TTSProcessingException;
import model.words.Word;
import model.words.WordNotExistException;
import model.words.WordRepository;
import model.words.WordXMLRepository;
import ui.EnglishWarehouseView;
import ui.MainView;
import utils.SoundPlayer;

/**
 * @author Joanna (±i®ÑÞ±)
 */
public class EnglishWarehouseController {
	private EnglishWarehouseView englishWarehouseView;
	private WordRepository wordRepository;
	private Crawler crawler;
	private TTS tts;
	
	public EnglishWarehouseController(ComponentAbstractFactory componentAbstractFactory) {
		wordRepository = componentAbstractFactory.getWordRepository();  
		crawler = componentAbstractFactory.getCrawler();
		tts = componentAbstractFactory.getTts();
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
				} catch (TTSException e) {
					e.printStackTrace();
					englishWarehouseView.onWordCreateFailed(wordtxt, e); 
				} catch (WordNotExistException e) {
					englishWarehouseView.onWordNotExistCreateFailed(wordtxt); 
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
	
	
}
