package edutechonline.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import edutechonline.database.Users;
import edutechonline.database.entity.User;
import edutechonline.security.ValidatorStatusCode;
import edutechonline.util.Mail;
import edutechonline.util.Validator;


/**
 * Servlet implementation class CoursesAdd
 */

	public class CoursesAdd extends HttpServlet {
	private static Logger log=Logger.getLogger(Registration.class);
	
	private static final long serialVersionUID = 13432521466L;
       
	private static final String COURSE_NAME="name";
	private static final String CATEGORY="category";
	private static final String DESCRIPTION="desc";
	private static final String FEE="cost";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoursesAdd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
