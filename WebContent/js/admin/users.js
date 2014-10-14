$(document).ready(function() {
	$(".promoteButton").click(function() {
		uId=$(this).attr("value");
		$.post(
				"/EduTechOnline/services/promote/user/" + uId,
				function(returnCode) {
					//s=parseReturnCode(returnCode);
					//if (s) {
					//	history.back(-1);
					//}
					location.reload();
				},
				"json"
		);
	});
	
	$(".deleteButton").click(function() {
		uId=$(this).attr("value");
		$.post(
				"/EduTechOnline/services/delete/user/" + uId,
				function(returnCode) {
					//s=parseReturnCode(returnCode);
					//if (s) {
					//	history.back(-1);
					//}
					location.reload();
				},
				"json"
		);
	});
});