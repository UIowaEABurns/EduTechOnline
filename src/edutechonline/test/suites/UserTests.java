package edutechonline.test.suites;

import java.util.List;

import org.junit.Assert;

import edutechonline.database.Users;
import edutechonline.database.entity.User;
import edutechonline.test.ResourceLoader;
import edutechonline.test.Test;
import edutechonline.test.TestSet;
import edutechonline.test.TestUtil;

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
	private void passResetTest() {
		String code=TestUtil.getRandomAlphaString(10);
		Assert.assertTrue(Users.addPassResetToUser(u.getID(), code));
		Assert.assertTrue(Users.userInPassResetTable(u.getID()));
		Assert.assertFalse(Users.userInPassResetTable(-1));
		Assert.assertTrue(Users.userInPassResetTable(u.getID(), code));
		Assert.assertFalse(Users.userInPassResetTable(u.getID(), code+"A"));

		
	}
	
	@Test
	private void registerAndVerifyTest() {
		User u=new User();
		u.setFirstName(TestUtil.getRandomUserName());
		u.setLastName(TestUtil.getRandomUserName());
		u.setEmail(TestUtil.getRandomEmail());
		u.setRole("user");
		String verify=TestUtil.getRandomAlphaString(9);
		u.setPassword(TestUtil.getRandomPassword());
		Assert.assertTrue(Users.registerUser(u,verify)>0);
		Assert.assertTrue(Users.verifyUser(verify, "user"));
	}
	
	
	@Test
	private void updateRoleTest() {
		User test=ResourceLoader.loadUserIntoDatabase();
		Assert.assertEquals(test.getRole(),Users.getUser(test.getID()).getRole());
		Assert.assertTrue(Users.updateRole(test.getID(), test.getRole()+"A"));
		Assert.assertEquals(test.getRole()+"A",Users.getUser(test.getID()).getRole());
		Assert.assertTrue(Users.deleteUser(test.getID()));		
	}
	
	@Test 
	private void updatePasswordTest() {
		String newPass=TestUtil.getRandomPassword();
		Assert.assertTrue(Users.updatePassword(u.getID(), newPass));
	}
	@Test
	private void updateUserTest() {
		String newName=TestUtil.getRandomUserName();
		String lastName=TestUtil.getRandomUserName();
		u.setFirstName(newName);
		u.setLastName(lastName);
		Assert.assertTrue(Users.updateUser(u));
		User newU=Users.getUser(u.getID());
		Assert.assertEquals(u.getFirstName(),newU.getFirstName());
		Assert.assertEquals(u.getLastName(),newU.getLastName());
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
