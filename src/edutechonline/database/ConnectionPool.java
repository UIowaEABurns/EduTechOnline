package edutechonline.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import edutechonline.application.Constants;

/**
 * This class will contain our pool of MYSQL database connections
 * 
 */
public class ConnectionPool {
		private static Logger log=Logger.getLogger(ConnectionPool.class);


		private static DataSource dataPool = null;		
	
		
		/**
		 * Begins a transaction by turning off auto-commit
		 */
		protected static void beginTransaction(Connection con){
			try {
				con.setAutoCommit(false);
			} catch (Exception e) {
				// Ignore any errors
			}
		}
		
		/**
		 * Rolls back any actions not committed to the database
		 */
		protected static void doRollback(Connection con){
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (Exception e) {
				// Ignore any errors
			}
		}
		
		/**
		 * Turns on auto-commit
		 */
		protected static void enableAutoCommit(Connection con){
			try {
				con.setAutoCommit(true);
			} catch (Exception e) {
				// Ignore any errors
			}
		}							
		
		/**
		 * Ends a transaction by committing any changes and re-enabling auto-commit
		 */
		protected static void endTransaction(Connection con){
			try {
				con.commit();
				enableAutoCommit(con);
			} catch (Exception e) {
				ConnectionPool.doRollback(con);
			}
		}	
		
	
		protected synchronized static Connection getConnection() throws SQLException {	
			
			
			return dataPool.getConnection();
		}
		
		
		public static void initialize() {						
			try {
				
			
				PoolProperties poolProp = new PoolProperties();				// Set up the Tomcat JDBC connection pool with the following properties
				

				poolProp.setUrl(Constants.MYSQL_URL);								// URL to the database we want to use
				poolProp.setDriverClassName("com.mysql.jdbc.Driver");				// We're using the JDBC driver
				poolProp.setUsername(Constants.MYSQL_USERNAME);								// Database username
				poolProp.setPassword(Constants.MYSQL_PASSWORD);							// Database password for the given username
				poolProp.setTestOnBorrow(true);								// True to check if a connection is live every time we take one from the pool
				poolProp.setValidationQuery("SELECT 1");					// The query to execute to check if a connection is live
				poolProp.setValidationInterval(20000);						// Only check live connection every so often when borrowing (milliseconds)
				poolProp.setMaxActive(100);				// How many active connections can we have in the pool
				poolProp.setInitialSize(100);				// How many connections the pool will start out with
				poolProp.setMinIdle(100);					// The minimum number of connections to keep "ready to go"
				poolProp.setDefaultAutoCommit(true);						// Turn autocommit on (turn transactions off by default)
				poolProp.setJmxEnabled(false);								// Turn JMX off (we don't use it so we don't need it)
				poolProp.setRemoveAbandonedTimeout(300);						// How int to wait (seconds) before reclaiming an open connection (should be the time of intest query)
				poolProp.setRemoveAbandoned(true);							// Enable removing connections that are open too int
				
				dataPool = new DataSource(poolProp);						// Create the connection pool with the supplied properties
			
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		
		/**
		 * Cleans up the database connection pool. This class must be reinitialized after this is called. 
		 */
		public static void release() {
			if(dataPool != null) {
				dataPool.close();
			}
		}
		
		private static String getProc(String procName,Object[] args) {
			StringBuilder procBuilder=new StringBuilder();
			procBuilder.append("{CALL ");
			procBuilder.append(procName);
			procBuilder.append("(");
			for (int i =0;i<args.length;i++) {
				procBuilder.append("?");
				if (i<args.length-1) {
					procBuilder.append(",");
				}
			}
			procBuilder.append(")}");
			return procBuilder.toString();
		}
		
		/**
		 * Executes a database update
		 * @param procName Name of the procedure to execute
		 * @param args
		 */
		protected static void executeUpdate(String procName, Object... args) {
			Connection con=null;
			CallableStatement procedure=null;
			try {
				String proc=getProc(procName,args);
				con=ConnectionPool.getConnection();
				procedure=con.prepareCall(proc);
				for (int i=0;i<args.length;i++) {
					procedure.setObject(i+1, args[i]);
				}
				
				procedure.executeUpdate();
			} catch (Exception e) {
				log.error("internal error calling procedure "+procName);
				log.error(e.getMessage(),e);
			} finally {
				ConnectionPool.safeClose(con);
				ConnectionPool.safeClose(procedure);
			}
		}
		
		
		protected static void safeClose(CallableStatement statement) {
			
			try {
				if (statement!=null) {
					statement.close();
				}
			
			} catch (Exception e) {
			}	
		}
		
		/**
		 * Method which safely closes a connection pool connection
		 * and doesn't raise any errors
		 * @param c The connection to safely close
		 */
		protected static synchronized void safeClose(Connection c) {
			try {
				if(c != null && !c.isClosed()) {
					c.close();
					
					//log.info("Connection Closed, Net connections opened = " + (connectionsOpened-connectionsClosed));
					//String methodName1=Thread.currentThread().getStackTrace()[2].getMethodName();
					//String methodName2=Thread.currentThread().getStackTrace()[2].getMethodName();
					//log.info("stack trace info for the closed connection is "+methodName1+ " "+methodName2);
				}
			} catch (Exception e){
				// Do nothing
			}

		}
		
		/**
		 * Method which safely closes a prepared Statement
		 * and doesn't raise any errors
		 * @param p the prepared statement to close
		 */
		protected static synchronized void safeClose(PreparedStatement p) {
			try {
				if(p != null && !p.isClosed()) {
					p.close();
					
				
				}
			} catch (Exception e){
			
			}

		}
		
		/**
		 * Method which closes a result set
		 * @param r The result set close
		 */
		protected static void safeClose(ResultSet r) {
			try {
				if(r != null) {
					
					r.close();
				}
			} catch (Exception e){
			}
		}
	
}
