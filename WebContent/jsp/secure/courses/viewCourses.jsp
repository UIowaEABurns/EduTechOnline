<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.*,edutechonline.servlets.SessionFilter, edutechonline.database.*, edutechonline.database.entity.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	try {
		int userId =SessionFilter.getUserId(request);
		List<Course> courses=null;
		String type=request.getParameter("type");
		if (type.equals("all")) {
			courses=Courses.getAllOpenCourses();

		} else if (type.equals("enrolled")) {
			courses=Users.getCoursesByUser(userId);
		}
				
		
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
				<th>Category</th>
				<th title="has the course been marked as deprecated by the manager?">Deprecated</th>
				<th title="Go to the home page for this course">View</th>
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
					<td><a href="/EduTechOnline/jsp/secure/courses/details.jsp?cid=${c.getID()}"><button value="${c.getID()}" class="editButton">View</button></a></td>
					
				</tr>
			</c:forEach>
		
		</tbody>
	
	
	
	</table>
</edutech:template>