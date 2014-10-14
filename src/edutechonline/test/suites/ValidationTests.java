package edutechonline.test.suites;

import org.junit.Assert;

import edutechonline.test.Test;
import edutechonline.test.TestSet;
import edutechonline.util.Validator;

public class ValidationTests extends TestSet {

	@Test
	private void emailTests() {
		Assert.assertTrue(Validator.isValidEmail("test@uiowa.edu"));
		
		Assert.assertFalse(Validator.isValidEmail("testuiowa.edu"));
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
