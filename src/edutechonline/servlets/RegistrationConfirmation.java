package edutechonline.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eduttechonline.security.ValidatorStatusCode;
import edutechonline.util.HttpRequestHelper;
public class RegistrationConfirmation extends HttpServlet {
	
	
	private static final long serialVersionUID = 5754311737700884227L;
	
	
	private static final String CONFIRMATION_ID="cid";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ValidatorStatusCode status=isValidRequest(request);
		if (!status.isSuccess()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,status.getMessage());
			return;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	
	public ValidatorStatusCode isValidRequest(HttpServletRequest request) {
		if (!HttpRequestHelper.paramExists(CONFIRMATION_ID,request)) {
			return new ValidatorStatusCode(false, "no confirmation ID specified");
		}
		return new ValidatorStatusCode(true);
	}
}
