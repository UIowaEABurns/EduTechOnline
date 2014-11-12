<%@page contentType="text/html" pageEncoding="UTF-8" import="edutechonline.servlets.SessionFilter, edutechonline.database.*, edutechonline.database.entity.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	try {
		int courseId=Integer.parseInt(request.getParameter("cid"));
		int userId=SessionFilter.getUserId(request);
		
		Course c=Courses.getCourse(courseId);
		boolean isOwner=c.getOwnerId()==userId;
		if (isOwner) {
			User u=Users.getUser(userId);
			request.setAttribute("user", u);
			request.setAttribute("course",c);
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		
		
	} catch (Exception e) {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
%>

<edutech:template js="manager/editCourse, manager/addTopic">
<input type="hidden" id="courseId" value="${course.getID()}"/>
<div>
<form enctype="multipart/form-data" role="form" method="post" action="/EduTechOnline/secure/addTopic" id="topicForm">
	<input type="hidden" name="course" value="${course.getID()}"/>
	<input type="hidden" name="type" value="0"/>
	<div class="hiddenQuestion">
						
							<edutech:question/>	
						
	</div>   
	<div class="hiddenAnswer">
						
							<edutech:answer/>	
						
	</div>   
	<div class="container">
		<div class="row"></div>
				<div class="col-lg-8">
			 	
			 			<div class="form-group">
			 				<label for="quiz" class="col-md-5">File</label>
				
							<input checked="checked" type="radio"  name="topicType" id="file" class="formcontrol" value="file">
						</div>	
						<div class="form-group">
						
							<label for="quiz" class="col-md-5">Quiz</label>
				
							<input type="radio"  name="topicType" id="quiz" class="formcontrol" value="quiz">
							</div>
						<div class="form-group">
							
							<label for="quiz" class="col-md-5">URL</label>
				
							<input type="radio" id="url" name="topicType" id="url" class="formcontrol" value="url">
						</div>
						<div class="form-group">
							<label for="name" class="col-md-5">Topic Name</label>
				
							<input type="text"  name="name" id="name" class="formcontrol" placeholder="Enter topic Name">
						</div>	
						<div class="form-group">
							<label for="desc" class="col-md-5">Description</label>
				
							<textarea name="desc" id="desc" class="formcontrol descField" placeholder="Enter Description"></textarea>
						</div>	
				
						<div class="form-group topicFile">
							<label for="typeFile" class="col-md-5">File</label>
				
							<input name="file" class="formcontrol" type="file" id="typeFile"/>
						</div>	
						
						<div class="form-group topicUrl">
							<label for="typeUrl" class="col-md-5">URL</label>
				
							<input name="url" class="formcontrol" type="text" id="typeUrl"/>
						</div>	
						
						
						
						<div class="topicQuiz">
						
							<edutech:question/>	
						
						</div>        
						<button type="button" id="addQuestion">Add New Question</button>
						        		
            			<input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-left">
             	</div>
            </div>
    </form>
  </div>  

</edutech:template>