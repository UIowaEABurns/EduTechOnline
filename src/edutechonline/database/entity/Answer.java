package edutechonline.database.entity;

public class Answer extends IDEntity {
	private String text;
	private boolean correct;
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
}
