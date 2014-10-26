package edutechonline.servlets;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import edutechonline.database.Users;
import edutechonline.database.entity.User;
import edutechonline.security.ValidatorStatusCode;
import edutechonline.util.Mail;
import edutechonline.util.Validator;

public class EditPassword extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5106148501555321114L;


	private static Logger log=Logger.getLogger(EditPassword.class);
	

	private static final String PASSWORD="pass";
	private static final String CONFIRM_PASSWORD="cpass";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ValidatorStatusCode status=isValidRequest(request);
			if (!status.isSuccess()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, status.getMessage());
				return;
			}
			int userId=SessionFilter.getUserId(request);
			boolean success=Users.updatePassword(userId, request.getParameter(PASSWORD));
			if (success) {
				response.sendRedirect("/EduTechOnline/jsp/secure/accounts/details.jsp");
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "There was an internal error updating your profile");

			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	
	public ValidatorStatusCode isValidRequest(HttpServletRequest request) {
		if (!Validator.isValidPassword(request.getParameter(PASSWORD)) || !Validator.isValidPassword(request.getParameter(CONFIRM_PASSWORD))) {
			return new ValidatorStatusCode(false, "The given password is not valid");
		}
		
		String pass1=request.getParameter(PASSWORD);
		String pass2=request.getParameter(CONFIRM_PASSWORD);
		if (!pass1.equals(pass2)) {
			return new ValidatorStatusCode(false, "The two passwords do not match");
		}
		return new ValidatorStatusCode(true);
	}
}
