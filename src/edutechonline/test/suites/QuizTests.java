package edutechonline.test.suites;

import java.io.File;
import java.io.IOException;

import edutechonline.application.Constants;
import edutechonline.configuration.ConfigUtil;
import edutechonline.database.Courses;
import edutechonline.database.entity.Answer;
import edutechonline.database.entity.Question;
import edutechonline.database.entity.Quiz;
import edutechonline.test.Test;
import edutechonline.test.TestSet;
import edutechonline.util.QuizXMLReader;

public class QuizTests extends TestSet {

	
	//@Test
	private void readExampleTest() throws IOException {
		//File f=new File(Constants.CONFIG_PATH, "quiz.xml");
		//Quiz q=QuizXMLReader.readQuizXML(f);
		
		//Courses.addQuiz(q);

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
