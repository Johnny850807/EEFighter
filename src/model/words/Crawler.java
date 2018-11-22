package model.words;

public interface Crawler {
	Word crawlWord(String wordSpelling) throws WordNotExistException;
}
