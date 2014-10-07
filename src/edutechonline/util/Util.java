package edutechonline.util;

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
}
