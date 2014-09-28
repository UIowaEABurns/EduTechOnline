package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class Users {
	
	
	public static void testDatabaseConnection() {
		Connection con=null;
		CallableStatement procedure=null;
		ResultSet results=null;
		try {
			con=ConnectionPool.getConnection();
			procedure=con.prepareCall("select * from users");
			results=procedure.executeQuery();
			while (results.next()) {
				System.out.println(results.getInt("id"));
			}
		} catch (Exception e) {
			
		} finally {
			ConnectionPool.safeClose(con);
			ConnectionPool.safeClose(procedure);
		}
	}
}
