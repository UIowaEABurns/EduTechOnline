<%@tag description="Display for a quiz" import="edutechonline.database.entity.*"%>
<%@attribute name="quiz" required="true" description="The quiz object" type="edutechonline.database.entity.Quiz" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form role="form" method="post" action="/EduTechOnline/secure/doQuiz" id="quizForm">

<input type="hidden" name="quiz" value="${quiz.getID()}"/>
<c:forEach var="question" items="${quiz.getQuestions()}">
	<div class="question panel panel-info">
		
		<div class="panel-body">
				<p id="${question.getID()}" class="questionText">Question: ${question.getText()} </p>
			
				<c:forEach var="answer" items="${question.getAnswers()}">
					
					<div class="form-group">											
						<p class="answerText">Answer: ${answer.getText()}</p>
								
						<input type="radio" name="question:${question.getID()}" value="${answer.getID()}" name="answerRadio" class="formcontrol answerRadio">
													
					</div>
				</c:forEach>				
		</div>						
	</div>
</c:forEach>
<input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-left">

</form>