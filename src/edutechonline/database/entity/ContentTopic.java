package edutechonline.database.entity;

public class ContentTopic extends IDEntity {
	public enum ContentType {
		PDF(0), TEXT(1), VIDEO(2), QUIZ(3);
		
	    private final int value;
		private ContentType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		
	public static ContentType toStatusCode(int code) {
			
		    switch (code) {
		    case 0:
		    	return ContentType.PDF;
		    case 1:
		    	return ContentType.TEXT;
		    case 2:
		    	return ContentType.VIDEO;
		    case 3:
		    	return ContentType.QUIZ;
		
		}
		return null;
	}
}
	private String name;
	private String description;
	private ContentType type;
	private String url;
	private int courseId;
	public ContentTopic(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
