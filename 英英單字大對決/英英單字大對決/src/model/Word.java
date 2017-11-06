package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word {
	private String word;
	private String soundPath;
	private Map<String, List<String>> sentences = new HashMap<String, List<String>>();
	
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
		return sentences;
	}

	public void setSentences(Map<String, List<String>> sentences) {
		this.sentences = sentences;
	}

	public void addDefinition(String partOfSpeech, String sentence) {
		if (sentences.get(partOfSpeech) == null) {
			List sentencelist = new ArrayList<>();
			sentencelist.add(sentence);
			sentences.put(partOfSpeech, sentencelist);
		}
		else {
			sentences.get(partOfSpeech).add(sentence);
		}
	}
}
