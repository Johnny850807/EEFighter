package model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerVocabularycom implements Crawler{
	
	@Override
	public Word crawlWordAndGetSentence(String wordSpelling) {
		final String LINK = "https://www.vocabulary.com/dictionary/" + wordSpelling;
		try {
			Document doc = Jsoup.connect(LINK).get();
			Elements definitions = doc.select("h3.definition");
			
			Word word = new Word(wordSpelling);
			int i = 1;
			for (Element element : definitions) {
				String[] e = element.text().split(" ", 2);
				String partOfSpeech = e[0];
				String sentence = e[1];
				word.addDefinition(partOfSpeech, sentence);
			}
			System.out.println(word.getSentences().entrySet());
			return word;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		CrawlerVocabularycom c = new CrawlerVocabularycom();
		c.crawlWordAndGetSentence("volunteer");
	}

}
