package edutechonline.test.suites;

import java.util.List;

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
	User student=null;
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
	private void getAllCoursesTest() {
		Assert.assertNotNull(Courses.getAllCourses());
	}
	
	@Test
	private void deleteCourseTest() {
		Course testCourse=ResourceLoader.loadCourseIntoDatabase(manager.getID());
		
		ContentTopic t1=ResourceLoader.loadTopicIntoDatabase(testCourse.getID());
		ContentTopic t2=ResourceLoader.loadTopicIntoDatabase(testCourse.getID());
		
		Assert.assertTrue(Courses.deleteCourse(testCourse.getID()));
		Assert.assertNull(Courses.getContentTopic(t1.getID()));
		Assert.assertNull(Courses.getContentTopic(t2.getID()));
		Assert.assertNull(Courses.getCourse(testCourse.getID()));
		

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
	private void getAllOpenCoursesTest() {
		List<Course> courses=Courses.getAllOpenCourses();
		for (Course c : courses) {
			Assert.assertTrue(c.isOpen());
		}
	}
	@Test
	private void isEnrolledTest() {
		Assert.assertTrue(Courses.isEnrolled(student.getID(), course.getID()));
		Assert.assertFalse(Courses.isEnrolled(manager.getID(), course.getID()));
	}
	@Test
	private void isCompleteTest() {
		Assert.assertTrue(Courses.hasUserCompletedCourse(student.getID(), course.getID()));
	}
	@Test
	private void getUserIdsByCourseTest() {
		Assert.assertEquals(1,Courses.getUserIdsInCourse(course.getID()).size());
	}
	@Test
	private void getCertURLTest() {
		Assert.assertNotNull(Courses.getCertificateUrl(student.getID(), course.getID()));
	}
	@Test
	private void editCourseDeprecationTest() {
		Assert.assertTrue(Courses.editCourseDeprecation(course.getID(), true));
		Assert.assertTrue(Courses.getCourse(course.getID()).isDeprecated());
		Assert.assertTrue(Courses.editCourseDeprecation(course.getID(), false));
		Assert.assertFalse(Courses.getCourse(course.getID()).isDeprecated());
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
	private void getCoursesByUserTest() {
		List<Course> courses=Users.getCoursesByUser(student.getID());
		Assert.assertEquals(1,courses.size());
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
		course.setTempUserId(-1);

		c1=ResourceLoader.loadTopicIntoDatabase(course.getID());
		c2=ResourceLoader.loadTopicIntoDatabase(course.getID());
		student=ResourceLoader.loadUserIntoDatabase("user");
		Courses.enroll(student.getID(),course.getID());

	}

	@Override
	protected void teardown() throws Exception {
		Assert.assertTrue(Courses.deleteContentTopic(c1.getID()));
		Assert.assertTrue(Courses.deleteContentTopic(c2.getID()));
		Assert.assertTrue(Courses.deleteCourse(course.getID()));
		Assert.assertTrue(Users.deleteUser(manager.getID()));
		Assert.assertTrue(Users.deleteUser(student.getID()));
	}

}
