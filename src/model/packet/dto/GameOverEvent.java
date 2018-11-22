package model.packet.dto;

public class GameOverEvent {
	public byte winnerNo; // 1 = p1, 2 = p2
	public byte player1Score;
	public byte player2Score;
	public GameOverEvent(byte winnerNo, byte player1Score, byte player2Score) {
		super();
		this.winnerNo = winnerNo;
		this.player1Score = player1Score;
		this.player2Score = player2Score;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + player1Score;
		result = prime * result + player2Score;
		result = prime * result + winnerNo;
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
		GameOverEvent other = (GameOverEvent) obj;
		if (player1Score != other.player1Score)
			return false;
		if (player2Score != other.player2Score)
			return false;
		if (winnerNo != other.winnerNo)
			return false;
		return true;
	}
	
	
}
