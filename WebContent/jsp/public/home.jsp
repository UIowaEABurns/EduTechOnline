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
			<li>"New course on Linux- Introduction to Linux</li>
			<li>New courses on Verification - Formal methods in software engineering."</li>
			<li>"Trouble in finding the formula in Microsoft excel- Excel in Microsoft excel</li>
			<li>Interested in photoediting !!- Introduction to adobe photoshop</li>
			<li>Website designing - Introduction to HTML and CSS</li>
			<li>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
			 incididunt ut labore et dolore magna aliqua</li>
		
		
		</ul>
	
	</div>
<div id="loginDiv" class="box">

	<c:if test="${!loggedIn}">
	<form method="POST" action="j_security_check">
			<p class="caption" title="one more tooltip">Login</p>
	
            <table id="loginTable">
                
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