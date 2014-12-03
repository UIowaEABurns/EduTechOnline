package edutechonline.test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.ContentTopic;
import edutechonline.database.entity.ContentTopic.ContentType;
import edutechonline.database.entity.Course;
import edutechonline.database.entity.User;
import edutechonline.util.Util;

/**
 * This class is responsible for loading primitives into the database that can be used for testing
 * 
 * @author Eric
 *
 */

public class ResourceLoader {

	public static User loadUserIntoDatabase(String role) {
		User u=new User();
		u.setFirstName(TestUtil.getRandomUserName());
		u.setLastName(TestUtil.getRandomUserName());
		u.setRole(role);
		u.setPassword(TestUtil.getRandomPassword());
		u.setEmail(TestUtil.getRandomEmail());
		int id=Users.addUser(u);
		if (id<=0) {
			return null;//failure
		}
		u.setID(id);
		return u;
	}
	public static User loadUserIntoDatabase() {
		return loadUserIntoDatabase("test");
	}
	
	/**
	 * The user is the owner of the course
	 * @param userId
	 * @return
	 */
	public static Course loadCourseIntoDatabase(int userId) {
		Random rand=new Random();
		Course c=new Course();
		c.setName(TestUtil.getRandomUserName());
		c.setDescription(TestUtil.getRandomUserName());
		c.setCategory(TestUtil.getRandomAlphaString(20));
		c.setOpen(false);
		c.setCost(rand.nextFloat());
		c.setOwnerId(userId);
		int id=Courses.addCourse(c);
		if (id<=0) {
			return null; //failure
		}
		c.setID(id);
		return c;
	}
	
	public static ContentTopic loadTopicIntoDatabase(int courseId) {
		ContentTopic c=new ContentTopic();
		c.setName(TestUtil.getRandomUserName());
		c.setDescription(TestUtil.getRandomUserName());
		c.setUrl("fake");
		c.setType(ContentType.PDF);
		c.setCourseId(courseId);
		int id=Courses.addContentTopic(c);
		if (id<=0) {
			return null;
		}
		return c;
	}
	
	
	/**
	 * Returns a WebDriver for selenium testing. The driver we be logged into the website 
	 * upon return
	 * @param email The email address of the user to log in
	 * @param password The password of the user to log in
	 * @return
	 */
	public static WebDriver getWebDriver(String email, String password) {
			//WebDriver driver=new FirefoxDriver();
		    WebDriver driver = new FirefoxDriver();
		  
	        driver.get(Util.getAbsoluteURL("jsp/secure/index.jsp"));
	        WebElement userName=driver.findElement(By.name("j_username"));
	        userName.sendKeys(email);
	        driver.findElement(By.name("j_password")).sendKeys(password);
	        try {
	        	//Thread.sleep(2000);
	        } catch (Exception e) {
	        	// pass
	        }
	        userName.submit();
	       driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	       return driver;
	}
}
