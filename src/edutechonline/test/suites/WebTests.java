package edutechonline.test.suites;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edutechonline.database.Courses;
import edutechonline.test.ResourceLoader;
import edutechonline.test.Test;
import edutechonline.test.TestSet;

public class WebTests extends TestSet {

	private WebDriver driver=null;
	
	@Test
	private void addCourseTest() {
		
		WebElement link=driver.findElement(By.id("addCourseFooter"));
		link.click();
		String url = driver.getCurrentUrl();
		WebElement nameField=driver.findElement(By.id("name"));
		WebElement descField=driver.findElement(By.id("desc"));

		WebElement categoryField=driver.findElement(By.id("category"));
		WebElement costField=driver.findElement(By.id("cost"));
		nameField.submit(); //should fail, haven't added anything
		Assert.assertEquals(url,driver.getCurrentUrl());
		nameField.sendKeys("Psychology");
		
		categoryField.sendKeys("Social Science");
		costField.sendKeys("10.00");
		descField.sendKeys("This is an introductory psychology course for new students. It has no prerequisites" );
		
		nameField.submit();
		
		List<WebElement> links=driver.findElements(By.className("viewCourseLink"));
		links.get(links.size()-1).click(); //navigate to the course details page
		
		WebElement releaseCourse =driver.findElement(By.id("toggleVisible"));
		releaseCourse.click();
		
		WebElement addTopicLink =driver.findElement(By.id("addContentLink"));
		addTopicLink.click();
		
		WebElement topicName = driver.findElement(By.id("name"));
		WebElement topicDesc = driver.findElement(By.id("desc"));
		WebElement urlRadio = driver.findElement(By.id("url"));
		urlRadio.click();
		WebElement urlField = driver.findElement(By.id("typeUrl"));
		
		topicName.sendKeys("Intro video for psychology");
		topicDesc.sendKeys("This introductory video will explain the basics of the course");
		urlField.sendKeys("https://www.youtube.com/embed/vo4pMVb0R6M");
		topicName.submit();
		driver.findElement(By.className("contentTopicLink")).click();
	}
	
	
	
	
	
	@Test
	
	private void deleteCourseTest()
	{
		
		WebElement link=driver.findElement(By.id("managecourses"));
		link.click();
		
		List<WebElement> links=driver.findElements(By.className("viewCourseLink"));
		links.get(links.size()-1).click(); //navigate to the course details page
				
		WebElement deleteCourseLink =driver.findElement(By.id("deleteButton"));
		deleteCourseLink.click();
	}
	
	@Override
	protected String getTestName() {
		return "WebTests";
	}

	@Override
	protected void setup() throws Exception {
		driver=ResourceLoader.getWebDriver("eric-burns@uiowa.edu", "test");
		
	}

	@Override
	protected void teardown() throws Exception {
		driver.quit();
	}

}
