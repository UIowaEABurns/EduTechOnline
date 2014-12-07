<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.*,edutechonline.util.*, edutechonline.security.*,edutechonline.database.entity.ContentTopic.ContentType, edutechonline.servlets.SessionFilter, edutechonline.database.*, edutechonline.database.entity.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	try {
		int topicId=Integer.parseInt(request.getParameter("tid"));
		int userId=SessionFilter.getUserId(request);
		ContentTopic topic=Courses.getContentTopic(topicId);
		Course c=Courses.getCourse(topic.getCourseId());
		ValidatorStatusCode status=CourseSecurity.canUserSeeCourseTopics(c.getID(), userId);
		boolean isOwner=c.getOwnerId()==userId;
		if (status.isSuccess()) {
			User u=Users.getUser(userId);
			c.setTopics(Courses.getContentTopicsForCourse(c.getID()));
			request.setAttribute("user", u);
			request.setAttribute("course",c);
			request.setAttribute("topic",topic);
			request.setAttribute("path",Courses.getTopicURL(topicId));
			request.setAttribute("isOwner",isOwner);
			
			if (topic.getType()==ContentType.PDF) {
				request.setAttribute("type", 1);
			} else if (topic.getType()==ContentType.TEXT) {
				request.setAttribute("type",2);
			} else if (topic.getType()==ContentType.QUIZ) {
				Quiz quiz=Courses.getQuiz(topicId);
				request.setAttribute("type",3);
				request.setAttribute("quiz",quiz);
				boolean takenQuiz=false;
				List<Integer> answers = Courses.getStudentAnswersForQuiz(userId, topicId);
			
				for (Question q : quiz.getQuestions()) {
					for (Answer a : q.getAnswers()) {
						if (answers.contains(a.getID())) {
							a.setBeingUsed(true);
						} else {
							a.setBeingUsed(false);
						}
					}
				}
				if (Courses.hasUserTakenQuiz(topic.getID(), userId)) {
					takenQuiz=true;
					request.setAttribute("grade",Util.pointsToGrade(Courses.getQuizScore(topic.getID(),userId)));
				}
				request.setAttribute("taken",takenQuiz);
			} else {
				request.setAttribute("type",4);
			}
			
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,status.getMessage());
		}
		
		
		
	} catch (Exception e) {
		e.printStackTrace();
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
%>

<edutech:template css="secure/courses/topic" js="secure/courses/topic">
<input type="hidden" id="courseId" value="${course.getID()}"/>
<input type="hidden" id="topicId" value="${topic.getID()}"/>

<div>
	<div id="courseContentTopics">
			<div class="panel panel-info">
	  			<div class="panel-heading">
	 		 		<h5 class="panel-title">Content Topics</h5>
	  			</div>
	  				<div class="panel-body">
						<ul class="contentTopicList">
							<ul><a class="contentTopicLink" href="/EduTechOnline/jsp/secure/courses/details.jsp?cid=${course.getID()}">course home</a></ul>
						
							<c:forEach var="topic" items="${course.getTopics()}">
								<ul><a class="contentTopicLink" href="/EduTechOnline/jsp/secure/courses/topic.jsp?tid=${topic.getID()}">${topic.getName()}</a></ul>
							</c:forEach>  
						</ul>
						
					</div>
			</div>
		</div>
	
	<div id="topicDetails">
			<div class="panel panel-info">
	  			<div class="panel-heading">
	 		 		<h5 class="panel-title">${topic.getName()}</h5>
	  			</div>
	  				<div class="panel-body">
	  				
	  					<div class="descriptionDiv">
	  						<p>${topic.getDescription()}</p>
	  					</div>
	  					<div class="dataPane">
	  						<c:if test="${type==1}">
	  						<object id="contentViewer" class="pdfViewer" data="${path}" type="application/pdf">
	 
	  							<p>No PDF reader available. Please download one from Adobe.</p>
	  
							</object>
	  						</c:if>
							
							<c:if test="${type==2}">
	  						<object id="contentViewer" class="textViewer" data="${path}" type="text/plain">
	 
	  
							</object>
	  						</c:if>
	  						<c:if test="${type==3}">
	  							<c:if test="${taken}">
	  								<p class="banner">You have already taken this quiz. You received a grade of ${grade}. 
	  								Your answers are shown below, along with the correct answers.</p>
	  								<edutech:finishedQuiz quiz="${quiz}"/>
	  								
	  							</c:if>
	  						
	  							<c:if test="${!taken}">
	  								<edutech:quiz quiz="${quiz}"/>
	  							</c:if>
	  						</c:if>
	  						<c:if test="${type==4}">
	  							 <iframe id="contentViewer" src="${topic.getUrl()}"></iframe> 
	  						</c:if>
						</div>
						
					</div>
			</div>
		</div>
		
</div>
<c:if test="${isOwner}">
			<fieldset class="actionField">
				<legend>Actions</legend>
				<button id="deleteButton">Delete Topic</button>
				
			</fieldset>
</c:if>
</edutech:template>