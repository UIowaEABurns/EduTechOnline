<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.*,edutechonline.servlets.SessionFilter, edutechonline.database.*, edutechonline.database.entity.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	try {
		List<Course> courses=Courses.getAllOpenCourses();
		
		request.setAttribute("courses", courses);
	} catch (Exception e) {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "error loading page");
	}
	

%>
<edutech:template title="home" css="" js="manager/viewCourses">
	
	<table id="userTable" class="dataTable">
		<thead>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th title="cost, in dollars and cents, of enrolling in this course">Cost</th>
				<th title="has the course been marked as deprecated by the manager?">Category</th>
				<th>Deprecated</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${courses}" var="c">
				<tr>
					<td>${c.getName()}</td>
					<td>${c.getDescription()}</td>
					<td>$ ${c.getCost()}</td>
					<td>${c.getCategory()}</td>
					<td>${c.isDeprecatedDisplay()}</td>
				</tr>
			</c:forEach>
		
		</tbody>
	
	
	
	</table>
</edutech:template>