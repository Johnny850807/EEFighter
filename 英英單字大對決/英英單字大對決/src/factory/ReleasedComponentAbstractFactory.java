package factory;

import controller.EEFighter;
import controller.EEFighterImp;
import model.Secret;
import model.sprite.BasicMapBuilder;
import model.sprite.BasicMapDirector;
import model.sprite.BasicRandomMapDirector;
import model.sprite.MapBuilder;
import model.sprite.MapDirector;
import model.sprite.WaterBallMapDirector;
import model.words.Crawler;
import model.words.CrawlerVocabularycom;
import model.words.ITRI_TTS;
import model.words.TTS;
import model.words.WordRepository;
import model.words.WordXMLRepository;

public class ReleasedComponentAbstractFactory implements ComponentAbstractFactory {
	private WordRepository wordRepository;
	private Crawler crawler;
	private TTS tts;
	
	public ReleasedComponentAbstractFactory() {
		crawler = new CrawlerVocabularycom();
		tts = new ITRI_TTS(Secret.TTS_ACCOUNT, Secret.TTS_PASSWORD);
		wordRepository = new WordXMLRepository("wordwarehouse");
	}

	@Override
	public MapBuilder createMapBuilder() {
		return new BasicMapBuilder();
	}

	@Override
	public MapDirector createMapDirector() {
		return new BasicRandomMapDirector(createMapBuilder());
	}

	@Override
	public WordRepository getWordRepository() {
		return wordRepository;
	}

	@Override
	public Crawler getCrawler() {
		return crawler;
	}

	@Override
	public TTS getTts() {
		return tts;
	}

	@Override
	public EEFighter createEEFighter() {
		return new EEFighterImp(this);
	}
	
}
