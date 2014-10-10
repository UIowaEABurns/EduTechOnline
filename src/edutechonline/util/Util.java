package edutechonline.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import edutechonline.application.Constants;

public class Util {
	
	public static String getAbsoluteURL() {
		StringBuilder sb=new StringBuilder();
		sb.append(Constants.URL_PREFIX);
		sb.append("://");
		sb.append(Constants.SERVERNAME);
		sb.append("/");
		sb.append(Constants.WEB_APP_ROOT);
		sb.append("/");
		return sb.toString();
	}
	
	public static String getAbsoluteURL(String postFix) {
		return getAbsoluteURL()+postFix;
	}
	
	/**
	 * Converts an array of bytes to a hexadecimal string. 
	 */
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static String hashPassword(String pass) throws NoSuchAlgorithmException {
		MessageDigest hasher = MessageDigest.getInstance("sha-512");
		hasher.update(pass.getBytes());
		// get the hashed version of the password
		
		return bytesToHex(hasher.digest());

	}
	
	/**
	 * Generates a random password that would be suitable as a temporary pass for a user
	 * @return
	 */
	public static String getRandomPlaintextPassword() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		Random rand=new Random();
		StringBuilder sb=new StringBuilder();
		for (int x=0;x<16;x++) {
			sb.append(rand.nextInt(chars.length));
		}
		return sb.toString();
	}
}
