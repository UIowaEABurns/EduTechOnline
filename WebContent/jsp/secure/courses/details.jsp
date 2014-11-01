<%@page contentType="text/html" pageEncoding="UTF-8" import="edutechonline.servlets.SessionFilter, edutechonline.database.*, edutechonline.database.entity.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	try {
		int courseId=Integer.parseInt(request.getParameter("cid"));
		int userId=SessionFilter.getUserId(request);
		
		Course c=Courses.getCourse(courseId);
		boolean isOwner=c.getOwnerId()==userId;
		if (c.isOpen() || isOwner) {
			User u=Users.getUser(userId);
			c.setTopics(Courses.getContentTopicsForCourse(c.getID()));
			request.setAttribute("user", u);
			request.setAttribute("course",c);
			request.setAttribute("isOwner",isOwner);
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		
		
	} catch (Exception e) {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
%>

<edutech:template js="manager/editCourse">
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

	<div id="courseDetails">
			<div class="panel panel-info">
	  			<div class="panel-heading">
	 		 		<h5 class="panel-title">${course.getName()} : ${course.getCategory()}</h5>
	  			</div>
	  				<div class="panel-body">
						<p id="courseDescription">${course.getDescription()}</p>
						
					</div>
			</div>
		</div>

	<c:if test="${isOwner}">
		<fieldset>
			<legend>Actions</legend>
			<button id="deleteButton">Delete Course</button>
			<button id="toggleVisible" value="${course.isOpen()}"></button>
			<button id="toggleDeprecation" value="${course.isDeprecated()}"></button>
			<a href="/EduTechOnline/jsp/manager/addTopic.jsp?cid=${course.getID()}"><button id="addContent">Add Content Topic</button></a>
		</fieldset>
	</c:if>
</div>

</edutech:template>