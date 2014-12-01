package edutechonline.security;

/***
 * Originally written for the Starexec project
 * @author: Eric Burns
 */

import com.google.gson.annotations.Expose;

/**
 * This is a simple class for reporing on the status of some operation. It contains both a boolean
 * to represent success and a string in case an error message is required. It also has a status code
 * in case additional info is needed. Basically, it is just a way to return both a boolean and a reason
 * at the same time
 * @author Eric
 *
 */

public class ValidatorStatusCode {
	
	//@Expose tags just mean these fields are visible when sending objects to the client using gson
	@Expose private boolean success;
	@Expose private String message;
	@Expose private int statusCode;
	
	public ValidatorStatusCode(boolean s, String m) {
		setSuccess(s);
		setMessage(m);
		setStatusCode(0);
	}
	
	public ValidatorStatusCode(boolean s) {
		success=s;
		message="";
		setStatusCode(0);
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
