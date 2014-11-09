package edutechonline.database;

import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import edutechonline.database.entity.Course;
import edutechonline.database.entity.User;
import edutechonline.util.Util;

public class Users {
	private static Logger log=Logger.getLogger(Users.class);
	
	
	public static int getUserIdByCode(String code) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL GetUserByCode(?)}");
			procedure.setString(1, code);
			results=procedure.executeQuery();
			if (results.next()) {
				return results.getInt("user_id");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return -1;
	}
	
	/**
	 * Sets a user's role to a new value
	 * @param userID the ID of the user to update
	 * @param role The new role to give the user
	 * @return
	 */
	public static boolean updateRole(int userId, String role) {
		Connection con=null;
		CallableStatement procedure=null;
		
		try {
			User u=Users.getUser(userId);
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL updateRole(?,?)}");
			procedure.setString(1,  u.getEmail());
			procedure.setString(2,role);
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
	 * Permanently removes a user from the database. Used by administrators
	 * @param userId The ID of the user to remove.
	 * @return
	 */
	public static boolean deleteUser(int userId) {
		Connection con=null;
		CallableStatement procedure=null;
		
		try {
			User u=Users.getUser(userId);
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL deleteUser(?)}");
			procedure.setInt(1, u.getID());
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
	 * Verifies the user with the given verification code
	 * @param code
	 * @return
	 */
	public static boolean verifyUser(String code, String role) {
		Connection con=null;
		CallableStatement procedure=null;
		
		try {
			int userId=getUserIdByCode(code);
			User u=Users.getUser(userId);
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL verifyUser(?,?,?)}");
			procedure.setInt(1,  u.getID());
			procedure.setString(2,u.getEmail());
			procedure.setString(3,role);
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
	 * Takes a result set that is currently pointing at a user and returns the User object
	 * @param results
	 * @return
	 */
	private static User resultSetToUser(ResultSet results) {
		try {
			User u=new User();
			u.setFirstName(results.getString("first_name"));
			u.setLastName(results.getString("last_name"));
			u.setEmail(results.getString("users.email"));
			u.setPassword(results.getString("password"));
			u.setID(results.getInt("id"));
			u.setRole(results.getString("role"));
			return u;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
		
	}
	
	/**
	 * Returns a list of every user in the database
	 * @return A list of all user objects
	 */
	public static List<User> getAllUsers() {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getAllUsers()}");
			
			results=procedure.executeQuery();
			List<User> users=new ArrayList<User>();
			while (results.next()) {
				users.add(resultSetToUser(results));
			}
			return users;
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
	 * Gets a user from the database given their ID
	 * @param id The ID of the user to retrieve
	 * @return The User object or null if they could not be found
	 */
	
	public static User getUser(int id) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getUser(?)}");
			procedure.setInt(1, id);
			results=procedure.executeQuery();
			if (results.next()) {
				return resultSetToUser(results);
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
	 * Gets a user from the database given their email
	 * @param email The email address of the user to retrieve
	 * @return The User object or null if they could not be found
	 */
	public static User getUser(String email) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getUserByEmail(?)}");
			procedure.setString(1, email);
			results=procedure.executeQuery();
			if (results.next()) {
				return resultSetToUser(results);
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
	 * This is for adding users directly to the database through the config file.
	 * For new users, you need to call registerUser!
	 * @param u The user to add
	 * @param role The role to give the user
	 * @return The int id of the new user
	 */
	public static int addUser(User u) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL addUser(?,?,?,?,?,?)}");
			procedure.setString(1, u.getFirstName());
			procedure.setString(2, u.getLastName());
			procedure.setString(3, u.getEmail());
			// encoder used to hash password for storage
			String hashedPass=Util.hashPassword(u.getPassword());
			
			procedure.setString(4, hashedPass);
			procedure.setString(5, u.getRole());
			procedure.registerOutParameter(6, java.sql.Types.INTEGER);
			procedure.executeUpdate();
			
			u.setID(procedure.getInt(6));
			
			return u.getID();
		} catch (Exception e) {
			ConnectionPool.doRollback(con);
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return -1;
	}
	
	public static boolean addPassResetToUser(int userId, String code) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure= con.prepareCall("{CALL addUserPassReset(?,?)}");
			procedure.setInt(1, userId);
			procedure.setString(2,code);
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
	 * Checks to see if there is a row with both the given user and the given row in the
	 * pass_eset table
	 * @param userId
	 * @param code
	 * @return
	 */
	public static boolean userInPassResetTable(int userId, String code) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure= con.prepareCall("{CALL getUserPassReset(?)}");
			procedure.setInt(1, userId);
			
			results=procedure.executeQuery();
			if (results.next()) {
				return results.getString("code").equals(code);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return false;
	}
	
	/**
	 * Removes password reset requests more than a day old
	 */
	public static void clearOldPassResetRequests() {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL clearPassResetEntriesOlderThan(?)}");
			Date yesterday=new Date(System.currentTimeMillis()-86400000);
			procedure.setTimestamp(1, new Timestamp(yesterday.getTime()));
			procedure.executeUpdate();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
	
	}
	
	/**
	 * Checks to see whether the given user has a request in the pass_reset table
	 * @param userId
	 * @return
	 */
	public static boolean userInPassResetTable(int userId) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure= con.prepareCall("{CALL getUserPassReset(?)}");
			procedure.setInt(1, userId);
			
			results=procedure.executeQuery();
			if (results.next()) {
				return true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
			ConnectionPool.safeClose(results);
		}
		return false;
	}
	
	
	/**
	 * Updates the personal information of a user. Does not update the users password or their role!
	 * Those attributes need to be modified separately. Email is currently immutable
	 * @param u A User object with all of its fields set
	 * @return True on success and false otherwise
	 */
	public static boolean updateUser(User u) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL updateUser(?,?,?)}");
			procedure.setInt(1,u.getID());
			procedure.setString(2, u.getFirstName());
			procedure.setString(3, u.getLastName());
			
			procedure.executeUpdate();
			return true;
		} catch (Exception e) {
			ConnectionPool.doRollback(con);
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return  false;
	}
	

	/**
	 * Registers a new user in the database. 
	 * @param u A User object representing the user to be added. Every field except the ID must be set!
	 * Password must be in plain text!
	 * @return the int id of the new user, or -1 on error.
	 */
	public static int registerUser(User u, String verificationCode) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL registerNewUser(?,?,?,?,?,?)}");
			procedure.setString(1, u.getFirstName());
			procedure.setString(2, u.getLastName());
			procedure.setString(3, u.getEmail());
			// encoder used to hash password for storage
			String hashedPass=Util.hashPassword(u.getPassword());

			procedure.setString(4, hashedPass);
			procedure.setString(5, verificationCode);
			procedure.registerOutParameter(6, java.sql.Types.INTEGER);
			procedure.executeUpdate();
			
			u.setID(procedure.getInt(6));
			return u.getID();
		} catch (Exception e) {
			ConnectionPool.doRollback(con);
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return -1;
	}
	
	/**
	 * Updates the password of the current user. The password must be given in cleartext!
	 * @param userId
	 * @param newPass
	 * @return
	 */
	public static boolean updatePassword(int userId, String newPass) {
		Connection con=null;
		CallableStatement procedure=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL updatePassword(?,?)}");
			procedure.setInt(1, userId);
			// encoder used to hash password for storage
			String hashedPass=Util.hashPassword(newPass);
			
			procedure.setString(2, hashedPass);
			procedure.executeUpdate();
			
		
			
			return true;
		} catch (Exception e) {
			ConnectionPool.doRollback(con);
			log.error(e.getMessage(),e);
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
		return false;
	}
	
	public static boolean isAdmin(int userId) {
		User u=Users.getUser(userId);
		if (u!=null && u.getRole().equals("admin")) {
			return true;
		}
		return false;
	}
	
	public static boolean isStudent(int userId) {
		User u=Users.getUser(userId);
		if (u!=null && u.getRole().equals("user")) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Gets the ID of every user enrolled in the given course
	 * @param userId
	 * @return
	 */
	public static List<Course> getCoursesByUser(int userId) {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("{CALL getCoursesByUser(?)}");
			procedure.setInt(1,userId);
			
			results=procedure.executeQuery();
			List<Course> answers=new ArrayList<Course>();
			while (results.next()) {
				answers.add(Courses.getCourse(results.getInt("course_id")));
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
}
