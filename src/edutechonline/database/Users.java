package edutechonline.database;

import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edutechonline.database.entity.User;

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
			u.setEmail(results.getString("email"));
			u.setPassword(results.getString("password"));
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
			ConnectionPool.safeClose(con);
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
			ConnectionPool.safeClose(con);
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
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(results);
		}
		return null;
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
			ConnectionPool.beginTransaction(con);
			procedure=con.prepareCall("{CALL AddUser(?,?,?,?,?,?)}");
			procedure.setString(1, u.getFirstName());
			procedure.setString(2, u.getLastName());
			procedure.setString(3, u.getEmail());
			// encoder used to hash password for storage
			MessageDigest hasher = MessageDigest.getInstance("sha-512");
			hasher.update(u.getPassword().getBytes());
			// get the hashed version of the password
			
			String hashedPass = new String(hasher.digest());
			procedure.setString(4, hashedPass);
			procedure.setString(5, verificationCode);
			procedure.registerOutParameter(6, java.sql.Types.INTEGER);
			procedure.executeUpdate();
			
			u.setID(procedure.getInt(6));

			
			return u.getID();
		} catch (Exception e) {
			ConnectionPool.doRollback(con);
			log.error(e.getMessage(),e);
		} 
		return -1;
	}
}
