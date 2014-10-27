package edutechonline.test;

import java.util.Random;

/***
 * Originally written for the Starexec project
 * @author: Eric Burns
 */

public class TestUtil {
	private static String[] letters={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	private static Random rnd=new Random();
	
	/**
	 * produces a random name of the maximum length for a new space. Useful for testing
	 * @return
	 */
	
	
	public static String getRandomAlphaString(int length) {
		String name="";
		while (length>0) {
			name=name+letters[rnd.nextInt(letters.length)];
			length--;
		}
		return name;
	}
	
	/**
	 * Gets a random name for a user
	 * @return
	 */
	public static String getRandomUserName() {
		return getRandomAlphaString(8);
	}
	
	public static String getRandomEmail() {
		return getRandomAlphaString(20)+"@test.edu";
	}
	
	public static String getRandomPassword() {
		return getRandomAlphaString(16);
	}
	
	public static String getErrorTrace(Throwable error) {
		if (error==null) {
			return "no error";
		}
		StringBuilder sb=new StringBuilder();
		StackTraceElement[] trace=error.getStackTrace();
		for (StackTraceElement te : trace) {
			sb.append(te.toString()+"\n");
		}
		return sb.toString();
	}
	
	
}
