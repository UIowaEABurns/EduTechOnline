package edutechonline.util;

import java.util.regex.Pattern;

import edutechonline.application.Constants;

public class Validator {
	private static Pattern namePattern;
	private static Pattern emailPattern;
	
	public static void compilePatterns() {
		namePattern=Pattern.compile(Constants.NAME_REGEX);
		emailPattern=Pattern.compile(Constants.EMAIL_REGEX);
	}
	
	
	public static boolean isValidEmail(String email) {
		
		if (!emailPattern.matcher(email).matches()) {
			return false;
		}
		if (email.length() > Constants.EMAIL_LENGTH)  {
			return false;
		}
		return true;
	}
	
	public static boolean isValidName(String name) {
		if (!namePattern.matcher(name).matches()) {
			return false;
		}
		if (name.length() > Constants.EMAIL_LENGTH)  {
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
}
