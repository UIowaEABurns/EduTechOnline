<%@tag import="edutechonline.servlets.SessionFilter, edutechonline.database.*"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="title" %>
<%@attribute name="css" %>
<%@attribute name="js" %>

<%
		try {
			int userId=SessionFilter.getUserId(request);
			boolean loggedIn=false;
			//if the ID was valid
			if (userId>=0) {
				loggedIn=true;
				
				request.setAttribute("user", Users.getUser(userId));
			}
			request.setAttribute("loggedIn", loggedIn);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "error loading page");
		}
		
	
	%>

<head>
	<!-- This tag defines the 'head' tag for every page on the website, which allows us to include important css and js files -->
	<title>EduTechOnline</title>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/EduTechOnline/css/lib/jquery-ui.css" />
	<link rel="stylesheet" href="/EduTechOnline/css/lib/jquery-ui.theme.css" />
	<link rel="stylesheet" href="/EduTechOnline/css/lib/jquery.qtip.css"/>
		<link rel="stylesheet" href="/EduTechOnline/css/lib/dataTable.css"/>
	
	<link rel="stylesheet" href="/EduTechOnline/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/EduTechOnline/css/master.css" />
	
	<link rel="stylesheet" href="/EduTechOnline/css/custom.css" />
	<link rel="stylesheet" href="/EduTechOnline/css/font-awesome.min.css">
	
	<!-- Includes any other css files specified on the page -->
	<c:if test="${not empty css}">	
		<c:forEach var="cssFile" items="${fn:split(css, ',')}">
			<link rel="stylesheet" href="/EduTechOnline/css/${fn:trim(cssFile)}.css"/>
		</c:forEach>	
	</c:if>		
	<script src="/EduTechOnline/js/jquery-1.11.1.min.js"></script>
	
	<script src="/EduTechOnline/js/lib/jquery-ui.min.js"></script>
	<script src="/EduTechOnline/js/lib/jquery.cookie.js"></script>
		<script src="/EduTechOnline/js/lib/jquery.qtip.min.js"></script>
		<script src="/EduTechOnline/js/lib/jquery.validate.min.js"></script>
		<script src="/EduTechOnline/js/lib/jquery.dataTables.min.js"></script>
	
		    <script src="/EduTechOnline/js/master.js"></script>
	
	<!-- Includes any other javascript files that were specified on the individual page -->
	<c:if test="${not empty js}">	
		<c:forEach var="jsFile" items="${fn:split(js, ',')}">
			<script type="text/javascript" src="/EduTechOnline/js/${fn:trim(jsFile)}.js"></script>
		</c:forEach>	
	</c:if>
						
</head>
	