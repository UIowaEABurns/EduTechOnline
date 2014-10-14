<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="edutech" tagdir="/WEB-INF/tags" %>


<edutech:template title="reset password" css="public/home" js="">
<div class="regis">
<form method="post" action="/EduTechOnline/public/resetpass">
            <table>
                <thead>
                    <tr>
                        <th >Enter Information Here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Temporary ID</td>
                        <td><input type="text" name="id"/></td>
                    </tr>
                    
                    <tr>
                        <td>Email</td>
                        <td><input type="text" name="email"  /></td>
                    </tr>
                   
                    <tr>
                        <td>New Password</td>
                        <td><input type="password" name="pass" /></td>
                    </tr>
                    <tr>
                        <td>Confirm Password</td>
                        <td><input type="password" name="passConfirm" /></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="Submit" /></td>
                    </tr>
                </tbody>
            </table>
        </form>
        </div>
</edutech:template>