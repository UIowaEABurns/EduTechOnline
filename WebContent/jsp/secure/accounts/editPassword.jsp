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
<div class="editAccount">
	<form method="POST" action="/EduTechOnline/secure/editPassword" id="editForm">
            <table>
                <thead>
                    <tr>
                        <th >Enter Information Here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>New Password</td>
                        <td><input id="pass" name="pass" type="password"/></td>
                    </tr>
                    <tr>
                        <td>Confirm Password</td>
                        <td><input id="cpass" name="cpass" type="password"/></td>
                    </tr>
                </tbody>
            </table>
            <input type="submit" name="submit" id="submit" value="Save Changes" class="btn btn-info pull-left">
            
           </form>
</div>
</edutech:template>