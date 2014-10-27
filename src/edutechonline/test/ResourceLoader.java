package edutechonline.test;

import edutechonline.database.Users;
import edutechonline.database.entity.User;

/**
 * This class is responsible for loading primitives into the database that can be used for testing
 * 
 * @author Eric
 *
 */

public class ResourceLoader {

	
	public static User loadUserIntoDatabase() {
		User u=new User();
		u.setFirstName(TestUtil.getRandomUserName());
		u.setLastName(TestUtil.getRandomUserName());
		u.setRole("test");
		u.setPassword(TestUtil.getRandomPassword());
		u.setEmail(TestUtil.getRandomEmail());
		int id=Users.addUser(u);
		if (id<=0) {
			return null;//failure
		}
		u.setID(id);
		return u;
	}
}
