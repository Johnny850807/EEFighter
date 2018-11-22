package model.packet.dto;

import java.util.Arrays;

public class LettersUpdatedEvent {
	public Letter[] letters;
	
	public LettersUpdatedEvent(){}

	public LettersUpdatedEvent(Letter[] letters) {
		super();
		this.letters = letters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(letters);
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
		LettersUpdatedEvent other = (LettersUpdatedEvent) obj;
		if (!Arrays.deepEquals(letters, other.letters))
			return false;
		return true;
	}
	
	
}
