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
	
	
}
