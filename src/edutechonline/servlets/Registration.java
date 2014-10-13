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

public class Registration extends HttpServlet {
	private static Logger log=Logger.getLogger(Registration.class);
	
	private static final long serialVersionUID = 8404699124554449863L;

	private static final String FIRST_NAME="fname";
	private static final String LAST_NAME="lname";
	private static final String EMAIL="email";
	private static final String PASSWORD="pass";
	private static final String PASSWORD_CONFIRM="passConfirm";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("incoming registration request");
		try {
			ValidatorStatusCode status=isValidRequest(request);
			if (!status.isSuccess()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, status.getMessage());
				return;
			}
			log.debug("the request was valid");
			User u=new User();
			u.setFirstName(request.getParameter(FIRST_NAME));
			u.setLastName(request.getParameter(LAST_NAME));
			u.setEmail(request.getParameter(EMAIL));
			u.setPassword(request.getParameter(PASSWORD));
			String code=UUID.randomUUID().toString();
			int id=Users.registerUser(u,code);
			log.debug("id of new user = "+id);
			if (id>0) {
				//if we successfully added the user
				Mail.sendConfirmationEmail(u,code);
				String message="Thank you for registering for the EduTechOnline service! You should receive"
						+ " an email message at the address you registered with-- please follow the directions in that"
						+ " message to continue the process";
				response.sendRedirect("/EduTechOnline/jsp/public/message.jsp?msg="+message);
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
		
		if (!Validator.isValidEmail(request.getParameter(EMAIL))) {
			return new ValidatorStatusCode(false, "The given email is not valid");
		}
		
		String email=request.getParameter(EMAIL);
		if (Users.getUser(email)!=null) {
			return new ValidatorStatusCode(false, "The given email is already in use");
		}
		
		if (!Validator.isValidPassword(request.getParameter(PASSWORD)) || !Validator.isValidPassword(request.getParameter(PASSWORD_CONFIRM))) {
			return new ValidatorStatusCode(false, "The given password is not valid");
		}
		
		String pass1=request.getParameter(PASSWORD);
		String pass2=request.getParameter(PASSWORD_CONFIRM);
		if (!pass1.equals(pass2)) {
			return new ValidatorStatusCode(false, "The two passwords do not match");
		}
		
		return new ValidatorStatusCode(true);
	}
}
