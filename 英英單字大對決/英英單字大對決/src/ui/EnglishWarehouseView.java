package ui;

import model.words.Word;

public interface EnglishWarehouseView {
	void onWordCreateSuccessfully(Word word);
	void onWordCreateFailed(String word);
}
