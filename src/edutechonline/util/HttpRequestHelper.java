package edutechonline.util;

import javax.servlet.http.HttpServletRequest;

/**
 * This class contains utility functions for getting parameters back from HttpRequest objects
 * @author Eric
 *
 */
public class HttpRequestHelper {
	
	/**
	 * Checks to see whether a parameter with the given name exists in the given request
	 * @param name The name of the parameter
	 * @param request The HttpServletRequest object
	 * @return True if the param exists and false otherwise
	 */
	public static boolean paramExists(String name, HttpServletRequest request){
		return !isNullOrEmpty(request.getParameter(name));
	}
			
	 public static boolean isNullOrEmpty(String s){
		 	return (s == null || s.trim().length() <= 0);
	 }	
}
