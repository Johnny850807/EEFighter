package model.words;

public interface TTS {
	String saveWordTTS(String directoryPath, String word) throws TTSException;
}
