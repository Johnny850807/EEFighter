package controller;

import java.util.List;

import factory.ComponentAbstractFactory;
import model.words.Crawler;
import model.words.ReadWordFailedException;
import model.words.TTS;
import model.words.TTSException;
import model.words.Word;
import model.words.WordNotExistException;
import model.words.WordProductionLineFactory;
import model.words.WordRepository;
import ui.EnglishWarehouseView;

/**
 * @author Joanna (±i®ÑÞ±)
 */
public class EnglishWarehouseController {
	private EnglishWarehouseView englishWarehouseView;
	private WordProductionLineFactory wordProductionLineFactory;
	private WordRepository wordRepository;
	
	public EnglishWarehouseController(ComponentAbstractFactory componentAbstractFactory) {
		this.wordProductionLineFactory = componentAbstractFactory.getWordProductionLineFactory();
		this.wordRepository = componentAbstractFactory.getWordRepository();
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
					word = wordProductionLineFactory.createWord(wordtxt);
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
