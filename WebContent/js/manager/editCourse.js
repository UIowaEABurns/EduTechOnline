
var courseId;
var visible;
var deprecated;
$(document).ready(function() {
	courseId=$("#courseId").attr("value");
	visible=toBoolean($("#toggleVisible").attr("value"));
	deprecated=toBoolean($("#toggleDeprecation").attr("value"));

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
				"/EduTechOnline/services/course/open/" + courseId+"/"+!visible,
				function(returnCode) {
					visible=!visible;
					setVisibilityButtonText();
				},
				"json"
		);
	});
	
	$("#toggleDeprecation").click(function() {
		$.post(
				"/EduTechOnline/services/course/deprecate/" + courseId+"/"+!deprecated,
				function(returnCode) {
					deprecated=!deprecated;
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
		$("#toggleVisible span").text("Close Course");
	} else {
		$("#toggleVisible span").text("Release Course");
	}
	if (deprecated) {
		$("#toggleDeprecation span").text("Remove Deprecation");

	} else {
		$("#toggleDeprecation span").text("Deprecate Course");

	}
	
}