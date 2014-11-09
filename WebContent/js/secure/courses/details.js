$(document).ready(function() {
	courseId=$("#courseId").attr("value");

	
	$("#enrollButton").click(function() {
		
		$.post(
				"/EduTechOnline/services/enroll/course/" + courseId,
				function(returnCode) {
					location.reload();
				},
				"json"
		);
		
	});
});