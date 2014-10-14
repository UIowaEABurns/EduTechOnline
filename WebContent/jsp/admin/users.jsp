<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.*,edutechonline.servlets.SessionFilter, edutechonline.database.*, edutechonline.database.entity.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	try {
		List<User> users=Users.getAllUsers();
		
		request.setAttribute("users", users);
	} catch (Exception e) {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "error loading page");
	}
	

%>
<edutech:template title="home" css="" js="admin/users">
	
	<table id="userTable" class="dataTable">
		<thead>
			<tr>
				<th>Full Name</th>
				<th>Email</th>
				<th>Role</th>
				<th title="make this user a manager">Promote</th>
				<th title="permanently remove this user from the system">Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="u">
				<tr>
					<td>${u.getFullName()}</td>
					<td>${u.getEmail()}</td>
					<td>${u.getRole()}</td>
					<td><button value="${u.getID()}" class="promoteButton">Promote</button></td>
					<td><button value="${u.getID()}" class="deleteButton">delete</button></td>
				</tr>
			</c:forEach>
		
		</tbody>
	
	
	
	</table>
</edutech:template>