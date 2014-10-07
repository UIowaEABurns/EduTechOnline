package edutechonline.application;

public class Constants {
	public static String APP_ROOT=null; //represents the absolute path to the root of this web application
	
	public static String CONFIRM_EMAIL_PATH="/WEB-INF/classes/edutechonline/configuration/confirmation.txt"; //path to confirmation email
	
	public static String LOG4J_PATH = "/WEB-INF/classes/edutechonline/configuration/log4j.properties"; //path to log config file
	public static String CONFIG_PATH=null;



	public static String MYSQL_DATABASE=null;
	public static String MYSQL_USERNAME=null;
	public static String MYSQL_PASSWORD=null;
	public static String MYSQL_URL=null;
	public static String URL_PREFIX =null;
	public static String SERVERNAME=null;
	public static String WEB_APP_ROOT="EduTechOnline";
	
	//length maxima
	public static int EMAIL_LENGTH=64;
	public static int NAME_LENGTH=32;
	public static int PASS_LENGTH=255;
	
	//regular expressions
	public static String NAME_REGEX = "^[\\w\\s'-]$";
	public static String EMAIL_REGEX = "^[\\w-%+\\.]+@[a-zA-Z0-9]+\\.[\\w]{2,4}$";
}
