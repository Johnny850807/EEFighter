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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((definition == null) ? 0 : definition.hashCode());
		result = prime * result + number;
		result = prime * result + ((partOfSpeech == null) ? 0 : partOfSpeech.hashCode());
		result = prime * result + ((soundPath == null) ? 0 : soundPath.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (definition == null) {
			if (other.definition != null)
				return false;
		} else if (!definition.equals(other.definition))
			return false;
		if (number != other.number)
			return false;
		if (partOfSpeech != other.partOfSpeech)
			return false;
		if (soundPath == null) {
			if (other.soundPath != null)
				return false;
		} else if (!soundPath.equals(other.soundPath))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	
}
