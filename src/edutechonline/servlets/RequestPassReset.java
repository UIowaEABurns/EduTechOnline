package edutechonline.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import edutechonline.database.Users;
import edutechonline.database.entity.User;
import edutechonline.security.ValidatorStatusCode;
import edutechonline.util.HttpRequestHelper;
import edutechonline.util.Mail;
import edutechonline.util.Util;
import edutechonline.util.Validator;
public class RequestPassReset extends HttpServlet {
	
	private static Logger log=Logger.getLogger(RequestPassReset.class);
	private static final long serialVersionUID = 5754311737700884227L;
	
	
	private static final String EMAIL="email";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("got a request to reset a password");

		ValidatorStatusCode status=isValidRequest(request);
		if (!status.isSuccess()) {
			log.debug("invalid request to reset a password");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,status.getMessage());
			return;
		}
		String randomPass=Util.getRandomPlaintextPassword();
		String email=request.getParameter(EMAIL);
		log.debug("current user email is "+ email);

		User u=Users.getUser(email);
		log.debug("current user ID is "+u.getID());
		boolean success=Users.addPassResetToUser(u.getID(), randomPass);
		log.debug("about to send email to user for password request");
		if (success) {
			Mail.sendPasswordResetEmail(u, randomPass);

			String message="Thank you for your request. An email has been sent to the supplied address. Please follow the directions"
					+ " in that message";
			response.sendRedirect("/EduTechOnline/jsp/public/message.jsp?msg="+message);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
	
	/**
	 * checks to see whether a valid email address was provided
	 * @param request
	 * @return
	 */
	public ValidatorStatusCode isValidRequest(HttpServletRequest request) {
		
		
		if (!Validator.isValidEmail(request.getParameter(EMAIL))) {
			return new ValidatorStatusCode(false, "The given email is not valid");
		}
		
		String email=request.getParameter(EMAIL);
		User u=Users.getUser(email);
		if (u==null) {
			return new ValidatorStatusCode(false, "No user with the given email address could be found");
		}
		
		if (Users.userInPassResetTable(u.getID())) {
			return new ValidatorStatusCode(false, "There is already an outstanding password reset for this account");
		}
		
		return new ValidatorStatusCode(true);
	}
}
