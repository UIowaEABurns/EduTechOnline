package application;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import database.ConnectionPool;
import database.Users;
public class EduTechOnline implements ServletContextListener {

		
		@Override
		public void contextDestroyed(ServletContextEvent arg0) {
			try {
			    
			    ConnectionPool.release();
			
			} catch (Exception e) {
			 
			}		
		}

		/**
		 * When the application starts, this method is called. Perform any initializations here
		 */	
		@Override
		public void contextInitialized(ServletContextEvent event) {				
			System.out.println("started up!");
			
			// Initialize the datapool after properties are loaded
			ConnectionPool.initialize();
			Users.testDatabaseConnection();
		}	

}
		
	


