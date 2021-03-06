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

<edutech:template css="public/home">
<div class="regis">
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
	<fieldset class="actions">
	
		<a href="/EduTechOnline/jsp/secure/accounts/edit.jsp"><button id="editAccount" >Edit Account</button></a>
		<a href="/EduTechOnline/jsp/secure/accounts/editPassword.jsp"><button id="editAccount" >Edit Password</button></a>
	
	</fieldset>
</div>
</edutech:template>