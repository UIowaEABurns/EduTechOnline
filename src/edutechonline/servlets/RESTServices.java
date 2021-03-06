package edutechonline.servlets;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;







import org.apache.log4j.Logger;









import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.Quiz;
import edutechonline.security.CourseSecurity;
import edutechonline.security.UserSecurity;
import edutechonline.security.ValidatorStatusCode;
import edutechonline.util.QuizXMLReader;

/**
 * This class is responsible for handling all small requests to the server. In other words, this is the place
 * to put handlers for requests that take only a single parameter or two, as writing full servlets for these is not necessary
 */
@Path("")
public class RESTServices {	
	private static final Logger log = Logger.getLogger(RESTServices.class);			
	private static Gson gson = new Gson();
	
	private static ValidatorStatusCode DATABASE_ERROR=new ValidatorStatusCode(false, "internal database error");
	
	@POST
	@Path("/add/quiz")
	@Produces("application/json")	
	public String addQuiz(@Context HttpServletRequest request) {					
		int requestUserId = SessionFilter.getUserId(request);
		String xml=request.getParameter("xml");
		Quiz q=QuizXMLReader.readQuizXML(xml);
		ValidatorStatusCode status=CourseSecurity.canUserModifyCourse(q.getCourseId(), requestUserId);
		if (!status.isSuccess()) {
			return gson.toJson(status);
		}
		
		boolean success=(Courses.addQuiz(q)>0);
		return success ? gson.toJson(new ValidatorStatusCode(true, "quiz added successfully")) :  gson.toJson(DATABASE_ERROR);
	}
	
	
	@POST
	@Path("/delete/user/{userId}")
	@Produces("application/json")	
	public String deleteUser(@PathParam("userId") int userId, @Context HttpServletRequest request) {					
		int requestUserId = SessionFilter.getUserId(request);
		log.debug("got a request to delete user ID = "+userId);
		ValidatorStatusCode status=UserSecurity.canUserDeleteUser(userId, requestUserId);
		if (!status.isSuccess()) {
			return gson.toJson(status);
		}
		boolean success=Users.deleteUser(userId);
		return success ? gson.toJson(new ValidatorStatusCode(true, "user deleted successfully")) :  gson.toJson(DATABASE_ERROR);
	}
	
	@POST
	@Path("/enroll/course/{courseId}")
	@Produces("application/json")	
	public String enrollInCourse(@PathParam("courseId") int courseId, @Context HttpServletRequest request) {					
		int userId = SessionFilter.getUserId(request);
		ValidatorStatusCode status=CourseSecurity.canUserEnrollInCourse(courseId, userId);
		if (!status.isSuccess()) {
			log.debug("rejected attempt to enroll in a course");
			return gson.toJson(status);
		}
		boolean success=Courses.enroll(userId, courseId);
		return success ? gson.toJson(new ValidatorStatusCode(true, "course enrolled successfully")) :  gson.toJson(DATABASE_ERROR);
	}
	
	@POST
	@Path("/delete/course/{courseId}")
	@Produces("application/json")	
	public String deleteCourse(@PathParam("courseId") int courseId, @Context HttpServletRequest request) {					
		int userId = SessionFilter.getUserId(request);
		ValidatorStatusCode status=CourseSecurity.canUserModifyCourse(courseId, userId);
		if (!status.isSuccess()) {
			log.debug("rejected attempt to delete a course");
			return gson.toJson(status);
		}
		boolean success=Courses.deleteCourse(courseId);
		return success ? gson.toJson(new ValidatorStatusCode(true, "course deleted successfully")) :  gson.toJson(DATABASE_ERROR);
	}
	
	@POST
	@Path("/delete/topic/{topicId}")
	@Produces("application/json")	
	public String deleteTopic(@PathParam("topicId") int topicId, @Context HttpServletRequest request) {					
		int userId = SessionFilter.getUserId(request);
		
		ValidatorStatusCode status=CourseSecurity.canUserModifyTopic(topicId, userId);
		if (!status.isSuccess()) {
			log.debug("rejected attempt to delete a content topic");
			return gson.toJson(status);
		}
		boolean success=Courses.deleteContentTopic(topicId);
		return success ? gson.toJson(new ValidatorStatusCode(true, "topic deleted successfully")) :  gson.toJson(DATABASE_ERROR);
	}
	
	
	
	@POST
	@Path("/course/open/{courseId}/{visible}")
	@Produces("application/json")	
	public String editCourseVisibility(@PathParam("courseId") int courseId,@PathParam("visible") boolean visible, @Context HttpServletRequest request) {					
		int userId = SessionFilter.getUserId(request);
		ValidatorStatusCode status=CourseSecurity.canUserModifyCourse(courseId, userId);
		if (!status.isSuccess()) {
			return gson.toJson(status);
		}
		boolean success=Courses.editCourseVisibility(courseId, visible);
		return success ? gson.toJson(new ValidatorStatusCode(true, "course deleted successfully")) :  gson.toJson(DATABASE_ERROR);
	}
	
	@POST
	@Path("/course/deprecate/{courseId}/{visible}")
	@Produces("application/json")	
	public String editCourseDeprecation(@PathParam("courseId") int courseId,@PathParam("visible") boolean visible, @Context HttpServletRequest request) {					
		int userId = SessionFilter.getUserId(request);
		ValidatorStatusCode status=CourseSecurity.canUserModifyCourse(courseId, userId);
		if (!status.isSuccess()) {
			return gson.toJson(status);
		}
		boolean success=Courses.editCourseDeprecation(courseId, visible);
		return success ? gson.toJson(new ValidatorStatusCode(true, "course deleted successfully")) :  gson.toJson(DATABASE_ERROR);
	}
	
	@POST
	@Path("/promote/user/{userId}")
	@Produces("application/json")	
	public String promoteUser(@PathParam("userId") int userId, @Context HttpServletRequest request) {					
		int requestUserId = SessionFilter.getUserId(request);
		log.debug("got a request to promote user ID = "+userId);
		//TODO: validate here
		ValidatorStatusCode status=new ValidatorStatusCode(true);
		if (!status.isSuccess()) {
			return gson.toJson(status);
		}
		boolean success=Users.updateRole(userId, "manager");
		return success ? gson.toJson(new ValidatorStatusCode(true, "recompilation successful")) :  gson.toJson(DATABASE_ERROR);
	}
	
	@POST
	@Path("/session/logout")
	@Produces("application/json")	
	public String doInvalidateSession(@Context HttpServletRequest request) {	
		request.getSession().invalidate();
		return gson.toJson(new ValidatorStatusCode(true));
	}	
	
	
	
}