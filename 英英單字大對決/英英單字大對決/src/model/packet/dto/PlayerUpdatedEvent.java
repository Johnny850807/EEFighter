package model.packet.dto;

public class PlayerUpdatedEvent {
	public byte playerNo;
	public Player player;
	public PlayerUpdatedEvent(byte playerNo, Player player) {
		super();
		this.playerNo = playerNo;
		this.player = player;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
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
		PlayerUpdatedEvent other = (PlayerUpdatedEvent) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (playerNo != other.playerNo)
			return false;
		return true;
	}
	
	
}
