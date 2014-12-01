package edutechonline.test.suites;

import org.junit.Assert;

import edutechonline.application.Constants;
import edutechonline.test.Test;
import edutechonline.test.TestSet;
import edutechonline.test.TestUtil;
import edutechonline.util.HttpRequestHelper;
import edutechonline.util.Validator;

public class ValidationTests extends TestSet {

	@Test
	private void emailTests() {
		Assert.assertTrue(Validator.isValidEmail("test@uiowa.edu"));
		Assert.assertTrue(Validator.isValidEmail("anything@lmuchlongertext.com"));
		for (int x=0;x<100;x++) {
			Assert.assertTrue(Validator.isValidEmail(TestUtil.getRandomEmail()));
		}
		
		Assert.assertFalse(Validator.isValidEmail("test@uiowaedu"));
		Assert.assertFalse(Validator.isValidEmail(""));
		Assert.assertFalse(Validator.isValidEmail(null));

		Assert.assertFalse(Validator.isValidEmail("testuiowa.edu"));
	}
	
	@Test
	private void categoryTests() {
		Assert.assertTrue(Validator.isValidCategory("Joe"));
		Assert.assertTrue(Validator.isValidCategory("Robert Griffin III"));
		Assert.assertTrue(Validator.isValidCategory("nocaps"));
		Assert.assertTrue(Validator.isValidCategory("Hyphenated-Category"));

		Assert.assertFalse(Validator.isValidCategory(""));
		Assert.assertFalse(Validator.isValidCategory(null));
		Assert.assertFalse(Validator.isValidCategory("Full sentence."));
		Assert.assertFalse(Validator.isValidCategory("This is a Category that is way too long to fit in the Category character limit for now"));

	}
	
	@Test
	private void nameTests() {
		Assert.assertTrue(Validator.isValidName("Joe"));
		Assert.assertTrue(Validator.isValidName("Robert Griffin III"));
		Assert.assertTrue(Validator.isValidName("nocaps"));
		Assert.assertTrue(Validator.isValidName("Hyphenated-Name"));

		Assert.assertFalse(Validator.isValidName(""));
		Assert.assertFalse(Validator.isValidName(null));
		Assert.assertFalse(Validator.isValidName("Full sentence."));
		Assert.assertFalse(Validator.isValidName("This is a name that is way too long to fit in the name character limit for now"));

	}
	
	@Test
	private void integerTests() {
		Assert.assertTrue(Validator.isValidInteger("3"));
		Assert.assertTrue(Validator.isValidInteger("-1"));
		Assert.assertTrue(Validator.isValidInteger("0"));
		Assert.assertTrue(Validator.isValidInteger("3952985"));
		
		Assert.assertFalse(Validator.isValidInteger("3.4"));

		Assert.assertFalse(Validator.isValidInteger("34848752389723987235"));
		Assert.assertFalse(Validator.isValidInteger("human"));
		Assert.assertFalse(Validator.isValidInteger(null));
		Assert.assertFalse(Validator.isValidInteger(""));
	}
	
	@Test
	private void floatTests() {
		Assert.assertTrue(Validator.isValidFloat("3.4"));
		Assert.assertTrue(Validator.isValidFloat("3"));
		Assert.assertTrue(Validator.isValidFloat("-1.3"));
		Assert.assertTrue(Validator.isValidFloat("0"));

		
		Assert.assertFalse(Validator.isValidFloat("human"));
		Assert.assertFalse(Validator.isValidFloat(null));
		Assert.assertFalse(Validator.isValidFloat(""));
	}
	
	@Test
	private void isValidRoleTest() {
		Assert.assertTrue(Validator.isValidRole("user"));
		Assert.assertTrue(Validator.isValidRole("admin"));
		Assert.assertTrue(Validator.isValidRole("manager"));
		Assert.assertTrue(Validator.isValidRole("unverified"));
		
		Assert.assertFalse(Validator.isValidRole("student"));
		Assert.assertFalse(Validator.isValidRole("test"));
		Assert.assertFalse(Validator.isValidRole(""));
		Assert.assertFalse(Validator.isValidRole(null));
	}
	
	@Test
	private void isValidDescriptionTest() {
		Assert.assertTrue(Validator.isValidDescription("this, is a description! with some - punctuation."));
		Assert.assertTrue(Validator.isValidDescription(TestUtil.getRandomAlphaString(Constants.DESC_LENGTH)));
		Assert.assertTrue(Validator.isValidDescription("a"));
		Assert.assertTrue(Validator.isValidDescription("Another test"));
		
		Assert.assertFalse(Validator.isValidDescription(TestUtil.getRandomAlphaString(Constants.DESC_LENGTH+1)));
		Assert.assertFalse(Validator.isValidDescription(null));
		
	}
	@Test
	private void isValidExtensionTest() {
		Assert.assertTrue(Validator.isValidFileExtension("this.pdf"));
		Assert.assertTrue(Validator.isValidFileExtension("this.txt"));
		Assert.assertFalse(Validator.isValidFileExtension("this.that"));
	}
	
	@Test
	private void isNullOrEmptyTest() {
		Assert.assertTrue(HttpRequestHelper.isNullOrEmpty(null));
		Assert.assertTrue(HttpRequestHelper.isNullOrEmpty(""));
		Assert.assertFalse(HttpRequestHelper.isNullOrEmpty("null"));

	}
	
	@Override
	protected String getTestName() {
		return "ValidationTests";
	}

	@Override
	protected void setup() throws Exception {
		
	}

	@Override
	protected void teardown() throws Exception {
		
	}

}
