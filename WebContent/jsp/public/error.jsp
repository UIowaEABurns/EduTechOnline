<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	
%>

<edutech:template title="EduTechOnline error" css="">	
		
	<p>Oops, it seems like there was some kind of error processing your request. Sorry about that. Some error details
	are shown below.</p>
	<p><c:out value="${requestScope['javax.servlet.error.message']}"/></p>
		
</edutech:template>