package edutechonline.database.entity;

import java.util.List;

public class Course extends IDEntity {
	
	private String name=null;  //what is the name of this course
	private String description=null; //description of the course
	private int ownerId=-1; //the content manager of the course
	private float cost=-1; //how much does it cost to take the course
	private boolean open=false; // is this course currently visible to the public
	private String category;
	private List<ContentTopic> topics;
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
}
