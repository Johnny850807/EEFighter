package model;

import model.words.PartOfSpeech;

public class Question {
	private int number;
	private String word;
	private String soundPath;
	private PartOfSpeech partOfSpeech;
	private String definition;

	public Question(int number, String word, String soundPath, PartOfSpeech partOfSpeech, String definition) {
		this.number = number;
		this.word = word;
		this.soundPath = soundPath;
		this.partOfSpeech = partOfSpeech;
		this.definition = definition;
	}

	public int getNumber() {
		return number;
	}

	public String getWord() {
		return word;
	}

	public String getSoundPath() {
		return soundPath;
	}

	public PartOfSpeech getPartOfSpeech() {
		return partOfSpeech;
	}

	public String getDefinition() {
		return definition;
	}

}
