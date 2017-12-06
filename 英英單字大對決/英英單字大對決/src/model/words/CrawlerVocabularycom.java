package model.words;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerVocabularycom implements Crawler{
	
	@Override
	public Word crawlWordAndGetSentence(String wordSpelling) throws WordNotExistException {
		final String LINK = "https://www.vocabulary.com/dictionary/" + wordSpelling;
		try {
			Document doc = Jsoup.connect(LINK).get();
			Elements definitions = doc.select("h3.definition");
			
			Word word = new Word(wordSpelling);
			int i = 1;
			if (definitions.isEmpty()) 
				throw new WordNotExistException(wordSpelling);
			for (Element element : definitions) {
				String[] e = element.text().split(" ", 2);
				e[0].toUpperCase();
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
		String wordtext = "acrgillj";
		WordRepository wordRepository = new WordRepositoryImp();
		CrawlerVocabularycom c = new CrawlerVocabularycom();
		Word word;
		try {
			word = c.crawlWordAndGetSentence(wordtext);
			word.setSoundPath("fjii");
			wordRepository.addWord(word);
			wordRepository.removeWord(word);
			System.out.println(wordRepository.readWord("despair").getSentences());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
