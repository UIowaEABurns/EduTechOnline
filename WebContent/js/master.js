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

});

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