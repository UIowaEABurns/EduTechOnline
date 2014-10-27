//this file contains some very basic javascript that will be inserted on every page.
//that makes this a good place to define global functions or variables

$(document).ready(function() {
	//use qtip Tooltips everywhere
	$('[title]').qtip({
		position: {
        target: 'mouse' }
	});
	
	$(".dataTable").DataTable();
	
	//use jQuery styled buttons everywhere
	$(":button").button();
	
	// Add regular expression capabilities to the validator
	$.validator.addMethod(
			"regex", 
			function(value, element, regexp) {
				return this.optional(element) || regexp.test(value);
	});

});

//regular expressions
//public static String NAME_REGEX = "^[\\w\\s]+$";
//public static String EMAIL_REGEX = "^[\\w-%+\\.]+@[a-zA-Z0-9]+\\.[\\w]{2,4}$";

function getNameRegex() {
	return /^[a-z-\s]+$/i;
}

function getEmailRegex() {
	return /^[\w-%+\.]+@[a-zA-Z0-9]+\.[\w]{2,4}$/i;
}

/**
 * Logs the current user out
 */
function logout() {
	$.post(  
	    "/EduTechOnline/services/session/logout",  
	    function(returnData){  
       	        window.location.href = "/EduTechOnline/jsp/secure/index.jsp";
	     },  
	     "json"  
	)
}