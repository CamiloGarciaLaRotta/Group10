package ca.mcgill.ecse321.group10.TAMAS.model;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.group10.controller.CourseController;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class TestCourse {
	private CourseManager cm;
	private String className;
    private int cdn;
    private float graderTimeBudget;
    private float taTimeBudget;
    private String error;
    private CourseController cc;
    private Course aCourse;
    
    InputException e;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeCourseManager("output"+File.separator+"testData.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cm = new CourseManager();
		cc = new CourseController (cm, CourseController.COURSE_FILE_NAME);
	}

	@After
	public void tearDown() throws Exception {
		cm.removeCourse(aCourse);
		cm.delete();
		aCourse.delete();
	}

	@Test
	public void testCourseClassNameNull() throws InputException {
		className = null;
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		
		assertEquals(0,cm.numberOfCourses());
		
		aCourse = new Course(className,cdn,graderTimeBudget,taTimeBudget);
		// Test Case #1: className = null
		try {
			cm.addCourse(aCourse);
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
			// Error message displays
			assertEquals(e.getMessage(),"Course name cannot be empty! ");
			// Course does not get saved
			assertEquals(cm.numberOfCourses(),0);
		}
	}
	
	@Test
	public void testCourseClassNameEmpty() throws InputException {
		className = "";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		
		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
			assertEquals("Course name cannot be empty! ",e.getMessage());
		}
	}
	
	@Test
	public void testCourseClassNameNumerical() throws InputException {
		className = "1234567890";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		
		aCourse = new Course(className, cdn, graderTimeBudget, taTimeBudget);
		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
			cm.addCourse(aCourse);
		}
		catch (InputException e) {
			assertEquals(1,cm.numberOfCourses());
		}
	}
	
	@Test
	public void testCourseClassNameEmptySpace() throws InputException {
		className = " ";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		
		aCourse = new Course(className, cdn, graderTimeBudget, taTimeBudget);
		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
			cm.addCourse(aCourse);
		}
		catch (InputException e) {
			assertEquals(e.getMessage(),"Course name cannot be empty! ");
			assertEquals(cm.numberOfCourses(),0);
		}
	}
	
	@Test
	public void testCourseCdn() {
		fail("Not yet implemented");
	}

	/*
	@Test
	public void testSetClassName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGraderTimeBudget() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTaTimeBudget() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClassName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCdn() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGraderTimeBudget() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTaTimeBudget() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetJobs() {
		fail("Not yet implemented");
	}

	@Test
	public void testNumberOfJobs() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasJobs() {
		fail("Not yet implemented");
	}

	@Test
	public void testIndexOfJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testMinimumNumberOfJobs() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddJobTimeTimeStringDoubleStringInstructor() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddJobJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddJobAt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddOrMoveJobAt() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}
	*/
}
