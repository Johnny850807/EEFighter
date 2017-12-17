package factory;

import controller.EEFighter;
import model.sprite.MapBuilder;
import model.sprite.MapDirector;
import model.words.Crawler;
import model.words.TTS;
import model.words.WordRepository;
import ui.GameView;
import ui.IGameStartView;

public interface AbstractFactory {
	IGameStartView getGameStartView();
	GameView getGameView();
	EEFighter getEeFighter();
	MapBuilder getMapBuilder();
	MapDirector getMapDirector();
	WordRepository getWordRepository();
	Crawler getCrawler();
	TTS getTts();
}
