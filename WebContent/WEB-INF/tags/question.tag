<%@tag description="A quiz question"%>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<div class="question panel panel-info">
	<div class="panel-heading">
		<h5 class="panel-title">Add Question</h5>
	</div>
	<div class="panel-body">
		<div class="form-group">
			<label for="name" class="col-md-5">Question Text</label>
							
			<input type="text"  name="question" class="formcontrol questionText" placeholder="Enter Question Text">
		</div>	
								
		<edutech:answer/>
							
		<button type="button" class="addAnswer">Add New Answer</button>
		<button type="button" class="deleteQuestion">Remove Question</button>
	</div>
						
</div>