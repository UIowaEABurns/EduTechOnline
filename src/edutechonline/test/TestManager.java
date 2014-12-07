package edutechonline.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import edutechonline.test.TestStatus.TestStatusCode;
import edutechonline.test.suites.CourseTests;
import edutechonline.test.suites.QuizTests;
import edutechonline.test.suites.StudentWebTests;
import edutechonline.test.suites.UserTests;
import edutechonline.test.suites.ValidationTests;
import edutechonline.test.suites.WebTests;
import edutechonline.test.suites.security.CourseSecurityTests;
import edutechonline.test.suites.security.UserSecurityTests;

/***
 * Originally written for the Starexec project
 * @author: Eric Burns
 */

/**
 * This class maintains a list of all TestSequences and handles requests
 * for running those sequences. It prevents multiple instances of the same
 * TestSequence from being run simultaneously.
 * @author Eric Burns
 *
 */
public class TestManager {
	
public static File getResource(String name) {
		
		return new File(TestManager.class.getResource(name).getFile());
	}	
	private static final Logger log = Logger.getLogger(TestManager.class);
	private final static AtomicBoolean isRunning=new AtomicBoolean(false);
	//this should never be modified outside of the initializeTests method
	private final static List<TestSet> tests=new ArrayList<TestSet>();
	//all test sequences need to be initialized here
	public static void initializeTests() {
		
		//tests.add(new UserTests());
		//tests.add(new ValidationTests());
		//tests.add(new CourseTests());
		//tests.add(new QuizTests());
		tests.add(new WebTests());
		//tests.add(new UserSecurityTests());
		//tests.add(new CourseSecurityTests());
		//tests.add(new StudentWebTests());
	}
	
	
	
	/**
	 * Executes every test sequence in tests
	 * @return True if the tests were started, and false if they were not for some reason
	 */
	public static boolean executeAllTestSequences() {
		
		//don't do anything if the tests are already running
		if (!isRunning.compareAndSet(false, true)) {
			return false;
		}
		final ExecutorService threadPool = Executors.newCachedThreadPool();
		
		
		//we want to return here, not wait until all the tests finish, which is why we spin off a new threads
		threadPool.execute(new Runnable() {
			@Override
			public void run(){
				
				//we want to clear all the results first, so it's obvious to the user what is left to be run
				for (TestSet t : tests) {
					t.clearResults();
				}
				
				for (TestSet t : tests) {
					t.execute();
				}
				isRunning.set(false);
				
				reportResults();
			}
		});	
		return true;
	}
	
	private static void logTestSet(TestSet s ){
		try {
			StringBuilder sb=new StringBuilder();
			sb.append("\n");
			sb.append("test run data for test set ");
			sb.append(s.getName());
			sb.append("\n");
			
			sb.append("tests | error | passed\n");
			sb.append(s.getTestCount()+" | "+s.getTestsFailed()+" | "+s.getTestsPassed());
			sb.append("\n");
			for (TestResult result : s.getTestResults()) {
				sb.append(result.getName() + " | "+result.getStatus().getStatus() + " | "+result.getTime());
				sb.append("\n");
				if (result.getStatus().getCode()==TestStatusCode.STATUS_FAILED) {
					sb.append(result.getErrorTrace());
					sb.append("\n");
				}
			}
			log.debug(sb.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
	}
	/**
	 * Writes out a report of every test sequence to the given file. File will be created if it doesn't exist.
	 * @param outputFile
	 */
	private static void reportResults() {
		log.debug("reporting all testing results");
		int failedSum=0;
		int successSum=0;
		for (TestSet s : tests) {
			failedSum+=s.getTestsFailed();
			successSum+=s.getTestsPassed();
		}
		log.debug("Failing tests = "+failedSum+" | passing tests = "+successSum);
		for (TestSet s : tests) {
			logTestSet(s);
		}
	}

}
