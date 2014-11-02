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
				regex: getMoneyRegex()
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
				regex: "cost needs to be 0 or greater and must be in dollars and cents"
			},
			category: {
				required: "enter a course category"
			},
		}
	});
	
	
	
});
	