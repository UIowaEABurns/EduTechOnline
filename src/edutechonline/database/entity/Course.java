package edutechonline.database.entity;

import java.util.List;

import edutechonline.database.Courses;
import edutechonline.util.Util;

public class Course extends IDEntity {
	
	private String name=null;  //what is the name of this course
	private String description=null; //description of the course
	private int ownerId=-1; //the content manager of the course
	private float cost=-1; //how much does it cost to take the course
	private boolean open=false; // is this course currently visible to the public
	private String category;
	private List<ContentTopic> topics;
	private boolean deprecated;
	private int tempUserId;
	public Course() {
		
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
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<ContentTopic> getTopics() {
		return topics;
	}
	
	public void addTopic(ContentTopic tp) {
		topics.add(tp);
	}
	public void setTopics(List<ContentTopic> topics) {
		this.topics = topics;
	}
	public boolean isDeprecated() {
		return deprecated;
	}
	public String isDeprecatedDisplay() {
		if (deprecated) {
			return "yes";
		}
		return "no";
	}
	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}
	
	public String getGradeString(int userId) {
		if (!Courses.hasUserCompletedCourse(userId, this.getID())) {
			return "in progress";
		} else {
			Float grade=Courses.getCourseGrade(userId, this.getID());
			if (grade==null) {
				return "ungraded";
			}
			return Util.pointsToGrade(grade);
		}
	}
	
	public String getGradeString() {
		return getGradeString(tempUserId);
	}

	public void setTempUserId(int tempUserId) {
		this.tempUserId = tempUserId;
	}
}
