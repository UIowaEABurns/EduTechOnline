<%@page contentType="text/html" pageEncoding="UTF-8" import="edutechonline.servlets.SessionFilter, edutechonline.database.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	try {
		int userId=SessionFilter.getUserId(request);
		boolean loggedIn=false;
		//if the ID was valid
		if (userId>=0) {
			loggedIn=true;
			
			request.setAttribute("user", Users.getUser(userId));
		}
		request.setAttribute("loggedIn", loggedIn);
	} catch (Exception e) {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "error loading page");
	}
	

%>
<edutech:template css="public/home" js="">
	
<title>Edutechonline</title>
	<div id="whatsNew" class="box" >
		<p class="caption" title="one more tooltip">What's new on EduTechOnline</p>
		<ul>
			<li>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
			Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris</li>
			<li>Duis aute irure dolor in reprehenderit in voluptate velit esse 
			cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat 
			cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."</li>
			<li>"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque 
			laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi
			 architecto beatae vitae dicta sunt explicabo</li>
			<li>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis 
			suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur</li>
			<li>Quis autem vel eum iure reprehenderit qui in ea voluptate
			 velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum 
			 fugiat quo voluptas nulla pariatur</li>
			<li>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
			 incididunt ut labore et dolore magna aliqua</li>
		
		
		</ul>
	
	</div>
<div id="loginDiv" class="box">

	<c:if test="${!loggedIn}">
	<form method="POST" action="j_security_check">
            <table id="loginTable">
                <thead>
                    <tr>
                        <th colspan="2">Login</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="j_username" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="j_password"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"><button type="submit">Login</button></td>
                    </tr>
                    <tr>
                        <td colspan="2"> <a href="/EduTechOnline/jsp/public/registration.jsp">Not Yet Registered? </a></td>
                    </tr>
                </tbody>
            </table>
           
        </form>
        </c:if>
        <c:if test="${loggedIn}">
        	<p>you logged in! yay!</p>
        </c:if>
    </div>
</edutech:template>