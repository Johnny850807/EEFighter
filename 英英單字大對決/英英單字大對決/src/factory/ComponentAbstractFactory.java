package factory;

import controller.EEFighter;
import model.sprite.MapBuilder;
import model.sprite.MapDirector;
import model.words.Crawler;
import model.words.TTS;
import model.words.WordProductionLineFactory;
import model.words.WordRepository;

public interface ComponentAbstractFactory {
	MapBuilder createMapBuilder();

	MapDirector createMapDirector();

	WordRepository getWordRepository();

	Crawler getCrawler();

	TTS getTts();
	
	EEFighter createEEFighter();
	
	WordProductionLineFactory getWordProductionLineFactory();
}