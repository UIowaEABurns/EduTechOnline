package application;


import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import database.ConnectionPool;
import database.Users;
public class EduTechOnline implements ServletContextListener {
		private static final Logger log=Logger.getLogger(EduTechOnline.class);
		private static String LOG4J_PATH = "/WEB-INF/classes/configuration/log4j.properties";

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
			String root = event.getServletContext().getRealPath("/");
			// Before we do anything we must configure log4j!
			PropertyConfigurator.configure(new File(root, LOG4J_PATH).getAbsolutePath());
			System.out.println("started up!");
			log.debug("this is my logger");
			// Initialize the datapool after properties are loaded
			ConnectionPool.initialize();
			Users.testDatabaseConnection();
		}	

}
		
	


