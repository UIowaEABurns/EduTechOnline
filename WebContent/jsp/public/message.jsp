<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	
	String message=request.getParameter("msg");
	
	
	request.setAttribute("msg", message);
%>

<edutech:template title="EduTechOnline Message" css="">	
	
	<p class="message">${msg}</p>
		
</edutech:template>