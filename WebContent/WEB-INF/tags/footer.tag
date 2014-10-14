
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<footer id="pageFooter">
	<!-- This defines a footer for the project -->
	<c:if test="${not empty user && user.role == 'admin'}">
		<ul id="adminLinks">
			<li><a href="/EduTechOnline/jsp/admin/users.jsp">users</a></li>
		
		</ul>
	</c:if>
</footer>

