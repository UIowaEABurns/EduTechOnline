<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="star" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String desc = "";
	
	switch(pageContext.getErrorData().getStatusCode()) {
		case 400:
			desc = "bad request";
			break;
		case 403:
			desc = "forbidden";
			break;
		case 404:
			desc = "not found";
			break;
		case 405:
			desc = "method not allowed";
			break;
		case 500:
			desc = "internal server error";
			break;
		default:
			break;
	}
	
	request.setAttribute("errorDesc", desc);
%>

<star:template title="EduTechOnline error" css="">	
	<h3>${errorDesc}</h3>	
	<p>Oops, it seems like there was some kind of error processing your request. Sorry about that. Some error details
	are shown below.</p>
	<p><c:out value="${requestScope['javax.servlet.error.message']}"/></p>
		
</star:template>