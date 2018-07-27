package model.packet.dto;

import model.Question;

public class NextQuestionEvent {
	public Question question;

	public NextQuestionEvent(Question question) {
		super();
		this.question = question;
	}
	
	
}
