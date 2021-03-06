package edutechonline.servlets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;




import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.ContentTopic;
import edutechonline.database.entity.ContentTopic.ContentType;
import edutechonline.database.entity.Course;
import edutechonline.database.entity.User;
import edutechonline.security.ValidatorStatusCode;
import edutechonline.util.Mail;
import edutechonline.util.Util;
import edutechonline.util.Validator;


	public class AddTopic extends HttpServlet 
	{
	private static Logger log=Logger.getLogger(Registration.class);
	
	private static final long serialVersionUID = 13432521466L;
       
	private static final String NAME="name";
	private static final String DESCRIPTION="desc";
	private static final String  COURSE_ID="course";
	private static final String URL="url";
	private static final String FILE="file";
	private static final String UPLOAD_TYPE="topicType";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("got a request to add a content topic");
		try {
			if(ServletFileUpload.isMultipartContent(request)) {	
				HashMap<String,Object> form=Util.parseMultipartRequest(request);
				log.debug("successfully processed the form");
				for (String s : form.keySet()) {
					log.debug(s);
				}
				ValidatorStatusCode status=isValidRequest(form,SessionFilter.getUserId(request));
				if (!status.isSuccess()) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, status.getMessage());
					return;
				}
				boolean file=((String)form.get(UPLOAD_TYPE)).equals("file");
				ContentTopic c=new ContentTopic();
				c.setName((String)form.get(NAME));
				c.setDescription((String)form.get(DESCRIPTION));
				c.setCourseId(Integer.parseInt((String)form.get(COURSE_ID)));
				FileItem topicFile=null;
				if (file) {
					topicFile = (FileItem)form.get(FILE);
					
					c.setType(topicFile.getName());
				} else {
					c.setType(ContentType.VIDEO);
					c.setUrl((String)form.get(URL));
				}
				
				//c.setType(ContentType.toStatusCode(Integer.parseInt((String)form.get(TYPE))));
				int id= Courses.addContentTopic(c);
				log.debug("added the topic successfully");
				if (id>0) {
					//success, need to store the file somewhere if one exists
					if (file) {
						File outputFile=new File(Courses.getAbsolutePathForTopic(id));
						outputFile.getParentFile().mkdirs();
						log.debug(outputFile.getAbsolutePath());
						// Save the uploaded file to disk
						log.debug(topicFile);
						topicFile.write(outputFile);
					}
					response.sendRedirect("/EduTechOnline/jsp/secure/courses/details.jsp?cid="+c.getCourseId());
					return;
				} else {
					//failure, internal error
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;

				}
			}
			
		}
	
		catch (Exception e) {
		log.error(e.getMessage(),e);
		}
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	}
	     
	public ValidatorStatusCode isValidRequest(HashMap<String,Object> request,int userId) {
		if (!Validator.isValidName((String)request.get(NAME))) {
			return new ValidatorStatusCode(false, "The given name is not valid");
		}
		
		
		
		if (!Validator.isValidDescription((String)request.get(DESCRIPTION))) {
			return new ValidatorStatusCode(false, "The given Description is not valid");
		}
		
		//if (!Validator.isValidInteger((String)request.get(TYPE))) {
		//	return new ValidatorStatusCode(false, "the given cost is not a valid number");
		//}
		//int type=Integer.parseInt((String)request.get((TYPE)));
		//if (ContentType.toStatusCode(type)==null) {
		//	return new ValidatorStatusCode(false, "the given type is not valid");
		//}
		
		if (!Validator.isValidInteger((String)request.get(COURSE_ID))) {
			return new ValidatorStatusCode(false, "the given course id is not a valid number");
		}
		int course=Integer.parseInt((String)request.get(COURSE_ID));
		Course c=Courses.getCourse(course);
		if (c==null || c.getOwnerId()!=userId) {
			return new ValidatorStatusCode(false, "you can only add topics to courses you own");
		}
		
		//then it must have a url
		String type=(String)request.get(UPLOAD_TYPE);
		if (type==null) {
			return new ValidatorStatusCode(false, "No type was specified");
		} else if (type.equals("url")) {
			String url=(String)request.get(URL);
			if (url==null || url.isEmpty()) {
				return new ValidatorStatusCode(false, "No URL or file was specified");
			}
		} else if (type.equals("file")) {
			FileItem topicFile = (FileItem)request.get(FILE);
			if (!Validator.isValidFileExtension(topicFile.getName())) {
				return new ValidatorStatusCode(false, "The given file does not have a valid extension");
			}

		} else {
			return new ValidatorStatusCode(false, "Invalid type");
		}
		
		
		log.debug("got all the way down here");
		return new ValidatorStatusCode(true);
	
	}
}

		