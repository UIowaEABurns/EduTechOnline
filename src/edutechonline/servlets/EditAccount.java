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

public class EditAccount extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5106148501555321114L;


	private static Logger log=Logger.getLogger(EditAccount.class);
	

	private static final String FIRST_NAME="fname";
	private static final String LAST_NAME="lname";
	
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
			User u=Users.getUser(userId);
			u.setFirstName(request.getParameter(FIRST_NAME));
			u.setLastName(request.getParameter(LAST_NAME));
			boolean success=Users.updateUser(u);
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
		if (!Validator.isValidName(request.getParameter(FIRST_NAME))) {
			return new ValidatorStatusCode(false, "The given first name is not valid");
		}
		
		if (!Validator.isValidName(request.getParameter(LAST_NAME))) {
			return new ValidatorStatusCode(false, "The given last name is not valid");
		}
		return new ValidatorStatusCode(true);
	}
}
