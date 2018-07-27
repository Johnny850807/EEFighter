package model.packet.dto;

public class PlayVoiceEvent {
	public String word;
	public String soundPath;
	
	public PlayVoiceEvent(String word, String soundPath) {
		super();
		this.word = word;
		this.soundPath = soundPath;
	}
}
