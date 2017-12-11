package model;

import model.words.PartOfSpeech;

public class Question {
	private String word;
	private String soundPath;
	private PartOfSpeech partOfSpeech;
	private String definition;
	
	public Question(String word, String soundPath, PartOfSpeech partOfSpeech, String definition) {
		this.word = word;
		this.soundPath = soundPath;
		this.partOfSpeech = partOfSpeech;
		this.definition = definition;
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
