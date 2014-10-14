package edutechonline.database.entity;

/**
 * Contains all fields to represent a single EduTechOnline User
 * @author Eric
 *
 */
public class User extends IDEntity {
	private String firstName=null;
	private String lastName=null;
	private String email=null;
	private String password=null; // may be either plaintext or hash depending on context!
	private String role=null;
	public User() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return firstName+" "+lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
