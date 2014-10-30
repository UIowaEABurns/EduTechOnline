package edutechonline.test.suites;

import org.junit.Assert;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.ContentTopic;
import edutechonline.database.entity.Course;
import edutechonline.database.entity.User;
import edutechonline.test.ResourceLoader;
import edutechonline.test.Test;
import edutechonline.test.TestSet;

public class CourseTests extends TestSet {

	User manager=null;
	Course course=null;
	ContentTopic c1=null;
	ContentTopic c2=null;
	@Override
	protected String getTestName() {
		return "CourseTests";
	}
	
	@Test
	private void deleteContentTopicTest() {
		Course testCourse=ResourceLoader.loadCourseIntoDatabase(manager.getID());
		
		ContentTopic t1=ResourceLoader.loadTopicIntoDatabase(testCourse.getID());
		ContentTopic t2=ResourceLoader.loadTopicIntoDatabase(testCourse.getID());
		
		Assert.assertTrue(Courses.deleteContentTopic(t1.getID()));
		Assert.assertNull(Courses.getContentTopic(t1.getID()));
		Assert.assertNotNull(Courses.getContentTopic(t2.getID()));
		Assert.assertNotNull(Courses.getCourse(testCourse.getID()));
		
		Assert.assertTrue(Courses.deleteContentTopic(t2.getID()));
		Assert.assertTrue(Courses.deleteCourse(testCourse.getID()));


	}
	
	@Test
	private void deleteCourseTest() {
		Course testCourse=ResourceLoader.loadCourseIntoDatabase(manager.getID());
		
		ContentTopic t1=ResourceLoader.loadTopicIntoDatabase(testCourse.getID());
		ContentTopic t2=ResourceLoader.loadTopicIntoDatabase(testCourse.getID());
		
		Assert.assertTrue(Courses.deleteContentTopic(t1.getID()));
		Assert.assertNull(Courses.getContentTopic(t1.getID()));
		Assert.assertNull(Courses.getContentTopic(t2.getID()));
		Assert.assertNull(Courses.getCourse(testCourse.getID()));
		

	}
	
	@Test
	private void getContentTopicTests() {
		
	}
	
	@Test 
	private void getCourseTest() {
		Course temp=Courses.getCourse(course.getID());
		Assert.assertNotNull(temp);
		Assert.assertEquals(course.getName(), temp.getName());
		
		
		temp=Courses.getCourse(-1);
		Assert.assertNull(temp);
	}
	
	@Test
	private void openCourseTest() {
		Assert.assertTrue(Courses.editCourseVisibility(course.getID(), false)); //make sure the course is closed before starting
		
		Assert.assertTrue(Courses.openCourse(course.getID()));
		Assert.assertEquals(true,Courses.getCourse(course.getID()).isOpen());
		
		Assert.assertTrue(Courses.openCourse(course.getID()));
		Assert.assertEquals(true,Courses.getCourse(course.getID()).isOpen()); //try it again to cover both cases
	}
	
	@Test
	private void closeCourseTest() {
		Assert.assertTrue(Courses.editCourseVisibility(course.getID(), true)); //make sure the course is open before starting
	
		Assert.assertTrue(Courses.hideCourse(course.getID()));
		Assert.assertEquals(false,Courses.getCourse(course.getID()).isOpen());
		
		Assert.assertTrue(Courses.hideCourse(course.getID()));
		Assert.assertEquals(false,Courses.getCourse(course.getID()).isOpen()); //try it again to cover both cases
	}
	
	@Test
	private void toggleOpenTest() {
		boolean open=course.isOpen();
		Assert.assertTrue(Courses.editCourseVisibility(course.getID(), !open));
		Assert.assertEquals(!open, Courses.getCourse(course.getID()).isOpen()); // should have inverted
		
		Assert.assertTrue(Courses.editCourseVisibility(course.getID(), open));
		Assert.assertEquals(open, Courses.getCourse(course.getID()).isOpen()); // should have flipped back to original
	}
	
	@Test 
	private void getContentTopicTest() {
		ContentTopic temp=Courses.getContentTopic(c1.getID());
		Assert.assertNotNull(temp);
		Assert.assertEquals(c1.getName(), temp.getName());
		
		
		temp=Courses.getContentTopic(-1);
		Assert.assertNull(temp);
	}

	@Override
	protected void setup() throws Exception {
		manager=ResourceLoader.loadUserIntoDatabase();
		course=ResourceLoader.loadCourseIntoDatabase(manager.getID());
		System.out.println(course.getName());
		c1=ResourceLoader.loadTopicIntoDatabase(course.getID());
		c2=ResourceLoader.loadTopicIntoDatabase(course.getID());

	}

	@Override
	protected void teardown() throws Exception {
		Assert.assertTrue(Courses.deleteContentTopic(c1.getID()));
		Assert.assertTrue(Courses.deleteContentTopic(c2.getID()));
		Assert.assertTrue(Courses.deleteCourse(course.getID()));
		Assert.assertTrue(Users.deleteUser(manager.getID()));
	}

}
