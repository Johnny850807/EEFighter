package factory;

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

public class ComponentAbstractFactoryImp implements ComponentAbstractFactory {
	private WordRepository wordRepository;
	private Crawler crawler;
	private TTS tts;
	private MapBuilder mapBuilder;
	private MapDirector mapDirector;
	
	public ComponentAbstractFactoryImp() {
		crawler = new CrawlerVocabularycom();
		tts = new ITRI_TTS(Secret.TTS_ACCOUNT, Secret.TTS_PASSWORD);
		wordRepository = new WordXMLRepository("wordwarehouse");
	}

	@Override
	public MapBuilder createMapBuilder() {
		return mapBuilder = new BasicMapBuilder();
	}

	@Override
	public MapDirector createMapDirector() {
		return mapDirector = new BasicMapDirector(mapBuilder);
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
	
}
