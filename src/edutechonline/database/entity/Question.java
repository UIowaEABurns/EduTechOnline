package edutechonline.database.entity;

import java.util.ArrayList;
import java.util.List;

public class Question extends IDEntity {
	private String text;
	private List<Answer> answers;
	private int quizId;
	public Question() {
		answers=new ArrayList<Answer>();
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	public void addAnswer(Answer a) {
		this.answers.add(a);
	}
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	
	public Answer getCorrectAnswer() {
		for(Answer a : answers) {
			if (a.isCorrect()) {
				return a;
			}
		}
		return null;
	}
}
