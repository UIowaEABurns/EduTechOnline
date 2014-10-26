package edutechonline.test.suites;

import org.junit.Assert;

import edutechonline.database.Users;
import edutechonline.database.entity.User;
import edutechonline.test.ResourceLoader;
import edutechonline.test.Test;
import edutechonline.test.TestSet;

public class UserTests extends TestSet {
	
	private User u=null;

	@Test
	private void getTest() {
		User tempUser=Users.getUser(u.getID());
		Assert.assertNotNull(tempUser);
		Assert.assertNull(Users.getUser(-48484));
		Assert.assertEquals(u.getFirstName(),tempUser.getFirstName());
	}
	
	@Test
	private void getUserByEmail() {
		User tempUser=Users.getUser(u.getEmail());
		Assert.assertNotNull(tempUser);
		Assert.assertNull(Users.getUser(-48484));
		Assert.assertEquals(u.getFirstName(),tempUser.getFirstName());
	}
	
	
	
	@Override
	protected String getTestName() {
		return "UserTests";
	}

	@Override
	protected void setup() throws Exception {
		u=ResourceLoader.loadUserIntoDatabase();
		
	}

	@Override
	protected void teardown() throws Exception {
		Assert.assertTrue(Users.deleteUser(u.getID()));
		
	}

}
