package model.packet.dto;

public class AnswerCommitResultEvent {
	public byte playerNo;
	public boolean answerCorrect;
	
	public AnswerCommitResultEvent(byte playerNo, boolean answerCorrect) {
		super();
		this.playerNo = playerNo;
		this.answerCorrect = answerCorrect;
	}
	
	
}
