package model.packet.dto;

public class CheckAnswerRequest {
	public byte playerNo;

	public CheckAnswerRequest(byte playerNo) {
		super();
		this.playerNo = playerNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		CheckAnswerRequest other = (CheckAnswerRequest) obj;
		if (playerNo != other.playerNo)
			return false;
		return true;
	}
	
	
}
