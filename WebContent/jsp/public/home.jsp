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
<link rel="stylesheet" href="/EduTechOnline/WebContent/css/public/home.css" type="text/css"/>
<div class="company_name">EduTechOnline</div>
<div class="login">
	<c:if test="${!loggedIn}">
	<form method="POST" action="j_security_check">
            <table border="1">
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
                        <td colspan="2"> <a href="registartion.jsp">Yet Not Registered? </a></td>
                    </tr>
                </tbody>
            </table>
           
        </form>
        </c:if>
        <c:if test="${loggedIn }">
        	<p>you logged in! yay!</p>
        </c:if>
        </div>
        
	
	
	
</edutech:template>