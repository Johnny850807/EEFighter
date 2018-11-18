package model.words;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VocabularycomCrawler implements Crawler{
	
	@Override
	public Word crawlWord(String wordSpelling) throws WordNotExistException {
		final String LINK = "https://www.vocabulary.com/dictionary/" + wordSpelling;
		try {
			Document doc = Jsoup.connect(LINK).get();
			Elements definitions = doc.select("h3.definition");
			
			Word word = new Word(wordSpelling);

			if (definitions.isEmpty()) 
				throw new WordNotExistException(wordSpelling);
			for (Element element : definitions) {
				String[] e = element.text().split(" ", 2);
				e[0].toUpperCase();
				String partOfSpeech = e[0];
				String sentence = e[1];
				word.addDefinition(PartOfSpeech.valueOf(partOfSpeech.toUpperCase()), sentence);
			}
			return word;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
