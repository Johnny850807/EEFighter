package model.packet.dto;

public class PlayerReadyRequest {
	public byte playerNo;

	public PlayerReadyRequest(byte playerNo) {
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
		PlayerReadyRequest other = (PlayerReadyRequest) obj;
		if (playerNo != other.playerNo)
			return false;
		return true;
	}
	
	
}
