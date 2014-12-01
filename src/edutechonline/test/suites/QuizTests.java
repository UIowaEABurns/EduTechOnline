package edutechonline.test.suites;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.itextpdf.text.DocumentException;

import edutechonline.application.Constants;
import edutechonline.configuration.ConfigUtil;
import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.Answer;
import edutechonline.database.entity.ContentTopic.ContentType;
import edutechonline.database.entity.Course;
import edutechonline.database.entity.Question;
import edutechonline.database.entity.Quiz;
import edutechonline.database.entity.User;
import edutechonline.test.ResourceLoader;
import edutechonline.test.Test;
import edutechonline.test.TestSet;
import edutechonline.util.GenerateCertificate;
import edutechonline.util.QuizXMLReader;

public class QuizTests extends TestSet {

	User student=null;
	User manager=null;
	Course c=null;
	Quiz q=null;
	@Test
	private void addQuizTest() throws IOException {
		File f=new File(Constants.CONFIG_PATH, "quiz.xml");
		Quiz q=QuizXMLReader.readQuizXML(f);
		q.setCourseId(c.getID());
		int id=Courses.addQuiz(q);
		Assert.assertTrue(id>0);
		Assert.assertNotNull(Courses.getQuiz(id));
		Assert.assertTrue(Courses.deleteContentTopic(id));
	}
	@Test
	private void hasTakenQuizTest() {
		Assert.assertTrue(Courses.hasUserTakenQuiz(q.getID(), student.getID()));
		Assert.assertFalse(Courses.hasUserTakenQuiz(q.getID(), manager.getID()));

	}
	
	@Test 
	private void makeCertificateTest() throws FileNotFoundException, DocumentException {
		String filePath=Courses.getAbsolutePathForCertificate(student.getID(), c.getID());
		File pdfFile=new File(filePath);
		GenerateCertificate.makeCertificate(student.getID(), c.getID(), pdfFile);
		Assert.assertTrue(pdfFile.exists());
		
	}
	@Test
	private void getCorrectAnswerTest() {
		for (Question quest : q.getQuestions()) {
			Assert.assertTrue(quest.getCorrectAnswer().isCorrect());
		}
	}
	@Test
	private void getQuestionTest() {
		Assert.assertNull(q.getQuestion(-1));
		Assert.assertNotNull(q.getQuestion(q.getQuestions().get(0).getID()));
	}
	@Override
	protected String getTestName() {
		return "QuizTests";
	}

	@Override
	protected void setup() throws Exception {
		manager=ResourceLoader.loadUserIntoDatabase("manager");
		student=ResourceLoader.loadUserIntoDatabase("user");
		c=ResourceLoader.loadCourseIntoDatabase(manager.getID());
		Courses.enroll(student.getID(), c.getID());
		File f=new File(Constants.CONFIG_PATH, "quiz.xml");
		q=QuizXMLReader.readQuizXML(f);
		q.setCourseId(c.getID());
		q.setType(ContentType.QUIZ);
		Courses.addQuiz(q);
		List<Integer> answers=new ArrayList<Integer>();
		for (Question question : q.getQuestions()) {
			answers.add(question.getAnswers().get(0).getID());
		}
		Courses.addQuizScores(student.getID(), q.getID(), 0, answers);
	}

	@Override
	protected void teardown() throws Exception {
		Courses.deleteCourse(c.getID());
		Users.deleteUser(student.getID());
		Users.deleteUser(manager.getID());
		
	}

}
