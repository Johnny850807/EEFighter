package model.words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

	public String getSoundPath() {
		return soundPath;
	}

	public void setSoundPath(String soundPath) {
		this.soundPath = soundPath;
	}

	public Map<String, List<String>> getSentences() {
		return definitions;
	}
	
	public String getSentence(String partOfSpeech) {
		Random random = new Random();
		List<String> definition = definitions.get(partOfSpeech);
		return definition.get(random.nextInt(definition.size()));
	}
	
	public void setSentences(Map<String, List<String>> sentences) {
		this.definitions = sentences;
	}

	public void addDefinition(String partOfSpeech, String sentence) {
		if (!definitions.containsKey(partOfSpeech)) 
			definitions.put(partOfSpeech, new ArrayList<>());
		definitions.get(partOfSpeech).add(sentence);
	}
	
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		strb.append(getWord()).append(", ").append(getSoundPath()).append(":\n");
		for (String partOfSpeech : definitions.keySet())
		{
			strb.append("====   ").append(partOfSpeech).append("   ====\n");
			for (String definition : definitions.get(partOfSpeech))
				strb.append("    ").append(definition).append("\n");
		}
		return strb.toString();
	}
}
