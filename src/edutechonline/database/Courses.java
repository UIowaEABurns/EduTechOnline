package edutechonline.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import edutechonline.database.entity.Course;

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
			c.setOwnerId(results.getInt("id"));
			c.setOpen(results.getBoolean("open"));
			c.setCategory(results.getString("category"));
			return c;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
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
			procedure=con.prepareCall("{CALL addCourse(?,?,?,?,?,?)}");
			procedure.setString(1,c.getName());
			procedure.setString(2, c.getDescription());
			procedure.setInt(3,c.getOwnerId());
			procedure.setFloat(4, c.getCost());
			procedure.setBoolean(5, c.isOpen());
			procedure.registerOutParameter(6, java.sql.Types.INTEGER);
			procedure.executeUpdate();

			return procedure.getInt(6);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return -1;
		
	}
	
	
	/**
	 * Retrieves the given course from the database
	 * @param id
	 * @return
	 */
	public static boolean deleteCourse(int id) {
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
}
