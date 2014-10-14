$(document).ready(function() {
	// Set up form validation
	$("#registerForm").validate({
		rules: {
			fname: {
				required: true
			},
			lname: {
				required: true
			},
			email: {
				required: true
			},
			pass: {
				required: true
			},
			passConfirm: {
				required: true,
				equalTo: '#pass'
			}
			
		},
		messages: {
			fname: {
				required: "enter a first name"
			},
			lname: {
				required: "enter a last name"
			},
			email: {
				required: "enter an email address"
			},
			pass: {
				required: "enter a password"
			},
			passConfirm: {
				required: "confirm your password",
				equalTo: 'passwords do not match'
			}
		}
	});
	
	
	
});
	