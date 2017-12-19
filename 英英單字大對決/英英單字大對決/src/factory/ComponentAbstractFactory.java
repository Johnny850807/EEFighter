package factory;

import model.sprite.MapBuilder;
import model.sprite.MapDirector;
import model.words.Crawler;
import model.words.TTS;
import model.words.WordRepository;

public interface ComponentAbstractFactory {
	MapBuilder getMapBuilder();

	MapDirector getMapDirector();

	WordRepository getWordRepository();

	Crawler getCrawler();

	TTS getTts();

}