
var courseId;
var visible;
$(document).ready(function() {
	courseId=$("#courseId").attr("value");
	visible=toBoolean($("#toggleVisible").attr("value"));
	$("#deleteButton").click(function() {
		
		$.post(
				"/EduTechOnline/services/delete/course/" + courseId,
				function(returnCode) {
					window.location.href = "/EduTechOnline/jsp/manager/viewCourses.jsp";
				},
				"json"
		);
		
	})
	
	$("#toggleVisible").click(function() {
		$.post(
				"/EduTechOnline/services/course/" + courseId+"/"+!visible,
				function(returnCode) {
					visible=!visible;
					setVisibilityButtonText();
				},
				"json"
		);
	});
	setVisibilityButtonText();
});

//sets the text of the button based on the current value of 'visible'
function setVisibilityButtonText() {
	if (visible) {
		$("#toggleVisible span").text("Deprecate Course");
	} else {
		$("#toggleVisible span").text("Open Course");
	}
	
}