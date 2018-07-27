package model.packet.dto;

public class PlayerLettersUpdatedEvent {
	public byte playerNo;
	public String letters;
	public PlayerLettersUpdatedEvent(byte playerNo, String letters) {
		super();
		this.playerNo = playerNo;
		this.letters = letters;
	}
	
	
}
