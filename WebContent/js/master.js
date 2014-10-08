//this file contains some very basic javascript that will be inserted on every page.
//that makes this a good place to define global functions or variables

$(document).ready(function() {
	//use qtip Tooltips everywhere
	$('[title]').qtip({});
	
	//use jQuery styled buttons everywhere
	$("button").button();
	$("input [type='button']").button();
	
});