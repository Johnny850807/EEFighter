package factory;

import controller.EEFighter;
import controller.EEFighterImp;
import model.Secret;
import model.sprite.BasicMapBuilder;
import model.sprite.BasicMapDirector;
import model.sprite.MapBuilder;
import model.sprite.MapDirector;
import model.words.Crawler;
import model.words.CrawlerVocabularycom;
import model.words.ITRI_TTS;
import model.words.TTS;
import model.words.WordRepository;
import model.words.WordXMLRepository;
import ui.GameStartView;
import ui.GameView;
import ui.GameViewImp;
import ui.IGameStartView;

public class MockComponentFactory implements ComponentAbstractFactory {
	private WordRepository wordRepository;
	private Crawler crawler;
	private TTS tts;
	private MapBuilder mapBuilder;
	private MapDirector mapDirector;
	private IGameStartView gameStartView;
	private GameView gameView;
	private EEFighter eeFighter;

	public MockComponentFactory() {
		crawler = new CrawlerVocabularycom();
		tts = new ITRI_TTS(Secret.TTS_ACCOUNT, Secret.TTS_PASSWORD);
		wordRepository = new WordXMLRepository("wordwarehouse");
		mapBuilder = new BasicMapBuilder();
		mapDirector = new BasicMapDirector(mapBuilder);
		eeFighter = new EEFighterImp(this);
		gameStartView = new GameStartView(this);
		gameView = new GameViewImp(this);
	}

	public IGameStartView getGameStartView() {
		if (gameStartView == null)
			return new GameStartView(this);
		return gameStartView;
	}

	public GameView getGameView() {
		if (gameView == null)
			return new GameViewImp(this);
		return gameView;
	}

	public EEFighter getEeFighter() {
		if (eeFighter == null)
			return new EEFighterImp(this);
		return eeFighter;
	}

	public MapBuilder getMapBuilder() {
		return mapBuilder;
	}

	public MapDirector getMapDirector() {
		return mapDirector;
	}

	public WordRepository getWordRepository() {
		return wordRepository;
	}

	public Crawler getCrawler() {
		return crawler;
	}

	public TTS getTts() {
		return tts;
	}

}
