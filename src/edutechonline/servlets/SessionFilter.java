package edutechonline.servlets;

/**
 * Very simply filter that simply adds the user ID to this session
 */

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import edutechonline.database.Users;
import edutechonline.database.entity.User;

public class SessionFilter implements Filter {
	private static Logger log=Logger.getLogger(SessionFilter.class);
	private static final String USER_ID="edu_user_id";
	@Override
	public void destroy() {
		// empty
		
	}
	
	/**
	 * Returns the user ID of the user who made this request, or -1 if the user is not logged in
	 * @param request
	 * @return
	 */
	public static int getUserId(HttpServletRequest request) {
		if (request.getUserPrincipal()==null) {
			return -1;
		}
		return (Integer)request.getSession().getAttribute(USER_ID);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			System.out.println("hit this filter");
			HttpServletRequest httpRequest=(HttpServletRequest) request;
			//if the user is logged in
			if (httpRequest.getUserPrincipal()!=null) {
				String email = httpRequest.getUserPrincipal().getName();
				System.out.println("found this email address "+email);
				User u=Users.getUser(email);
				httpRequest.getSession().setAttribute(USER_ID,u.getID());
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		//pass the request on
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// empty
		
	}

}
