$(document).ready(function() {
	// Set up form validation
	$("#courseForm").validate({
		rules: {
			name: {
				required: true
			},
			desc: {
				required: true
			},
			cost: {
				required: true,
				min: 0
			},
			category: {
				required: true
			},
			
			
		},
		messages: {
			name: {
				required: "enter a course name"
			},
			desc: {
				required: "enter a course description"
			},
			cost: {
				required: "enter a course cost",
				min: "cost needs to be 0 or greater"
			},
			category: {
				required: "enter a course category"
			},
		}
	});
	
	
	
});
	