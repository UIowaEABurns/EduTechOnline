package edutechonline.test.suites.security;

import edutechonline.database.Users;
import edutechonline.database.entity.User;
import edutechonline.security.UserSecurity;
import edutechonline.test.ResourceLoader;
import edutechonline.test.Test;
import edutechonline.test.TestSet;

public class UserSecurityTests extends TestSet {

	User admin=null;
	User temp=null;
	@Test
	private void temp() {
		UserSecurity.canUserDeleteUser(temp.getID(), admin.getID());
	}
	@Override
	protected String getTestName() {
		return "UserSecurityTests";
	}

	@Override
	protected void setup() throws Exception {
		admin=Users.getUser("test@uiowa.edu");
		temp=ResourceLoader.loadUserIntoDatabase("user");
	}

	@Override
	protected void teardown() throws Exception {
		Users.deleteUser(temp.getID());
		
	}

}
