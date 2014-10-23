<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>


<edutech:template title="Add Course" css="public/home" js="manager/addCourse">

<legend>Please Complete The Fields Below</legend>
<form role="form" method="post" action="/EduTechOnline/secure/addCourse" id="courseForm">
	
	<div class="container">
		<div class="row"></div>
				<div class="col-lg-8">
			 	
						<div class="form-group">
							<label for="name" class="col-md-5">Course Name</label>
				
							<input type="text"  name="name" id="name" class="formcontrol" placeholder="Enter Course Name">
						</div>	
						<div class="form-group">
							<label for="category" class="col-md-5">Category</label>
				
							<input type="text"  name="category" id="lnamew" class="formcontrol" placeholder="Enter Category">
						</div>
				
					
						<div class="form-group">
							<label for="description" class="col-md-5">Description</label>
				
							<textarea name="desc" id="desc" class="formcontrol" placeholder="Enter Description"></textarea>
						</div>	
				
					
						<div class="form-group">
							<label title="The amount of money, in dollars and cents, that it will cost to enroll in this course" for="confemail" class="col-md-5">Cost</label>
				
							<input type="text"  name="cost" id="cost" class="formcontrol" placeholder="Enter Cost">
                		</div>
                		
            			<input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-left">
             	</div>
            </div>
    </form>
          
        
                
           
</edutech:template>