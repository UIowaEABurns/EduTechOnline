<%@page contentType="text/html" pageEncoding="UTF-8" import="edutechonline.servlets.SessionFilter, edutechonline.database.*, edutechonline.database.entity.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	try {
		int userId=SessionFilter.getUserId(request);
		User u=Users.getUser(userId);
		request.setAttribute("user", u);
		
	} catch (Exception e) {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}




%>

<edutech:template css="public/home" js="public/registration">
<div class="regis">
<form method="post" action="/EduTechOnline/public/registration" id="registerForm">
            <table>
                <thead>
                    <tr>
                        <th >Enter Information Here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>First Name</td>
                        <td>${user.getFirstName()}</td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td>${user.getLastName()}</td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td>${user.getEmail()}</td>
                    </tr>
                    <tr>
                    	<td>Role</td>
                    	<td>${user.getRole()}</td>
                    </tr>
                </tbody>
            </table>
        </form>
        </div>
</edutech:template>