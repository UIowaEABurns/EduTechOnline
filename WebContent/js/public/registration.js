$(document).ready(function() {
	// Set up form validation
	$("#registerForm").validate({
		rules: {
			fname: {
				required: true,
				regex : getNameRegex()

			},
			lname: {
				required: true,
				regex : getNameRegex()
			},
			email: {
				required: true,
				regex : getEmailRegex()
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
				required: "enter a first name",
				regex: "names can contain only letters, hyphens, and spaces"
			},
			lname: {
				required: "enter a last name",
				regex: "names can contain only letters, hyphens, and spaces"

			},
			email: {
				required: "enter an email address",
				regex: "invalid email format"
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
	