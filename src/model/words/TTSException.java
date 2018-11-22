package model.words;

public class TTSException extends Exception{
	public TTSException(String status){
		super("Status got: " + status + ", the tts api does not succeed.");
	}
}
