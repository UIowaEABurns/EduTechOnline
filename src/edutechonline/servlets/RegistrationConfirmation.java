package edutechonline.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import edutechonline.database.Users;
import edutechonline.security.ValidatorStatusCode;
import edutechonline.util.HttpRequestHelper;
public class RegistrationConfirmation extends HttpServlet {
	
	private static Logger log=Logger.getLogger(RegistrationConfirmation.class);
	private static final long serialVersionUID = 5754311737700884227L;
	
	
	private static final String CONFIRMATION_ID="id";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("confirming registration of a user");

		ValidatorStatusCode status=isValidRequest(request);
		if (!status.isSuccess()) {
			log.debug("invalid request to confirm a user");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,status.getMessage());
			return;
		}
		
		boolean success=Users.verifyUser(request.getParameter(CONFIRMATION_ID), "user");
		if (success) {
			String message="Congratulations, you have successfully registered for EduTechOnline!";
			response.sendRedirect("/EduTechOnline/jsp/public/message.jsp?msg="+message);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	
	public ValidatorStatusCode isValidRequest(HttpServletRequest request) {
		if (!HttpRequestHelper.paramExists(CONFIRMATION_ID,request)) {
			return new ValidatorStatusCode(false, "no confirmation ID specified");
		}
		String code=request.getParameter(CONFIRMATION_ID);
		if (Users.getUserIdByCode(code)<0) {
			return new ValidatorStatusCode(false, "no user ID with the given confirmation code");
		}
		return new ValidatorStatusCode(true);
	}
}
