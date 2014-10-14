<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>


<edutech:template css="public/home" js="">
<div class="regis">
<form method="post" action="/EduTechOnline/public/requestresetpass">
            <table>
                <thead>
                    <tr>
                        <th>Reset Password</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Email</td>
                        <td><input type="text" name="email"  /></td>
                    </tr>
                   <tr>
                        <td colspan="2"><input type="submit" value="Submit" /></td>
                    </tr>
                </tbody>
            </table>
        </form>
        </div>
</edutech:template>