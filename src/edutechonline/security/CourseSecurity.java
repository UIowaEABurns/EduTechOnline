package edutechonline.security;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.Course;

public class CourseSecurity {

	
	
	public static ValidatorStatusCode canUserModifyCourse(int courseId, int userIdMakingRequest) {
		Course c=Courses.getCourse(courseId);
		if (c.getOwnerId()!=userIdMakingRequest) {
			return new ValidatorStatusCode(false, "only the owner of a course can delete it");

		}
		
		
		return new ValidatorStatusCode(true);
	}
}
