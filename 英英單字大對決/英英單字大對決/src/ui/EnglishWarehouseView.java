package ui;

import model.words.Word;

public interface EnglishWarehouseView {
	void onWordCreateSuccessfully(Word word);
	void onWordCreateFailed(String word, Exception exception);
	void onWordReadSuccessfully(Word word);
	void onWordReadFailed(String word, Exception exception);
	void onWordRemoveSuccessfully(Word word);
}
