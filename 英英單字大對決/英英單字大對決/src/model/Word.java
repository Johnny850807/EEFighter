package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word {
	private String word;
	private String soundPath;
	// TODO 詞性改成列舉
	private Map<String, List<String>> definitions = new HashMap<String, List<String>>();
	
	public Word(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getSound() {
		return soundPath;
	}

	public void setSound(String soundPath) {
		this.soundPath = soundPath;
	}

	public Map<String, List<String>> getSentences() {
		return definitions;
	}

	public void setSentences(Map<String, List<String>> sentences) {
		this.definitions = sentences;
	}

	public void addDefinition(String partOfSpeech, String sentence) {
		if (definitions.get(partOfSpeech) == null) {
			List sentencelist = new ArrayList<>();
			sentencelist.add(sentence);
			definitions.put(partOfSpeech, sentencelist);
		}
		else {
			definitions.get(partOfSpeech).add(sentence);
		}
	}
}
