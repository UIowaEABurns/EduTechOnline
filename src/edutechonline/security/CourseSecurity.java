package edutechonline.security;

import java.util.List;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.ContentTopic;
import edutechonline.database.entity.Course;

public class CourseSecurity {

	
	
	public static ValidatorStatusCode canUserModifyCourse(int courseId, int userIdMakingRequest) {
		Course c=Courses.getCourse(courseId);
		if (c==null) {
			return new ValidatorStatusCode(false, "the given course could not be found");
		}
		if (c.getOwnerId()!=userIdMakingRequest) {
			return new ValidatorStatusCode(false, "only the owner of a course can delete it");

		}
		
		
		return new ValidatorStatusCode(true);
	}
	
	public static ValidatorStatusCode canUserEnrollInCourse(int courseId, int userId) {
		if (!Users.isStudent(userId)) {
			return new ValidatorStatusCode(false, "Only students may enroll in a course");
		}
		Course c=Courses.getCourse(courseId);
		if (c==null) {
			return new ValidatorStatusCode(false, "The given course could not be found");
		}
		if (!c.isOpen()) {
			return new ValidatorStatusCode(false, "The given course is not open");
		}
		if (Courses.isEnrolled(userId, courseId)) {
			return new ValidatorStatusCode(false, "You are already enrolled in the given course");
		}
		return new ValidatorStatusCode(true);
	}
	
	public static ValidatorStatusCode canUserModifyTopic(int topicId, int userIdMakingRequest) {
		ContentTopic c=Courses.getContentTopic(topicId);
		if (c==null) {
			return new ValidatorStatusCode(false, "the given topic could not be found");
		}
		return canUserModifyCourse(c.getCourseId(),userIdMakingRequest);
	}
	
	public static ValidatorStatusCode canUserSeeCourseTopics(int courseId, int userId) {
		if (canUserModifyCourse(courseId,userId).isSuccess()) {
			return new ValidatorStatusCode(true);
		}
		//if not, we need to check if the given user is enrolled in the given course
		//and check to make sure it is open
		Course c=Courses.getCourse(courseId);
		if (c==null) {
			return new ValidatorStatusCode(false, "the given course could not be found");
		}
		if (!c.isOpen()) {
			return new ValidatorStatusCode(false, "The course is not currently open");
		}
		List<Integer> enrolled=Courses.getUserIdsInCourse(courseId);
		
		for (Integer i : enrolled) {
			if (i==userId) {
				return new ValidatorStatusCode(true);
			}
		}
		
		return new ValidatorStatusCode(false, "You must enroll in the course to see the topics");
	}
}
