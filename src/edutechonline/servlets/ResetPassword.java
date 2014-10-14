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
import edutechonline.util.Validator;
public class ResetPassword extends HttpServlet {
	
	private static Logger log=Logger.getLogger(ResetPassword.class);
	private static final long serialVersionUID = 5754311737700884227L;
	
	
	private static final String TEMP_ID="id";
	private static final String EMAIL="email";
	private static final String PASSWORD="pass";
	private static final String PASSWORD_CONFIRM="passConfirm";
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
		
		//this is a valid request, so update the password
		boolean success=Users.updatePassword(Users.getUser(request.getParameter(EMAIL)).getID(), request.getParameter(PASSWORD));
		if (success) {
			String message="Thank you, your password was reset successfully!";
			response.sendRedirect("/EduTechOnline/jsp/public/message.jsp?msg="+message);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
	
	
	public ValidatorStatusCode isValidRequest(HttpServletRequest request) {
		
		
		if (!Validator.isValidEmail(request.getParameter(EMAIL))) {
			return new ValidatorStatusCode(false, "The given email is not valid");
		}
		
		String email=request.getParameter(EMAIL);
		User u=Users.getUser(email);
		
		if (!Validator.isValidPassword(request.getParameter(PASSWORD)) || !Validator.isValidPassword(request.getParameter(PASSWORD_CONFIRM)) ||
				!Validator.isValidPassword(request.getParameter(TEMP_ID))) {
			return new ValidatorStatusCode(false, "The given password is not valid");
		}
		String code=request.getParameter(TEMP_ID);
		
		if (u==null) {
			return new ValidatorStatusCode(false, "No user with the given email address could be found");
		}
		if (!Users.userInPassResetTable(u.getID(), code)) {
			return new ValidatorStatusCode(false, "Incorrect temporary password");
		}
		
		String pass1=request.getParameter(PASSWORD);
		String pass2=request.getParameter(PASSWORD_CONFIRM);
		if (!pass1.equals(pass2)) {
			return new ValidatorStatusCode(false, "The two passwords do not match");
		}
		
		return new ValidatorStatusCode(true);
	}
}
