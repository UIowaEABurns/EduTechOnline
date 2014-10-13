<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>

<%
	int x=0;
	request.setAttribute("temp",x);

%>
<edutech:template css="public/home" js="">
	
<title>Edutechonline</title>
<link rel="stylesheet" href="/EduTechOnline/WebContent/css/public/home.css" type="text/css"/>
<div class="company_name">EduTechOnline</div>
<div class="nav_top"><a href="/edutechonline/jss/public/registration.jsp" id="signup">Sign up</a> <a href="/edutechonline/jss/public/help.jsp" class="top_nav" id="helpedu">Enquiry</a>
<div class="login">
<form method="post" action="login.jsp">
            <center>
            <table border="1" width="30%" cellpadding="3">
                <thead>
                    <tr>
                        <th colspan="2">Login</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="uname" value="" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="pass" value="" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Login" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                    <tr>
                        <td colspan="2">Yet Not Registered!! <a href="registartion.jsp">Register Here</a></td>
                    </tr>
                </tbody>
            </table>
            </center>
        </form>
        </div>
        
	
	
	
</edutech:template>