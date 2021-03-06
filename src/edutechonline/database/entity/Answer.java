package edutechonline.database.entity;

public class Answer extends IDEntity {
	private String text;
	private boolean correct;
	private int questionId;
	private boolean beingUsed; // only used in topic JSP page
	public Answer() {
		
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public boolean isBeingUsed() {
		return beingUsed;
	}
	public void setBeingUsed(boolean beingUsed) {
		this.beingUsed = beingUsed;
	}
}
