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

<edutech:template>
<div class="editAccount">
	<form method="POST" action="/EduTechOnline/secure/editAccount" id="editForm">
            <table>
                <thead>
                    <tr>
                        <th >Enter Information Here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>First Name</td>
                        <td><input id="fname" name="fname" type="text" value="${user.getFirstName()}"/></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input id="lname" name="lname" type="text" value="${user.getLastName()}"/></td>
                    </tr>
                </tbody>
            </table>
            <input type="submit" name="submit" id="submit" value="Save Changes" class="btn btn-info pull-left">
            
           </form>
</div>
</edutech:template>