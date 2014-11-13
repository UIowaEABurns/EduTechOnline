<%@tag description="Display for a quiz" import="edutechonline.database.entity.*"%>
<%@attribute name="quiz" required="true" description="The quiz object" type="edutechonline.database.entity.Quiz" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="question" items="${quiz.getQuestions()}">
	<div class="question panel panel-info">
		
		<div class="panel-body">
				<p id="${question.getID()}" class="questionText">Question: ${question.getText()} </p>
			
				<c:forEach var="answer" items="${question.getAnswers()}">
					
					<div class="form-group">											
						<p class="answerText">Answer: ${answer.getText()} 
						<c:if test="${answer.isCorrect()}"><span class="answerTag">Correct</span></c:if> 
						<c:if test="${answer.isCorrect() && answer.isBeingUsed()}"><span class="answerTag"> / </span></c:if> 
						
						<c:if test="${answer.isBeingUsed()}"><span class="answerTag">Your Answer</span></c:if> 
						</p>
								
													
					</div>
				</c:forEach>				
		</div>						
	</div>
</c:forEach>
