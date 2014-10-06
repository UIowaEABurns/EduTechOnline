package edutechonline.util;


import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import edutechonline.application.Constants;


/**
 * Contains utilities for sending mail from the local SMTP server
 */
public class Mail {
	private static final Logger log = Logger.getLogger(Mail.class);
	
	/**
	 * Sends a registration confirmation email to the given 
	 * @param userId
	 * @param uniqueID
	 */
	public static void sendConfirmationEmail(String name, String uniqueID) {
		try {
			String message=FileUtils.readFileToString(new File(Constants.APP_ROOT,Constants.CONFIRM_EMAIL_PATH));
			message.replace("$$USER$$", name);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
	}
	
	/**
	 * Sends an e-mail from the configured SMTP server
	 * @param message The body of the message
	 * @param subject The subject of the message
	 * @param from Who the message is from (null to send from default account)
	 * @param to The list of e-mail addresses to send the message to
	 */
	public static void mail(String message, String subject, String[] to) {
		try {
			if(to == null || to.length < 1) {
				return;
			}					
			
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setSubject(subject);
			email.setMsg(message);
			email.setAuthenticator(new DefaultAuthenticator("NoReply.EduTechOnline@gmail.com", "ceufpxj1"));
			email.setTLS(true);
			
			
			
			email.setFrom("NoReply.EduTechOnline@gmail.com");
			
			
			for(String s : to) {
				email.addTo(s);
			}
			
			email.send();
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
	}	    
  
    

	
   

	
    
   

	
	
	
    
    
}
