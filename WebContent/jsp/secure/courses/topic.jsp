<%@page contentType="text/html" pageEncoding="UTF-8" import="edutechonline.servlets.SessionFilter, edutechonline.database.*, edutechonline.database.entity.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	try {
		int topicId=Integer.parseInt(request.getParameter("tid"));
		int userId=SessionFilter.getUserId(request);
		ContentTopic topic=Courses.getContentTopic(topicId);
		Course c=Courses.getCourse(topic.getCourseId());
		boolean isOwner=c.getOwnerId()==userId;
		if (c.isOpen() || isOwner) {
			User u=Users.getUser(userId);
			c.setTopics(Courses.getContentTopicsForCourse(c.getID()));
			request.setAttribute("user", u);
			request.setAttribute("course",c);
			request.setAttribute("path",Courses.getTopicURL(topicId));
			request.setAttribute("isOwner",isOwner);
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		
		
	} catch (Exception e) {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
%>

<edutech:template css="secure/courses/topic">
<input type="hidden" id="courseId" value="${course.getID()}"/>
<div >
	<div id="courseContentTopics">
			<div class="panel panel-info">
	  			<div class="panel-heading">
	 		 		<h5 class="panel-title">Content Topics</h5>
	  			</div>
	  				<div class="panel-body">
						<ul class="contentTopicList">
							<c:forEach var="topic" items="${course.getTopics()}">
								<ul><a href="/EduTechOnline/jsp/secure/courses/topic.jsp?tid=${topic.getID()}">${topic.getName()}</a></ul>
							</c:forEach>  
						</ul>
						
					</div>
			</div>
		</div>
	
	<div id="topicDetails">
			<div class="panel panel-info">
	  			<div class="panel-heading">
	 		 		<h5 class="panel-title">${course.getName()} : ${course.getCategory()}</h5>
	  			</div>
	  				<div class="panel-body">
						<object id="pdfViewer" data="${path}" type="application/pdf">
 
  					<p>No reader! sorry!</p>
  
	</object>
						
					</div>
			</div>
		</div>

	
</div>

</edutech:template>