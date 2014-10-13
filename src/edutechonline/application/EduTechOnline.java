package edutechonline.application;


import java.io.File;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edutechonline.database.ConnectionPool;
import edutechonline.database.Users;
import edutechonline.test.TestManager;
import edutechonline.util.Mail;
import edutechonline.util.Util;
import edutechonline.util.Validator;
import edutechonline.configuration.*;
public class EduTechOnline implements ServletContextListener {
		private static final Logger log=Logger.getLogger(EduTechOnline.class);
	    private ScheduledExecutorService schedule = Executors.newScheduledThreadPool(10);	

	    

		@Override
		public void contextDestroyed(ServletContextEvent arg0) {
			try {
			    schedule.shutdown();
			    ConnectionPool.release();
			    
			    // Wait for the task scheduler to finish
			    schedule.awaitTermination(10, TimeUnit.SECONDS);
			    schedule.shutdownNow();
			
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

			Constants.CONFIG_PATH = new File(Constants.APP_ROOT, "/WEB-INF/classes/edutechonline/configuration/").getAbsolutePath();
			ConfigUtil.loadProperties(new File(Constants.CONFIG_PATH, "config.xml"));
			Validator.compilePatterns();
			// Before we do anything we must configure log4j!
			// Initialize the datapool after properties are loaded
			ConnectionPool.initialize();
			ConfigUtil.loadUsersFromConfig(new File(Constants.CONFIG_PATH, "config.xml"));			
			
			final Runnable clearOldPassResetRequests = new Runnable() {
				@Override
				public void run() {
					
				}
			};
			
			schedule.scheduleAtFixedRate(clearOldPassResetRequests, 0, 8, TimeUnit.HOURS);
			TestManager.initializeTests();
			TestManager.executeAllTestSequences();

			
		}	

}
		
	


