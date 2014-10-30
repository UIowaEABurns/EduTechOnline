package edutechonline.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import edutechonline.application.Constants;

public class Util {
	private static Logger log=Logger.getLogger(Util.class);
	
	
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
			sb.append(chars[rand.nextInt(chars.length)]);
		}
		return sb.toString();
	}
	
	/**
     * Parses a multipart request and returns a hashmap of form parameters
     * @param request The request to parse
     * @return A hashmap containing the field name to field value mapping
     */
    public static HashMap<String, Object> parseMultipartRequest(HttpServletRequest request) throws Exception {
		// Use Tomcat's multipart form utilities
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);
		HashMap<String, Object> form = new HashMap<String, Object>();
			
		for(FileItem f : items) {
		    // If we're dealing with a regular form field...
		    if(f.isFormField()) {
			// Add the field name and field value to the hashmap
			form.put(f.getFieldName(), f.getString());				
		    } else {
			// Else we've encountered a file, so add the FileItem to the hashmap
			form.put(f.getFieldName(), f);					
		    }	
		}
			
		return form;
    }
}
