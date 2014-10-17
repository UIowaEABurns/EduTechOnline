<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%@attribute name="title" %>
<%@attribute name="css" %>
<%@attribute name="js" %>

<!DOCTYPE html>
<html lang="en">
	<edutech:head title="${title}" css="${css}" js="${js}"/>	
	<body>			
	
		<div id="wrapper">
			<edutech:header />
			<div id="content" class="round">
				
				<jsp:doBody/>
			</div>		
		<edutech:footer />
		</div>
	</body>
</html>