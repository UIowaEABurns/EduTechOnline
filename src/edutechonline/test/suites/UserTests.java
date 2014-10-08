package edutechonline.test.suites;

import org.junit.Assert;

import edutechonline.database.Users;
import edutechonline.database.entity.User;
import edutechonline.test.Test;
import edutechonline.test.TestSequence;

public class UserTests extends TestSequence {
	
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
		
	}
	
	@Test
	private void whatever() {
		
	}
	
	@Override
	protected String getTestName() {
		return "UserTests";
	}

	@Override
	protected void setup() throws Exception {
		u=new User();
		
	}

	@Override
	protected void teardown() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
