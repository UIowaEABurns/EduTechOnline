package edutechonline.application;


import java.io.File;
import java.util.UUID;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edutechonline.database.ConnectionPool;
import edutechonline.database.Users;
import edutechonline.util.Mail;
import edutechonline.util.Util;
import edutechonline.configuration.*;
public class EduTechOnline implements ServletContextListener {
		private static final Logger log=Logger.getLogger(EduTechOnline.class);
		

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
			Constants.APP_ROOT=root;
			PropertyConfigurator.configure(new File(Constants.APP_ROOT, Constants.LOG4J_PATH).getAbsolutePath());

			// Setup the path to starexec's configuration files
			Constants.CONFIG_PATH = new File(Constants.APP_ROOT, "/WEB-INF/classes/edutechonline/configuration/").getAbsolutePath();
			// Load all properties from the starexec-config file
			ConfigUtil.loadProperties(new File(Constants.CONFIG_PATH, "config.xml"));
			// Before we do anything we must configure log4j!
			// Initialize the datapool after properties are loaded
			ConnectionPool.initialize();
			
			//Mail.sendConfirmationEmail(Users.getUser(1),UUID.randomUUID().toString());
		}	

}
		
	

