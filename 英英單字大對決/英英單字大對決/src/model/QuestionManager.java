package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.words.PartOfSpeech;
import model.words.ReadWordFailedException;
import model.words.Word;
import model.words.WordRepository;
import model.words.WordXMLRepository;

public class QuestionManager implements Iterable<Question> {
	private WordRepository wordRepository;
	private List<Question> questions = new ArrayList<>();
	private List<QuestionListener> questionListeners = new ArrayList<>();
	private int index = -1;

	public QuestionManager(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}

	public void addListener(QuestionListener questionListener) {
		questionListeners.add(questionListener);
	}

	public void prepareQuestions() {
		try {
			List<Word> words = wordRepository.readAllWord();
			Collections.shuffle(words);
			// int number = 0;
			for (int i = 0; i < 3; i++) {
				String wordtxt = words.get(i).getWord();
				String soundPath = words.get(i).getSoundPath();
				Map<PartOfSpeech, List<String>> definitions = words.get(i).getSentences();
				List<PartOfSpeech> partOfSpeechs = new ArrayList<>(definitions.keySet());
				PartOfSpeech partOfSpeech = partOfSpeechs.get(0);
				String definition = words.get(i).getSentence(partOfSpeech);
				Question question = new Question(i + 1, wordtxt, soundPath, partOfSpeech, definition);
				questions.add(question);
			}
			for (QuestionListener questionListener : questionListeners)
				questionListener.onQuestionPrepareFinish();
		} catch (ReadWordFailedException e) {
			e.printStackTrace();
		}
	}

	public int getIndex() {
		return index;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public Question getNowQuestion() {
		return questions.get(index);
	}

	public Question getNextQuestion() {
		if (index != questions.size() - 1)
			return questions.get(++index);
		throw new IllegalStateException("No more questions.");
	}

	public boolean hasNext() {
		return index != questions.size() - 1;
	}

	public int getAllQuestionAmount() {
		return questions.size();
	}

	@Override
	public Iterator<Question> iterator() {
		return questions.iterator();
	}

	public interface QuestionListener {
		void onQuestionPrepareFinish();
	}

	public static void main(String[] argv) {
		QuestionManager questionManager = new QuestionManager(new WordXMLRepository("words"));
		List<Question> questions = questionManager.getQuestions();
		for (Question question : questions) {
			System.out.println(question.getWord() + question.getPartOfSpeech() + question.getDefinition());
		}
	}

}
