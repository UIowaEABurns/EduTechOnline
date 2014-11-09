$(document).ready(function() {
	courseId=$("#courseId").attr("value");
	topicId=$("#topicId").attr("value");

	$("#deleteButton").click(function() {
		
		$.post(
				"/EduTechOnline/services/delete/topic/" + topicId,
				function(returnCode) {
					window.location.href = "/EduTechOnline/jsp/secure/courses/details.jsp?cid="+courseId;
				},
				"json"
		);
		
	});
});