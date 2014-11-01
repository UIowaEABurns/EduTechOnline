package edutechonline.test;

import java.util.Random;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.ContentTopic;
import edutechonline.database.entity.ContentTopic.ContentType;
import edutechonline.database.entity.Course;
import edutechonline.database.entity.User;

/**
 * This class is responsible for loading primitives into the database that can be used for testing
 * 
 * @author Eric
 *
 */

public class ResourceLoader {

	public static User loadUserIntoDatabase(String role) {
		User u=new User();
		u.setFirstName(TestUtil.getRandomUserName());
		u.setLastName(TestUtil.getRandomUserName());
		u.setRole(role);
		u.setPassword(TestUtil.getRandomPassword());
		u.setEmail(TestUtil.getRandomEmail());
		int id=Users.addUser(u);
		if (id<=0) {
			return null;//failure
		}
		u.setID(id);
		return u;
	}
	public static User loadUserIntoDatabase() {
		return loadUserIntoDatabase("test");
	}
	
	public static Course loadCourseIntoDatabase(int userId) {
		Random rand=new Random();
		Course c=new Course();
		c.setName(TestUtil.getRandomUserName());
		c.setDescription(TestUtil.getRandomUserName());
		c.setCategory(TestUtil.getRandomAlphaString(20));
		c.setOpen(false);
		c.setCost(rand.nextFloat());
		c.setOwnerId(userId);
		int id=Courses.addCourse(c);
		if (id<=0) {
			return null; //failure
		}
		c.setID(id);
		return c;
	}
	
	public static ContentTopic loadTopicIntoDatabase(int courseId) {
		ContentTopic c=new ContentTopic();
		c.setName(TestUtil.getRandomUserName());
		c.setDescription(TestUtil.getRandomUserName());
		c.setUrl("fake");
		c.setType(ContentType.PDF);
		c.setCourseId(courseId);
		int id=Courses.addContentTopic(c);
		if (id<=0) {
			return null;
		}
		return c;
	}
}
