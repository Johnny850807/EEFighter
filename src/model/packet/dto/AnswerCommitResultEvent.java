package model.packet.dto;

public class AnswerCommitResultEvent {
	public byte playerNo;
	public boolean answerCorrect;
	
	public AnswerCommitResultEvent(byte playerNo, boolean answerCorrect) {
		super();
		this.playerNo = playerNo;
		this.answerCorrect = answerCorrect;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (answerCorrect ? 1231 : 1237);
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
		AnswerCommitResultEvent other = (AnswerCommitResultEvent) obj;
		if (answerCorrect != other.answerCorrect)
			return false;
		if (playerNo != other.playerNo)
			return false;
		return true;
	}
	
	
}
