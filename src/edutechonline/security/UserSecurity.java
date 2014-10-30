package edutechonline.security;

import edutechonline.database.Users;

public class UserSecurity {

	
	public static ValidatorStatusCode canUserDeleteUser(int userIdToDelete, int userIdMakingRequest) {
		if (!Users.isAdmin(userIdMakingRequest)) {
			return new ValidatorStatusCode(false, "only admins can delete users");
		}
		
		return new ValidatorStatusCode(true);
	}
}
