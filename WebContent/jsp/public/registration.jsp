<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>


<edutech:template css="public/home" js="public/registration">
	<div class="container style="margin: 10px;">
		<div class="row"><h3>Please Enter The Below Fields</h3>
			<div class="col-lg-6">
			 <form role="form">
				<div class="formgroup">
					<label for="fname" class="col-md-5">Enter First Name	</label>
				<div class="col-md-30">
					<input type="text" name="fname" id="inputfirstname" class="formcontrol" placeholder="Enter First Name" required>
					
				</div>						
				</div>
				
				<div class="formgroup">
					<label for="lname" class="col-md-5">Enter Last Name</label>
				<div class="col-md-30">
					<input type="text" name="lname" id="inputlastname" class="formcontrol" placeholder="Enter Last Name" required>
					
				</div>
				</div>
					
				<div class="formgroup">
					<label for="Email" class="col-md-5">Enter Email</label>
				<div class="col-md-30">
					<input type="email" name="email" id="inputfirstemail" class="formcontrol" placeholder="Enter Email" required>
					
				</div>
					
				<div class="formgroup">
					<label for="Confemail" class="col-md-5">Confirm Email </label>
				<div class="col-md-30">
					<input type="email" name="cemail" id="confemail" class="formcontrol" placeholder="Confirm Email" required>
            
            	</div>
            
            	<div class="formgroup">
					<label for="Password"class="col-md-5">Enter Password</label>
				<div class="col-md-30">
					<input type="password" name="pass" id="inputfirstpass" class="formcontrol" placeholder="Enter password" required>
					
				</div>
					
				<div class="formgroup">
					<label for="Confpwd"class="col-md-5">Confirm Password </label>
				<div class="col-md-30">
					<input type="password" name="password" id="inputsecondpass" class="formcontrol" placeholder="Confirm Password" required>
            	</div>
            
            <input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-right">
            
          </div>
          </div>
          </div> 
           
           </div>
           </div>
           </div>
           
           
</edutech:template>