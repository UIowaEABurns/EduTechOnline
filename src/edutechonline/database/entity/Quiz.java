package edutechonline.database.entity;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends ContentTopic {

	private List<Question> questions;
	public Quiz() {
		questions=new ArrayList<Question>();
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public void addQuestion(Question q) {
		this.questions.add(q);
	}
	
	
}
