package model.question;

public class Question {
	private String word;
	private String soundPath;
	private String partOfSpeech;
	private String definition;
	
	public Question(String word, String soundPath, String partOfSpeech, String definition) {
		super();
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

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public String getDefinition() {
		return definition;
	}

}
