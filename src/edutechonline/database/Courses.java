package edutechonline.database;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;









import edutechonline.application.Constants;
import edutechonline.database.entity.Answer;
import edutechonline.database.entity.ContentTopic;
import edutechonline.database.entity.ContentTopic.ContentType;
import edutechonline.database.entity.Course;
import edutechonline.database.entity.Question;
import edutechonline.database.entity.Quiz;
import edutechonline.util.Util;

/**
 * This class contains functions for reading and writing courses from the database
 * @author Eric
 *
 */

public class Courses {
	private static Logger log=Logger.getLogger(Courses.class);
	/**
	 * Given a ResultSet pointed at a course, returns that course
	 * @param results
	 * @return
	 */
	private static Course resultSetToCourse(ResultSet results) {
		try {
			Course c=new Course();
			c.setCost(results.getFloat("cost"));
			c.setID(results.getInt("id"));
			c.setName(results.getString("name"));
			c.setDescription(results.getString("description"));
			c.setOwnerId(results.getInt("owner_id"));
			c.setOpen(results.getBoolean("open"));
			c.setCategory(results.getString("category"));
			c.setDeprecated(results.getBoolean("deprecated"));
			return c;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	private static ContentTopic resultSetToTopic(ResultSet results) {
		try {
			ContentTopic c=new ContentTopic();
			c.setID(results.getInt("id"));
			c.setName(results.getString("name"));
			c.setDescription(results.getString("description"));
			c.setCourseId(results.getInt("course_id"));
			c.setUrl(results.getString("url"));
			c.setType(ContentType.toStatusCode(results.getInt("topic_type")));
			return c;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	
	public static int addContentTopic(ContentTopic c) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL addContentTopic(?,?,?,?,?,?)}");
			procedure.setString(1,c.getName());
			procedure.setString(2, c.getDescription());
			procedure.setInt(3,c.getCourseId());
			procedure.setString(4, c.getUrl());
			procedure.setInt(5, c.getType().getValue());
			procedure.registerOutParameter(6, java.sql.Types.INTEGER);
			procedure.executeUpdate();

			int id=procedure.getInt(6);
			c.setID(id);
			return id;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return -1;
		
	}
	
	/**
	 * Adds a new course to the database, given a course with all of its attributes
	 * set
	 * @param id
	 * @return The ID of the new course or -1 on errors
	 */
	public static int addCourse(Course c) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL addCourse(?,?,?,?,?,?,?,?)}");
			procedure.setString(1,c.getName());
			procedure.setString(2, c.getDescription());
			procedure.setInt(3,c.getOwnerId());
			procedure.setFloat(4, c.getCost());
			procedure.setString(5, c.getCategory());
			procedure.setBoolean(6, c.isOpen());
			procedure.setBoolean(7,c.isDeprecated());
			procedure.registerOutParameter(8, java.sql.Types.INTEGER);
			procedure.executeUpdate();
			int id=procedure.getInt(8);
			c.setID(id);
			return id;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return -1;
		
	}
	
	/**
	 * Deletes a content topic both on disk and from the database
	 * @param id
	 * @return
	 */
	public static boolean deleteContentTopic(int id) {
		
		//first, delete the content on disk
		String filepath=Courses.getAbsolutePathForTopic(id);
		File f=new File(filepath);
		FileUtils.deleteQuietly(f);
		
		Connection con=null;
		CallableStatement procedure=null;
		try {
			
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL deleteContentTopic(?)}");
			procedure.setInt(1,id);
			procedure.executeUpdate();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return false;
		
	}
	
	
	/**
	 * Deletes the given course from the database, including deleting all of its
	 * content topics
	 * @param id
	 * @return
	 */
	public static boolean deleteCourse(int id) {
		List<ContentTopic> topics=Courses.getContentTopicsForCourse(id);
		for (ContentTopic tp : topics) {
			Courses.deleteContentTopic(tp.getID());
		}
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL deleteCourse(?)}");
			procedure.setInt(1,id);
			procedure.executeUpdate();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return false;
		
	}
	
	
	public static List<ContentTopic> getContentTopicsForCourse(int id) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getCourseTopics(?)}");
			procedure.setInt(1,id);
			results=procedure.executeQuery();
			List<ContentTopic> topics=new ArrayList<ContentTopic>();
			while (results.next()) {
				topics.add(resultSetToTopic(results));
			}
			return topics;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return null;
		
	}
	
	/**
	 * Retrieves the given course from the database
	 * @param id
	 * @return
	 */
	public static ContentTopic getContentTopic(int id) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getContentTopic(?)}");
			procedure.setInt(1,id);
			results=procedure.executeQuery();
			if (results.next()) {
				return resultSetToTopic(results);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return null;
		
	}
	
	/**
	 * Retrieves the given course from the database
	 * @param id
	 * @return
	 */
	public static Course getCourse(int id) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getCourse(?)}");
			procedure.setInt(1,id);
			results=procedure.executeQuery();
			if (results.next()) {
				return resultSetToCourse(results);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return null;
		
	}
	
	
	/**
	 * Retrieves all courses created by the given manager from the database
	 * @param id
	 * @return
	 */
	public static List<Course> getCoursesByManager(int ownerId) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getCoursesByManager(?)}");
			procedure.setInt(1,ownerId);
			results=procedure.executeQuery();
			List<Course> courses=new ArrayList<Course>();
			while (results.next()) {
				courses.add(resultSetToCourse(results));
			}
			return courses;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return null;
		
	}
	
	/**
	 * Retrieves all courses from the database
	 * @param id
	 * @return
	 */
	public static List<Course> getAllCourses() {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getAllCourses()}");
			results=procedure.executeQuery();
			List<Course> courses=new ArrayList<Course>();
			while (results.next()) {
				courses.add(resultSetToCourse(results));
			}
			return courses;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return null;
		
	}
	
	public static boolean editCourseVisibility(int id, boolean visible) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL editCourseVisibility(?,?)}");
			procedure.setInt(1,id);
			procedure.setBoolean(2,visible);
			procedure.executeUpdate();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return false;
	}
	
	public static boolean editCourseDeprecation(int id, boolean deprecated) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL editCourseDeprecation(?,?)}");
			procedure.setInt(1,id);
			procedure.setBoolean(2,deprecated);
			procedure.executeUpdate();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return false;
	}
	
	public static boolean openCourse(int id){
		return editCourseVisibility(id,true);
	}
	public static boolean hideCourse(int id){
		return editCourseVisibility(id,false);
	}
	
	/**
	 * Retrieves all open courses from the database
	 * @param id
	 * @return
	 */
	public static List<Course> getAllOpenCourses() {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getAllOpenCourses()}");
			results=procedure.executeQuery();
			List<Course> courses=new ArrayList<Course>();
			while (results.next()) {
				courses.add(resultSetToCourse(results));
			}
			return courses;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return null;
		
	}
	
	/**
	 * Gets the URL to the given content topic
	 * @return
	 */
	
	
	//TODO: Continue updating these functions to handle different types
	public static String getTopicURL(int topicId) {
		ContentTopic c=Courses.getContentTopic(topicId);
		String url=Util.getAbsoluteURL("jsp/secure/content/"+c.getCourseId()+"/"+c.getID());
		url=url+"/"+c.getName();
		if (c.getType()==ContentType.PDF) {
			url=url+".pdf";
		} else if (c.getType()==ContentType.TEXT) {
			url=url+".txt";
		}
				
		log.debug("returning the following url for topic id = "+topicId+" "+url);
				
		return url;
	}
	
	public static String getCertificateUrl(int userId, int courseId) {
		String url=Util.getAbsoluteURL("jsp/secure/content/"+userId+"/"+courseId+"cert.pdf");
		
		return url;
	}
	
	public static String getAbsolutePathForCertificate(int userId, int courseId) {
		File f=new File(Constants.contentTopicDirectory,userId+"/"+courseId+"cert.pdf");
		
		return f.getAbsolutePath();
	}
	
	
	/**
	 * Returns the file for this content topic, relative to whatever directory
	 * we are storing content topics
	 * @param topicId
	 * @return
	 */
	public static String getAbsolutePathForTopic(int topicId) {
		ContentTopic c=Courses.getContentTopic(topicId);
		if (c==null) {
			return null;
		}
		File f=new File(Constants.contentTopicDirectory,c.getCourseId()+"/"+c.getID());
		if (c.getType()==ContentType.PDF) {
			f=new File(f, c.getName()+".pdf");
		} else if (c.getType()==ContentType.TEXT) {
			f=new File(f, c.getName()+".txt");
		}
		 //todo: change based on type
		return f.getAbsolutePath();
	}
	
	/**
	 * Enrolls the given user in the given course
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public static boolean enroll(int userId, int courseId) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL enroll(?,?)}");
			procedure.setInt(1,userId);
			procedure.setInt(2,courseId);
			procedure.executeUpdate();
			
			
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return false;
	}
	
	/**
	 * Returns whether the given user is enrolled in the given course
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public static boolean isEnrolled(int userId, int courseId) {
		List<Integer> users=getUserIdsInCourse(courseId);
		if (users==null) {
			return false;
		}
		for (Integer i : users) {
			if (i==userId) {
				return true;
			}
		}
		return false;
 	}
	
	/**
	 * Gets the ID of every user enrolled in the given course
	 * @param courseId
	 * @return
	 */
	public static List<Integer> getUserIdsInCourse(int courseId) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getUsersInCourse(?)}");
			procedure.setInt(1,courseId);
			
			results=procedure.executeQuery();
			List<Integer> answers=new ArrayList<Integer>();
			while (results.next()) {
				answers.add(results.getInt("user_id"));
			}
			
			return answers;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return null;
	}
	
	private static int addAnswer(Answer a, Connection con) {
		log.debug("trying to add an answer");
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL addAnswer(?,?,?,?)}");
			procedure.setInt(1, a.getQuestionId());
			procedure.setString(2,a.getText());
			procedure.setBoolean(3, a.isCorrect());
			procedure.registerOutParameter(4, java.sql.Types.INTEGER);
			procedure.executeUpdate();
			return procedure.getInt(4);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(procedure);
		}
		return -1;
	}
	
	public static int addQuestion(Question q) {
		log.debug("trying to add a question");
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL addQuestion(?,?,?)}");
			procedure.setInt(1, q.getQuizId());
			procedure.setString(2,q.getText());
			procedure.registerOutParameter(3, java.sql.Types.INTEGER);
			procedure.executeUpdate();
			int id=procedure.getInt(3);
			q.setID(id);
			for (Answer a : q.getAnswers()) {
				a.setQuestionId(id);
				addAnswer(a,con);
			}
			return id;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return -1;
	}
	
	
	private static Answer resultSetToAnswer(ResultSet results) throws SQLException {
		Answer a=new Answer();
		a.setID(results.getInt("id"));
		a.setText(results.getString("text"));
		a.setQuestionId(results.getInt("question_id"));
		a.setCorrect(results.getBoolean("correct"));
		return a;
	}
	
	private static Question resultSetToQuestion(ResultSet results) throws SQLException {
		Question a=new Question();
		a.setID(results.getInt("id"));
		a.setText(results.getString("text"));
		a.setQuizId(results.getInt("topic_id"));
	
		return a;
	}
	
	public static List<Question> getQuestionsForQuiz(int quizId) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getQuestionsByQuiz(?)}");
			procedure.setInt(1,quizId);
			results=procedure.executeQuery();
			List<Question> questions=new ArrayList<Question>();
			while (results.next()) {
				Question q=resultSetToQuestion(results);
				q.setAnswers(getAnswersForQuestion(q.getID()));
				questions.add(q);
				
			}
			return questions;
				
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return null;
	}
	
	public static List<Answer> getAnswersForQuestion(int questionId) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getAnswersByQuestion(?)}");
			procedure.setInt(1,questionId);
			results=procedure.executeQuery();
			List<Answer> answers=new ArrayList<Answer>();
			while (results.next()) {
				answers.add(resultSetToAnswer(results));
			}
			return answers;
				
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return null;
	}
	
	public static Quiz getQuiz(int quizId) {
		Quiz q=new Quiz();
		ContentTopic c= Courses.getContentTopic(quizId);
		q.setID(c.getID());
		q.setCourseId(c.getCourseId());
		q.setName(c.getName());
		q.setDescription(c.getDescription());
		q.setType(ContentType.QUIZ);
		
		q.setQuestions(getQuestionsForQuiz(quizId));
		
		return q;
	}
	
	public static int addQuiz(Quiz q) {
		try {
			int qid=Courses.addContentTopic(q); //first, add the quiz
			log.debug("successfully added the quiz with new id = "+qid);
			for (Question quest : q.getQuestions()) {
				quest.setQuizId(qid);
				addQuestion(quest);
			}
			return qid;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return -1;
	}
	
	/**
	 * 
	 * @param quizId
	 * @param userId
	 * @return null if the user has not taken the quiz, -1 on error
	 */
	public static Float getQuizScore(int quizId, int userId) {
		Quiz q=Courses.getQuiz(quizId);
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getQuizScore(?,?)}");
			procedure.setInt(1,userId);
			procedure.setInt(2,quizId);
			results=procedure.executeQuery();
			if (results.next()) {
				int points=results.getInt("score");
				return (new Float(points) / q.getQuestions().size());
			}
			return null;
		}  catch (Exception e) {
			log.error(e.getMessage(),e);
			return -1f;
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
	}
	
	/**
	 * Returns whether the given user has completed every quiz
	 * in the course
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public static boolean hasUserCompletedCourse(int userId, int courseId) {
		List<ContentTopic> topics=Courses.getContentTopicsForCourse(courseId);
		for (ContentTopic c : topics) {
			if (c.getType()!=ContentType.QUIZ) {
				continue;
			}
			if (!hasUserTakenQuiz(c.getID(),userId)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean hasUserTakenQuiz(int quizId, int userId) {
		return getQuizScore(quizId,userId)!=null;
	}
	
	public static boolean addAnswer(int userId, int answerId) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL addQuizAnswer(?,?)}");
			procedure.setInt(1,userId);
			procedure.setInt(2,answerId);
			
			procedure.executeUpdate();
			
			return true;
		}  catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return false;
	}
	
	public static boolean addQuizScores(int userId, int quizId, int score, List<Integer> answers) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL addQuizScore(?,?,?)}");
			procedure.setInt(1,userId);
			procedure.setInt(2,quizId);
			procedure.setInt(3,score);
			
			procedure.executeUpdate();
			for (Integer i : answers ){
				addAnswer(userId, i);
			}
			return true;
		}  catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return false;
	}
}
