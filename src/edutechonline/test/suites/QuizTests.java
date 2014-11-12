package edutechonline.test.suites;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.text.DocumentException;

import edutechonline.application.Constants;
import edutechonline.configuration.ConfigUtil;
import edutechonline.database.Courses;
import edutechonline.database.entity.Answer;
import edutechonline.database.entity.Question;
import edutechonline.database.entity.Quiz;
import edutechonline.test.Test;
import edutechonline.test.TestSet;
import edutechonline.util.GenerateCertificate;
import edutechonline.util.QuizXMLReader;

public class QuizTests extends TestSet {

	
	//@Test
	private void readExampleTest() throws IOException {
		//File f=new File(Constants.CONFIG_PATH, "quiz.xml");
		//Quiz q=QuizXMLReader.readQuizXML(f);
		
		//Courses.addQuiz(q);

	}
	
	@Test 
	private void tempTest() throws FileNotFoundException, DocumentException {
		GenerateCertificate.makeCertificate(3, 23, new File("C:/users/eric/desktop/attemptingapdf.pdf"));
	}
	@Override
	protected String getTestName() {
		return "QuizTests";
	}

	@Override
	protected void setup() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void teardown() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
