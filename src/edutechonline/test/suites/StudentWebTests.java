package edutechonline.test.suites;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.Course;
import edutechonline.database.entity.User;
import edutechonline.test.ResourceLoader;
import edutechonline.test.Test;
import edutechonline.test.TestSet;
import edutechonline.test.TestUtil;
import edutechonline.util.Util;

public class StudentWebTests extends TestSet {
	WebDriver driver=null;
	
	User u=null;
	User manager=null;
	Course c=null;
	@Test
	private void editAccountTest() throws InterruptedException  {
		//Thread.sleep(5000);
        driver.get(Util.getAbsoluteURL("jsp/secure/accounts/edit.jsp"));
        String newFName=TestUtil.getRandomUserName();
		String newLName=TestUtil.getRandomUserName();

		WebElement fName=driver.findElement(By.id("fname"));
		fName.clear();
		WebElement lName=driver.findElement(By.id("lname"));
		lName.clear();
		fName.sendKeys(newFName);
		lName.sendKeys(newLName);
		fName.submit();
		
		Thread.sleep(2000);
		User newU=Users.getUser(u.getID());
	
		Assert.assertEquals(newU.getFirstName(), newFName);
		Assert.assertEquals(newU.getLastName(), newLName);
		u=newU;

	}
	
	@Test
	private void editPasswordTest() throws InterruptedException  {
		//Thread.sleep(5000);
        driver.get(Util.getAbsoluteURL("jsp/secure/accounts/editPassword.jsp"));
        String newPass=TestUtil.getRandomPassword();

		WebElement pass=driver.findElement(By.id("pass"));
		pass.clear();
		WebElement cpass=driver.findElement(By.id("cpass"));
		cpass.clear();
		pass.sendKeys(newPass);
		cpass.sendKeys(newPass);
		pass.submit();
	}
	
	@Test
	private void getCertTest() {
		driver.get(Util.getAbsoluteURL("jsp/secure/courses/details.jsp?cid="+c.getID()));
		WebElement download=driver.findElement(By.id("downloadCert"));
		download.click();
	}
	
	//@Test
	private void enrollCourseTest()
	{
		WebElement link=driver.findElement(By.id("searchcourse"));
		link.click();
		driver.findElement(By.id("viewb")).click();
		driver.findElement(By.id("enrollButton")).click();
		
		
	}
	
	
	
	@Override
	protected String getTestName() {
		return "StudentWebTests";
	}

	@Override
	protected void setup() throws Exception {
		u=ResourceLoader.loadUserIntoDatabase("user");
		manager=ResourceLoader.loadUserIntoDatabase("manager");
		c=ResourceLoader.loadCourseIntoDatabase(manager.getID());
		Courses.openCourse(c.getID());
		
		Courses.enroll(u.getID(), c.getID());
		driver=ResourceLoader.getWebDriver(u.getEmail(), u.getPassword());
		
	}

	@Override
	protected void teardown() throws Exception {
		driver.quit();
		Courses.deleteCourse(c.getID());
		Users.deleteUser(u.getID());
		Users.deleteUser(manager.getID());
		
	}

}
