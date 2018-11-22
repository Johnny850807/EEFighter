package model.packet.dto;

import java.util.Arrays;

public class PlayerLettersUpdatedEvent {
	public byte playerNo;
	public Letter[] letters;
	
	
	public PlayerLettersUpdatedEvent(byte playerNo, Letter[] letters) {
		super();
		this.playerNo = playerNo;
		this.letters = letters;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(letters);
		result = prime * result + playerNo;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerLettersUpdatedEvent other = (PlayerLettersUpdatedEvent) obj;
		if (!Arrays.equals(letters, other.letters))
			return false;
		if (playerNo != other.playerNo)
			return false;
		return true;
	}
	
	
	
	
}
