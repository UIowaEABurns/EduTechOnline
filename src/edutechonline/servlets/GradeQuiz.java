package edutechonline.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
import edutechonline.database.entity.Question;
import edutechonline.database.entity.Quiz;
import edutechonline.database.entity.User;
import edutechonline.security.ValidatorStatusCode;
import edutechonline.util.Mail;
import edutechonline.util.Validator;


/**
 * Servlet implementation class CoursesAdd
 */

	public class GradeQuiz extends HttpServlet 
	{
	private static Logger log=Logger.getLogger(GradeQuiz.class);
	
	private static final long serialVersionUID = 4720972089297721L;
       
	private static final String QUIZ_ID="quiz";
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try {
			ValidatorStatusCode status=isValidRequest(request);
			if (!status.isSuccess()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, status.getMessage());
				return;
			}
			int userId= SessionFilter.getUserId(request);
			Enumeration<String> params = request.getParameterNames();
			int points=0;
			Quiz quiz = Courses.getQuiz(Integer.parseInt(request.getParameter(QUIZ_ID)));
			List<Integer> answers=new ArrayList<Integer>();
			while (params.hasMoreElements()) {
				String name=params.nextElement();
				if (!name.startsWith("question:")) {
					continue;
				}
				int questionId=Integer.parseInt(name.replace("question:", "").trim());
				Question q=quiz.getQuestion(questionId);
				int correctAnswer=q.getCorrectAnswer().getID();
				int answerId=Integer.parseInt(request.getParameter(name));
				if (correctAnswer==answerId) {
					points++;
				}
				answers.add(answerId);
			}
			boolean success = Courses.addQuizScores(userId, quiz.getID(), points, answers);
			if (success) {
				//success
				response.sendRedirect("/EduTechOnline/jsp/secure/courses/topic.jsp?tid="+quiz.getID());
			} else {
				//failure, internal error
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			}
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
		int userId=SessionFilter.getUserId(request);
		if (!Validator.isValidInteger(request.getParameter(QUIZ_ID))) {
			return new ValidatorStatusCode(false, "The given quiz could not be found");
		}
		int quizId=Integer.parseInt(request.getParameter(QUIZ_ID));
		Quiz q=Courses.getQuiz(quizId);
		if (q==null) {
			return new ValidatorStatusCode(false, "The given quiz could not be found");
		}
		
		Float score=Courses.getQuizScore(quizId, userId);
		if (score!=null) {
			return new ValidatorStatusCode(false, "You may only take a quiz one time");
		}
		
		return new ValidatorStatusCode(true);
	}
}

		