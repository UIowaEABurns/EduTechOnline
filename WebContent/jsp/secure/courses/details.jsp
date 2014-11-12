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
			//if we can see the course
			User u=Users.getUser(userId);
			c.setTopics(Courses.getContentTopicsForCourse(c.getID()));
			request.setAttribute("user", u);
			request.setAttribute("course",c);
			
			request.setAttribute("isOwner",isOwner);
			request.setAttribute("topicsVisible", isOwner || Courses.isEnrolled(userId,courseId));
			request.setAttribute("completed", Courses.hasUserCompletedCourse(userId, courseId));
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		
		
	} catch (Exception e) {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
%>

<edutech:template js="manager/editCourse, secure/courses/details">
<input type="hidden" id="courseId" value="${course.getID()}"/>
<div >
	<c:if test="${course.isDeprecated()}">
		<div class="banner">
			<p>This course is deprecated and may be removed at any time. Please take that under consideration
			when deciding whether to enroll</p>
		</div>
	
	</c:if>
	<c:if test="${topicsVisible}">
		<div id="courseContentTopics">
			<div class="panel panel-info">
	  			<div class="panel-heading">
	 		 		<h5 class="panel-title">Content Topics</h5>
	  			</div>
	  				<div class="panel-body">
						<ul class="contentTopicList">
							<c:forEach var="topic" items="${course.getTopics()}">
								<ul><a class="contentTopicLink" href="/EduTechOnline/jsp/secure/courses/topic.jsp?tid=${topic.getID()}">${topic.getName()}</a></ul>
							</c:forEach>  
						</ul>
						
					</div>
			</div>
		</div>
	</c:if>
	<c:if test="${!topicsVisible}">
		<div id="courseContentTopics">
			<div class="panel panel-info">
	  			<div class="panel-heading">
	 		 		<h5 class="panel-title">Enroll</h5>
	  			</div>
	  				<div class="panel-body">
						<p>Click below to enroll in this course! This course costs $ ${course.getCost()}.</p>
						<button id="enrollButton">Enroll</button>
					</div>
			</div>
		</div>
	
	</c:if>
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
	
	<c:if test="${completed}">
		<fieldset>
			<legend>Actions</legend>
			<form role="form" method="post" action="/EduTechOnline/secure/getCert" id="certForm">
				<input type="hidden" name="course" value="${course.getID()}"/>
				<button type="submit" id="downloadCert">Download Certificate</button>
			</form>
		</fieldset>
	</c:if>
	<c:if test="${isOwner}">
		<fieldset>
			<legend>Actions</legend>
			<button id="deleteButton">Delete Course</button>
			<button id="toggleVisible" value="${course.isOpen()}"></button>
			<button id="toggleDeprecation" value="${course.isDeprecated()}"></button>
			<a id="addContentLink" href="/EduTechOnline/jsp/manager/addTopic.jsp?cid=${course.getID()}"><button id="addContent">Add Content Topic</button></a>
		</fieldset>
	</c:if>
</div>

</edutech:template>