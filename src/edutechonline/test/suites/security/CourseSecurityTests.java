package edutechonline.test.suites.security;

import org.junit.Assert;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.ContentTopic;
import edutechonline.database.entity.Course;
import edutechonline.database.entity.User;
import edutechonline.security.CourseSecurity;
import edutechonline.test.ResourceLoader;
import edutechonline.test.Test;
import edutechonline.test.TestSet;

public class CourseSecurityTests extends TestSet {
	Course c=null;
	ContentTopic topic=null;
	User student=null;
	User manager=null;
	@Test
	private void canUserEnrollTest() {
		Assert.assertTrue(CourseSecurity.canUserEnrollInCourse(c.getID(), student.getID()).isSuccess());
		Assert.assertFalse(CourseSecurity.canUserEnrollInCourse(c.getID(), manager.getID()).isSuccess());
	}
	@Test
	private void canUserModifyTest() {
		Assert.assertFalse(CourseSecurity.canUserModifyCourse(c.getID(), student.getID()).isSuccess());
		Assert.assertTrue(CourseSecurity.canUserModifyCourse(c.getID(), manager.getID()).isSuccess());
	}
	@Test
	private void canUserModifyTopicTest() {
		Assert.assertFalse(CourseSecurity.canUserModifyTopic(topic.getID(), student.getID()).isSuccess());
		Assert.assertTrue(CourseSecurity.canUserModifyTopic(topic.getID(), manager.getID()).isSuccess());
	}
	@Test
	private void canUserSeeCourseTopicsTest() {
		Assert.assertFalse(CourseSecurity.canUserSeeCourseTopics(c.getID(), student.getID()).isSuccess());
		Assert.assertTrue(CourseSecurity.canUserSeeCourseTopics(c.getID(), manager.getID()).isSuccess());
	}
	@Override
	protected String getTestName() {
		return "CourseSecurityTests";
	}

	@Override
	protected void setup() throws Exception {
		manager=ResourceLoader.loadUserIntoDatabase("manager");
		student=ResourceLoader.loadUserIntoDatabase("user");
		c=ResourceLoader.loadCourseIntoDatabase(manager.getID());
		topic=ResourceLoader.loadTopicIntoDatabase(c.getID());
		Courses.openCourse(c.getID());
	}

	@Override
	protected void teardown() throws Exception {
		Courses.deleteCourse(c.getID());
		Users.deleteUser(manager.getID());
		Users.deleteUser(student.getID());
	}

}
