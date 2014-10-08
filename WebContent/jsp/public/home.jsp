<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	int x=0;
	request.setAttribute("temp",x);

%>
<edutech:template css="public/home" js="">
	<form id="loginForm">
		<label title="this is some text">email:</label><input type="text" name="email"/>
		<label>password:</label><input type="text" name="password"/>
		<button>button</button>
	</form>	
</edutech:template>