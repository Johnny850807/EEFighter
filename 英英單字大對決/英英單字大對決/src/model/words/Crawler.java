package model.words;

public interface Crawler {
	Word crawlWordAndGetSentence(String wordSpelling) throws WordNotExistException;
}
