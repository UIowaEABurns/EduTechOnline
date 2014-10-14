<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="title" %>
<%@attribute name="css" %>
<%@attribute name="js" %>

<head>
	<!-- This tag defines the 'head' tag for every page on the website, which allows us to include important css and js files -->
	<title>EduTechOnline</title>
	<meta charset="utf-8" />
	<link rel="stylesheet" href="/EduTechOnline/css/lib/jquery-ui.css" />
	<link rel="stylesheet" href="/EduTechOnline/css/lib/jquery-ui.theme.css" />
	<link rel="stylesheet" href="/EduTechOnline/css/lib/jquery.qtip.css"/>
		<link rel="stylesheet" href="/EduTechOnline/css/lib/dataTable.css"/>
	
	<link rel="stylesheet" href="/EduTechOnline/css/master.css" />
	
	<!-- Includes any other css files specified on the page -->
	<c:if test="${not empty css}">	
		<c:forEach var="cssFile" items="${fn:split(css, ',')}">
			<link rel="stylesheet" href="/EduTechOnline/css/${fn:trim(cssFile)}.css"/>
		</c:forEach>	
	</c:if>		
	<script src="/EduTechOnline/js/lib/jquery-1.11.1.min.js"></script>	
	
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
	