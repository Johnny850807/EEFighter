package model.words;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordRepositoryImp implements WordRepository {
	private Map<String, Word> words = new HashMap<String, Word>();
	
	public WordRepositoryImp() {
		readFile();
	}

	@Override
	public void addWord(Word word) {
		if (wordExistedOrNot(word.getWord()))
			return;
		words.put(word.getWord(), word);
		writeFile(words);
	}

	@Override
	public void removeWord(Word word) {
		if (!wordExistedOrNot(word.getWord()))
			return;
		words.remove(word.getWord());
		writeFile(words);
	}

	@Override
	public Word readWord(String wordtext) {
		if (wordExistedOrNot(wordtext))
			return words.get(wordtext);
		return null;
	}

	private boolean wordExistedOrNot(String wordtext) {
		if (words.containsKey(wordtext))
			return true;
		return false;
	}
	
	private void readFile() {
		try {
			FileReader fr = new FileReader("words.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] results = line.split(" ");
				String wordtext = results[0];
				Word word = new Word(wordtext);
				word.setSoundPath(results[1]);
				for (int i = 0; i < Integer.valueOf(results[2]); i++) {
					String[] define = br.readLine().split("/");
					for (int j = 1; j < define.length; j++)
						word.addDefinition(define[0], define[j]);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeFile(Map<String, Word> words) {
		try {
			FileWriter fw = new FileWriter("words.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (String w : words.keySet()) {
				Word word = words.get(w);
				Map<String, List<String>> definitions = word.getSentences();
				int keyAmount = definitions.keySet().size();
				
				bw.append(word.getWord() + " " + word.getSoundPath() + " " + keyAmount);
				bw.newLine();
				for (String partOfSpeech : definitions.keySet()) {
					bw.append(partOfSpeech);
					for (String definition : definitions.get(partOfSpeech))
						bw.append("/" + definition);
					bw.newLine();
				}
			}
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	

	}
}
