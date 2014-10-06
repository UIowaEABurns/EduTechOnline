package edutechonline.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edutechonline.database.entity.User;

public class Users {
	private static Logger log=Logger.getLogger(Users.class);
	
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
}
