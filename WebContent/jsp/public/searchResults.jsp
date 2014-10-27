<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	
	String message=request.getParameter("msg");
	
	
	request.setAttribute("msg", message);
%>

<edutech:template title="EduTechOnline Message" css="">	
	<div id="searchResults">
<script>
  (function() {
    var cx = '013636592991520040445:mcodauuhghu';
    var gcse = document.createElement('script');
    gcse.type = 'text/javascript';
    gcse.async = true;
    gcse.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') +
        '//www.google.com/cse/cse.js?cx=' + cx;
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(gcse, s);
  })();
</script>
<gcse:searchresults-only></gcse:searchresults-only>
	
	
	</div>
		
</edutech:template>