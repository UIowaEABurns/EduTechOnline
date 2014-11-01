package edutechonline.database;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;





import edutechonline.application.Constants;
import edutechonline.database.entity.ContentTopic;
import edutechonline.database.entity.ContentTopic.ContentType;
import edutechonline.database.entity.Course;
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
		System.out.println(c.getCourseId());
		System.out.println(Courses.getCourse(c.getCourseId()).getName());
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
	
	public static String getTopicURL(int topicId) {
		ContentTopic c=Courses.getContentTopic(topicId);
		String url=Util.getAbsoluteURL("jsp/secure/content/"+c.getCourseId()+"/"+c.getID());
		url=url+"/"+c.getName()+".pdf"; //todo: change
		System.out.println(url);
		return url;
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
		f=new File(f, c.getName()+".pdf"); //todo: change based on type
		return f.getAbsolutePath();
	}
	
}
