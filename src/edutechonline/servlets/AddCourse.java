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
public class AddCourse extends HttpServlet {
	
	
	private static final long serialVersionUID = 3897208779260532367L;


	private static Logger log=Logger.getLogger(AddCourse.class);
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	/**
	 * checks to see whether this is a valid request
	 * @param request
	 * @return
	 */
	public ValidatorStatusCode isValidRequest(HttpServletRequest request) {
		return null; //TODO: fill in
	}
}
