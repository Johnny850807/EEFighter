package model.words;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
		if (!wordExists(word.getWord())) {
			words.put(word.getWord(), word);
			writeFile(words);
		}
	}

	@Override
	public void removeWord(Word word) {
		if (wordExists(word.getWord())) {
			words.remove(word.getWord());
			writeFile(words);
		}
	}

	@Override
	public Word readWord(String wordtext) throws ReadWordFailedException {
		if (wordExists(wordtext))
			return words.get(wordtext);
		throw new ReadWordFailedException();
	}

	@Override
	public List<Word> readAllWord() throws ReadWordFailedException {
		return new ArrayList(this.words.values());
	}

	private boolean wordExists(String wordtext) {
		return words.containsKey(wordtext); 
	}
	
	private void readFile() {
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader("words.txt");
			br = new BufferedReader(fr);
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
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void writeFile(Map<String, Word> words) {
		BufferedWriter bw = null;
		try {
			FileWriter fw = new FileWriter("words.txt");
			bw = new BufferedWriter(fw);
			
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
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}

}
