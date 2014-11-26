<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>


<edutech:template title="Register" css="public/home" js="public/registration">

<legend>Please Complete The Fields Below</legend>
<form role="form" method="post" action="/EduTechOnline/public/registration" id="registerForm">
	
	<div class="container">
		<div class="row"></div>
				<div class="col-lg-8">
			 	
						<div class="form-group">
							<label for="fnamew" class="col-md-5">Enter First Name	</label>
				
							<input type="text"  name="fname" id="fnamew" class="formcontrol" placeholder="Enter First Name" required>
						</div>	
						<div class="form-group">
							<label for="lnamew" class="col-md-5">Enter Last Name</label>
				
							<input type="text"  name="lname" id="lnamew" class="formcontrol" placeholder="Enter Last Name" required>
						</div>
				
					
						<div class="form-group">
							<label for="emailw" class="col-md-5">Enter Email</label>
				
							<input type="email"  name="email" id="emailw" class="formcontrol" placeholder="Enter Email" required>
						</div>	
				
					
						<div class="form-group">
							<label for="confemail" class="col-md-5">Confirm Email </label>
				
							<input type="email"  name="cemail" id="confemail" class="formcontrol" placeholder="Confirm Email" required>
                		</div>
            
            
            			<div class="form-group">
							<label for="passw"class="col-md-5">Enter Password</label>
				
							<input type="password" name="pass" id="pass" class="formcontrol" placeholder="Enter password" required>
						</div>	
				
					
						<div class="form-group">
							<label for="confpwd"class="col-md-5">Confirm Password </label>
				
							<input type="password" name="passConfirm" id="confpwd" class="formcontrol" placeholder="Confirm Password" required>
            			</div>
            
            			<input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-left">
             	</div>
            </div>
    </form>
          
        
                
           
</edutech:template>