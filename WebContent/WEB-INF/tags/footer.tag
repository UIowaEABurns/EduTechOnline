	<!-- This defines a footer for the project -->

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<footer id="pageFooter">
	<c:if test="${empty user}">
	
		<nav class="navbar navbar-default" role="navigation">
	
			<div class="collapse navbar-collapse" id="navbar-collapse-1">
      		<div id="headerli">
     				<ul class="navbar-nav nav nav-pills">
                      			   				    
      					

        			
        			</ul>
        	</div>		
     		 
      
      	</div>
      	</nav>
		
	</c:if>
	<c:if test="${not empty user && user.role == 'admin'}">
		<nav class="navbar navbar-default" role="navigation">
	
			<div class="collapse navbar-collapse" id="navbar-collapse-1">
      		<div id="headerli">
     				<ul class="navbar-nav nav nav-pills">
                      			   				    
      			
      					<li><a href="/EduTechOnline/jsp/admin/users.jsp">Manage Users</a></li>

        			
        			</ul>
        	</div>		
     		 
      
      	</div>
      	</nav>
	</c:if>
	<c:if test="${not empty user && user.role == 'manager'}">
		<nav class="navbar navbar-default" role="navigation">
			<div class="collapse navbar-collapse" id="navbar-collapse-1">
      		<div id="headerli">
     				<ul class="navbar-nav nav nav-pills">
      					<li><a id="addCourseFooter" href="/EduTechOnline/jsp/manager/addCourse.jsp">Add Course</a></li>
      					<li><a id="managecourses" href="/EduTechOnline/jsp/manager/viewCourses.jsp">Manage Courses</a></li>
        			</ul>
        	</div>		
      	</div>
      	</nav>
	</c:if>
</footer>

