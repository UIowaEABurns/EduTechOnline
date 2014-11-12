package edutechonline.servlets;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.User;
import edutechonline.security.ValidatorStatusCode;
import edutechonline.util.GenerateCertificate;
import edutechonline.util.Mail;
import edutechonline.util.Validator;

public class GetCertificate extends HttpServlet {
	private static Logger log=Logger.getLogger(GetCertificate.class);
	
	private static final long serialVersionUID = 8404699124554449863L;

	private static final String COURSE_ID="course";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("incoming registration request");
		try {
			int userId=SessionFilter.getUserId(request);

			ValidatorStatusCode status=isValidRequest(request);
			if (!status.isSuccess()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, status.getMessage());
				return;
			}
			int courseId=Integer.parseInt(request.getParameter(COURSE_ID));
			String filePath=Courses.getAbsolutePathForCertificate(userId, courseId);
			GenerateCertificate.makeCertificate(userId, courseId, new File(filePath));
				//if we successfully added the user
		
			response.sendRedirect(Courses.getCertificateUrl(userId, courseId));
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	
	public ValidatorStatusCode isValidRequest(HttpServletRequest request) {
		int userId=SessionFilter.getUserId(request);
		if (!Validator.isValidInteger(request.getParameter(COURSE_ID))) {
			return new ValidatorStatusCode(false, "The given course ID is not valid");
		}
		int courseId=Integer.parseInt(request.getParameter(COURSE_ID));
		if (!Courses.hasUserCompletedCourse(userId, courseId)) {
			return new ValidatorStatusCode(false, "You have not yet completed this course");
		}
		
		
		return new ValidatorStatusCode(true);
	}
}
