package model.words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Word {
	private String word;
	private String soundPath;
	private Map<PartOfSpeech, List<String>> definitions = new HashMap<PartOfSpeech, List<String>>();

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

	public Map<PartOfSpeech, List<String>> getDefinitions() {
		return definitions;
	}
	
	public String getDefinition(PartOfSpeech partOfSpeech) {
		Random random = new Random();
		List<String> definition = definitions.get(partOfSpeech);
		return definition.get(random.nextInt(definition.size()));
	}
	
	public void setDefinitions(Map<PartOfSpeech, List<String>> definitions) {
		this.definitions = definitions;
	}

	public void addDefinition(PartOfSpeech partOfSpeech, String definition) {
		if (!definitions.containsKey(partOfSpeech)) 
			definitions.put(partOfSpeech, new ArrayList<>());
		definitions.get(partOfSpeech).add(definition);
	}
	
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		strb.append(getWord()).append(", ").append(getSoundPath()).append(":\n");
		for (PartOfSpeech partOfSpeech : definitions.keySet())
		{
			strb.append("====   ").append(partOfSpeech).append("   ====\n");
			for (String definition : definitions.get(partOfSpeech))
				strb.append("    ").append(definition).append("\n");
		}
		return strb.toString();
	}
}
