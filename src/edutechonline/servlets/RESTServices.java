package edutechonline.servlets;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;


import org.apache.log4j.Logger;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import edutechonline.database.Users;
import edutechonline.security.ValidatorStatusCode;

/**
 * This class is responsible for handling all small requests to the server. In other words, this is the place
 * to put handlers for requests that take only a single parameter or two, as writing full servlets for these is not necessary
 */
@Path("")
public class RESTServices {	
	private static final Logger log = Logger.getLogger(RESTServices.class);			
	private static Gson gson = new Gson();
	
	private static ValidatorStatusCode DATABASE_ERROR=new ValidatorStatusCode(false, "internal database error");
	
	@POST
	@Path("/delete/user/{userId}")
	@Produces("application/json")	
	public String deleteUser(@PathParam("userId") int userId, @Context HttpServletRequest request) {					
		int requestUserId = SessionFilter.getUserId(request);
		log.debug("got a request to delete user ID = "+userId);
		//TODO: validate here
		ValidatorStatusCode status=new ValidatorStatusCode(true);
		if (!status.isSuccess()) {
			return gson.toJson(status);
		}
		boolean success=Users.deleteUser(userId);
		return success ? gson.toJson(new ValidatorStatusCode(true, "recompilation successful")) :  gson.toJson(DATABASE_ERROR);
	}
	
	@POST
	@Path("/promote/user/{userId}")
	@Produces("application/json")	
	public String promoteUser(@PathParam("userId") int userId, @Context HttpServletRequest request) {					
		int requestUserId = SessionFilter.getUserId(request);
		log.debug("got a request to promote user ID = "+userId);
		//TODO: validate here
		ValidatorStatusCode status=new ValidatorStatusCode(true);
		if (!status.isSuccess()) {
			return gson.toJson(status);
		}
		boolean success=Users.updateRole(userId, "manager");
		return success ? gson.toJson(new ValidatorStatusCode(true, "recompilation successful")) :  gson.toJson(DATABASE_ERROR);
	}
	
	@POST
	@Path("/session/logout")
	@Produces("application/json")	
	public String doInvalidateSession(@Context HttpServletRequest request) {	
		request.getSession().invalidate();
		return gson.toJson(new ValidatorStatusCode(true));
	}	
	
	
	
}