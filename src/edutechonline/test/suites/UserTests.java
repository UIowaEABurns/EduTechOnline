package edutechonline.test.suites;

import java.util.List;

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
	
	@Test
	private void getAllUsersTest() {
		List<User> users=Users.getAllUsers();
		Assert.assertNotNull(users);
		Assert.assertTrue(users.size()>=1);
		
		
	}
	
	@Test
	private void isAdminTest() {
		Assert.assertFalse(Users.isAdmin(u.getID()));
		Assert.assertFalse(Users.isAdmin(-1));
		
		
	}
	
	
	
	
	@Test
	private void updateRoleTest() {
		User test=ResourceLoader.loadUserIntoDatabase();
		Assert.assertEquals(test.getRole(),Users.getUser(test.getID()).getRole());
		Assert.assertTrue(Users.updateRole(test.getID(), test.getRole()+"A"));
		Assert.assertEquals(test.getRole()+"A",Users.getUser(test.getID()).getRole());
		Assert.assertTrue(Users.deleteUser(test.getID()));		
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
