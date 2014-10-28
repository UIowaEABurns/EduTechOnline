package edutechonline.servlets;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.Course;
import edutechonline.database.entity.User;
import edutechonline.security.ValidatorStatusCode;
import edutechonline.util.Mail;
import edutechonline.util.Validator;


/**
 * Servlet implementation class CoursesAdd
 */

	public class CoursesAdd extends HttpServlet 
	{
	private static Logger log=Logger.getLogger(Registration.class);
	
	private static final long serialVersionUID = 13432521466L;
       
	private static final String COURSE_NAME="name";
	private static final String CATEGORY="category";
	private static final String DESCRIPTION="desc";
	private static final String  FEE="0";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("incoming registration request");
		try {
			ValidatorStatusCode status=isValidRequest(request);
			if (!status.isSuccess()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, status.getMessage());
				return;
			}
			log.debug("the request was valid");
			Course c=new Course();
			c.setName(request.getParameter(COURSE_NAME));
			c.setCategory(request.getParameter(CATEGORY));
			c.setDescription(request.getParameter(DESCRIPTION));
			//c.setCost(request.getParameter(FEE));
			
		}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
			 catch (Exception e) {
					log.error(e.getMessage(),e);
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}

               }
	     
	public ValidatorStatusCode isValidRequest(HttpServletRequest request) {
		if (!Validator.isValidName(request.getParameter(COURSE_NAME))) {
			return new ValidatorStatusCode(false, "The given Course name is not valid");
		}
		
		if (!Validator.isValidName(request.getParameter(CATEGORY))) {
			return new ValidatorStatusCode(false, "The given Category name is not valid");
		}
		
		if (!Validator.isValidEmail(request.getParameter(DESCRIPTION))) {
			return new ValidatorStatusCode(false, "The given Description is not valid");
		}
		
		
		
		
		return new ValidatorStatusCode(true);
	}
}

		