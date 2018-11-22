package model.words;

public interface TTS {
	/**
	 * @param directoryPath the directory path where to save the output file.
	 * @param word the word text to be converted.
	 * @return the path of the sound file.
	 * @throws TTSException if the TTS API is not enabled now.
	 */
	String saveWordTTS(String directoryPath, String word) throws TTSException;
}
