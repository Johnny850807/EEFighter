package ui;

import java.util.List;

import model.words.Word;

public interface EnglishWarehouseView {
	void onWordCreateSuccessfully(Word word);

	void onWordCreateFailed(String word, Exception exception);

	void onWordNotExistCreateFailed(String word);

	void onWordReadSuccessfully(Word word);

	void onWordReadFailed(String word, Exception exception);

	void onWordReadSuccessfully(List<Word> words);

	void onWordReadFailed(Exception exception);

	void onWordRemoveSuccessfully(Word word);
}
