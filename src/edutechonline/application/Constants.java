package edutechonline.application;

import java.io.File;

public class Constants {
	public static String APP_ROOT=null; //represents the absolute path to the root of this web application
	
	public static String CONFIRM_EMAIL_PATH="/WEB-INF/classes/edutechonline/configuration/messages/confirmation.txt"; //path to confirmation email
	public static String PASSWORD_RESET_EMAIL_PATH="/WEB-INF/classes/edutechonline/configuration/messages/resetPass.txt"; //path to confirmation email

	public static String LOG4J_PATH = "/WEB-INF/classes/edutechonline/configuration/log4j.properties"; //path to log config file
	public static String CONFIG_PATH=null;



	public static String MYSQL_DATABASE=null;
	public static String MYSQL_USERNAME=null;
	public static String MYSQL_PASSWORD=null;
	public static String MYSQL_URL=null;
	public static String URL_PREFIX =null;
	public static String SERVERNAME=null;
	public static String WEB_APP_ROOT="EduTechOnline";
	
	
	//file that will point to our content topic containing directory
	public static File contentTopicDirectory=null;
	
	//length maxima
	public static int EMAIL_LENGTH=64;
	public static int NAME_LENGTH=32;
	public static int PASS_LENGTH=255;
	public static int DESC_LENGTH=1024;
	public static int CATEGORY_LENGTH=32;
	//regular expressions
	public static String NAME_REGEX = "^[\\w\\s-]+$";
	public static String EMAIL_REGEX = "^[\\w-%+\\.]+@[a-zA-Z0-9]+\\.[\\w]{2,4}$";
	public static String DESC_REGEX=".*";
}
