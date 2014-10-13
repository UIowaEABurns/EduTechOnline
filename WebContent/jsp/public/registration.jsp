<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>


<edutech:template css="public/home" js="">
<div class="regis">
<form method="post" action="/EduTechOnline/public/registration">
            <table>
                <thead>
                    <tr>
                        <th >Enter Information Here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="fname"/></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="lname"  /></td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td><input type="text" name="email"  /></td>
                    </tr>
                   
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="pass" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="passConfirm" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Submit" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                    <tr>
                        <td>Already registered? <a href="/EduTechOnline/jsp/public/home.jsp">Login Here</a></td>
                    </tr>
                </tbody>
            </table>
        </form>
        </div>
</edutech:template>