var courseId;
$(document).ready(function() {
	courseId=$("#courseId").attr("value");
	$(".topicQuiz").hide();
	$(".hiddenQuestion").hide();
	$(".hiddenAnswer").hide();
	attachClicks();

	$("#quiz").click(function() {
		$(".topicFile").hide();
		$(".topicQuiz").show();
	});
	$("#file").click(function() {
		$(".topicFile").show();
		$(".topicQuiz").hide();
	});
	
	$("#addQuestion").click(function(e) {
		e.preventDefault();
		$(".topicQuiz").append($(".hiddenQuestion").html());
		attachClicks();
		setupRadios();
	});
	
	$("#submit").click(function(e) {
		if ($("#quiz").is(":checked")) {
			xml=convertToXML();
			$.post(
					"/EduTechOnline/services/add/quiz",
					{xml: xml},
					function(returnCode) {
						location.reload();
					},
					"json"
			);
			return false;
		}
		return true;
	});
	
	
});

function attachClicks() {
	$(".deleteQuestion").unbind();
	$(".deleteQuestion").click(function(e) {
		e.preventDefault();
		$(this).parent().parent().remove();
	});
	$(".addAnswer").unbind();
	$(".addAnswer").click(function(e) {
		e.preventDefault();
		$(this).siblings(".answer").last().after($(".hiddenAnswer").html());
		setupRadios();
	});
	
}






function setupRadios() {
	x=1;
	$(".question").each(function() {
		x=x+1;
		$(this).find(".answerRadio").each(function() {
			$(this).attr("name",x);
		});
	});
}



function convertToXML() {
	name=$("#name").val();
	desc=$("#desc").val();
	xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

	xml=xml+"\n<Quiz name=\""+name+"\" description=\""+desc+"\" course=\""+courseId+"\">\n"
	$(".topicQuiz").find(".question").each(function() {
		qText=$(this).find(".questionText").val();
		xml=xml+"<Question text=\""+qText+"\">\n";
		$(this).find(".answer").each(function(){
			correct=$(this).find(".answerRadio").is(":checked");
			xml=xml+"<Answer correct=\""+correct+"\">";
			xml=xml+$(this).find(".answerText").val();
			xml=xml+"</Answer>\n";
			
		});
		xml=xml+"</Question>\n";
	});
	
	xml=xml+"</Quiz>"
	return xml;
}
