package edutechonline.util;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import edutechonline.application.Constants;

public class Validator {
	private static Logger log =Logger.getLogger(Validator.class);
	private static Pattern namePattern;
	private static Pattern emailPattern;
	private static Pattern descPattern;
	public static void compilePatterns() {
		namePattern=Pattern.compile(Constants.NAME_REGEX,Pattern.CASE_INSENSITIVE);
		emailPattern=Pattern.compile(Constants.EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		descPattern=Pattern.compile(Constants.DESC_REGEX,Pattern.CASE_INSENSITIVE);
	}
	
	/**
	 * Checks to see whether the given email is valid
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		if (email==null) {
			return false;
		}
		if (!emailPattern.matcher(email).matches()) {
			return false;
		}
		if (email.length() > Constants.EMAIL_LENGTH)  {
			return false;
		}
		return true;
	}
	
	public static boolean isValidDescription(String desc) {
		if (desc==null) {
			return false;
		}
		if (!descPattern.matcher(desc).matches()) {
			return false;
		}
		if (desc.length()>Constants.DESC_LENGTH) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidCategory(String name) {
		if (name==null) {
			return false;
		}
		if (!namePattern.matcher(name).matches()) {
			return false;
		}
		if (name.length() > Constants.CATEGORY_LENGTH || name.length()==0)  {
			return false;
		}
		return true;
	}
	
	
	public static boolean isValidName(String name) {
		if (name==null) {
			return false;
		}
		if (!namePattern.matcher(name).matches()) {
			log.debug("invalid name");
			return false;
		}
		if (name.length() > Constants.NAME_LENGTH)  {
			return false;
		}
		return true;
	}
	
	public static boolean isValidPassword(String pass) {
		
		if (pass==null) {
			return false;
		}
		
		if (pass.length()>Constants.PASS_LENGTH) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidInteger(String f) {
		try {
			Integer.parseInt(f);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isValidFloat(String f) {
		try {
			Float.parseFloat(f);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isValidRole(String role) {
		if (role==null) {
			return false;
		}
		if (role.equals("admin") || role.equals("unverified") || role.equals("user") || role.equals("manager")) {
			return true;
		}
		return false;
	}
}
