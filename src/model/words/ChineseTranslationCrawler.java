package model.words;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ChineseTranslationCrawler implements Crawler{
	private static HashMap<String, PartOfSpeech> POSMAP = new HashMap<>();
	
	static{
		POSMAP.put("noun", PartOfSpeech.N);
		POSMAP.put("adjective", PartOfSpeech.ADJ);
		POSMAP.put("verb", PartOfSpeech.V);
		POSMAP.put("adverb", PartOfSpeech.ADV);
		POSMAP.put("conjunction", PartOfSpeech.CONJ);
		POSMAP.put("preposition", PartOfSpeech.PREPOSITION);
	}
	
	@Override
	public Word crawlWord(String wordSpelling) throws WordNotExistException {
		//https://dictionary.cambridge.org/zht/µü¨å/­^»y-º~»y-ÁcÅé/present
		final String LINK = "https://dictionary.cambridge.org/zht/%E8%A9%9E%E5%85%B8/%E8%8B%B1%E8%AA%9E-%E6%BC%A2%E8%AA%9E-%E7%B9%81%E9%AB%94/" + wordSpelling;
		try {
			Document doc = Jsoup.connect(LINK).get();
			Elements definitions = doc.select("div.entry-body__el");
			
			Word word = new Word(wordSpelling);
			if (definitions.isEmpty()) 
				throw new WordNotExistException(wordSpelling);
			for (Element element : definitions) {
				String pos = element.select("span.pos").first().text().trim().toLowerCase();
				String translation = element.select("span.trans").first().text();
				word.addDefinition(POSMAP.get(pos), translation);
			}
			return word;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] argv) throws WordNotExistException{
		Crawler crawler = new ChineseTranslationCrawler();
		Word word = crawler.crawlWord("present");
		System.out.println(word);
	}
}
