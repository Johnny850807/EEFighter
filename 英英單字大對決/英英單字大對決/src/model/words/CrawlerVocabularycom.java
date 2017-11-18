package model.words;

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
		String wordtext = "volunteer";
		CrawlerVocabularycom c = new CrawlerVocabularycom();
		Word word = c.crawlWordAndGetSentence(wordtext);
		Word word2 = c.crawlWordAndGetSentence("despair");
		word.setSoundPath("fjii");
		word2.setSoundPath("wdtetwew");
		WordFileManager wordFileManager = WordFileManager.getInstance();
		wordFileManager.addWordToFile(word);
		wordFileManager.addWordToFile(word2);
		System.out.println(wordFileManager.readWordFromFile("despair").getSentences());
	}

}
