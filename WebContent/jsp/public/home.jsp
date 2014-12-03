<%@page contentType="text/html" pageEncoding="UTF-8" import="edutechonline.servlets.SessionFilter, edutechonline.database.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>
	
	
<edutech:template title="EduTechOnline" css="public/home" js="">
		
	<div class="row equal">
	  	
	
		<div id="whatsNew">
			<div class="panel panel-info">
	  			<div class="panel-heading">
	 		 		<h5 class="panel-title">What's New</h5>
	  			</div>
	  				<div class="panel-body">
			<ul>
				<li>New course on Linux- Introduction to Linux</li>
				<li>New courses on Verification - Formal methods in software engineering.</li>
				<li>Trouble in finding the formula in Microsoft excel- Excel in Microsoft excel</li>
				<li>Interested in photoediting !!- Introduction to adobe photoshop</li>
				<li>Website designing - Introduction to HTML and CSS</li>
				<li>New course on Animation</li>
			
			
			</ul>
					</div>
			</div>
		</div>

	<c:if test="${!loggedIn}">
	 <form method="POST" action="j_security_check" class="form-horizontal">	
	   	
	
	
			<div id="loginDiv">
			 
				<div class="panel panel-info">
	 				<div class="panel-heading">
	  				<h5 class="panel-title">Login</h5>
	  				</div>
	 					 		
										<div style="padding-top:10px" class="panel-body" >
		
	            						<div style="margin-bottom: 25px" class="input-group">
	                                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
	                                        <input id="login-username" type="text" class="form-control" name="j_username" value="" placeholder="username or email">                                        
	                                    </div>
	                                
	                            		<div style="margin-bottom: 25px" class="input-group">
	                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
	                                        <input id="login-password" type="password" class="form-control" name="j_password" value="" placeholder="password">
	                                    </div>
	                                    
	
	                                
	                            		<div class="input-group">
	                                      	<div class="checkbox">
	                                        	<label>
	                                          		<input id="login-remember" type="checkbox" name="remember" value="1"> Remember me
	                                        	</label>
	                                      	</div>
	                                    </div>
	
	
	                                	<div style="margin-top:10px" class="form-group">
	                                   
	                                    	<div class="col-sm-12 controls">
	                                      		<input type="submit" value="Login" class="btn btn-primary" id="loggedIn">
	                                      
	                                    	</div>
	                                	</div>
	
	
	                                	<div class="form-group">
	                                    	<div class="col-md-12 control">
	                                        	<div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
	                                            	<a href="/EduTechOnline/jsp/public/registration.jsp">
	                                            			Not Registered? 
	                                        		</a>
	                                        		<a href="/EduTechOnline/jsp/public/requestPassword.jsp">Forgot Password?</a>
	                                       		</div>
	                                    	</div>
	                                	</div>    
	                                 
							</div>
	     			</div>                     
	 			</div> 
	 		
	 	 
	 	</form>
	 </c:if> 
	 
	
	 		
	        <c:if test="${loggedIn}">
	        	 <form method="POST" action="j_security_check" class="form-horizontal">	
	        
		        <div id="loginDiv">
		        <div class="panel panel-info">
		 				<div class="panel-heading">
		  				<h5 class="panel-title">Login</h5>
		  		</div>
		  			
		        	<p>Hello, ${user.getFullName()}</p>
		        	<ul>
		        		<li><a id="viewAccount" href="/EduTechOnline/jsp/secure/accounts/details.jsp">view account details</a></li>
		        		<li><a href="#" onclick="javascript:logout();">logout</a></li>
		        	</ul>
		        </div>
		        </div>
		        </form>
	        </c:if>
	    
	  </div> 
	
	</edutech:template>