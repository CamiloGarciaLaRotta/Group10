package ca.mcgill.ecse321.group10.TAMAS.model;
import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.group10.TAMAS.model.Course;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
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
    private Instructor aInstructor = new Instructor("6matty9", "yahboy69", "Matthew", "Lesko");
    
    private InputException e;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeCourseManager("output"+File.separator+"data.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cm = new CourseManager();
	}

	@After
	public void tearDown() throws Exception {
		cm.delete();
	}

	@Test
	public void testCourseClassNameNull() {
		assertEquals(0,cm.numberOfCourses());
		
		className = null;
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);
		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
			// Make sure error message displays
			assertEquals("Course name cannot be empty! ",e.getMessage());
			// Make sure Course does not get saved	
			assertEquals(0,cm.numberOfCourses());
		}
		
	}
	
	@Test
	public void testCourseClassNameEmpty() {
		assertEquals(0,cm.numberOfCourses());
		
		className = "";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);
		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
			assertEquals("Course name cannot be empty! ",e.getMessage());
			assertEquals(0,cm.numberOfCourses());
		}
	}
	
	@Test
	public void testCourseClassNameNumerical() {
		assertEquals(0,cm.numberOfCourses());
		
		className = "1234567890";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);
		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		// Course gets saved
		assertEquals(1,cm.numberOfCourses());
		// Correct className
		assertEquals("1234567890",cm.getCourse(0).getClassName());
	}
	
	@Test
	public void testCourseClassNameEmptySpace() {
		assertEquals(0,cm.numberOfCourses());
		
		className = " ";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);
		
		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
			assertEquals("Course name cannot be empty! ",e.getMessage());
			assertEquals(0,cm.numberOfCourses());
		}
	}
	@Test
	public void testCourseCdnNonUnique() {
		assertEquals(0,cm.numberOfCourses());
		
		className = "ECSE";
		cdn = 101;
		int cdn2 = cdn;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);
		
		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
			cc.createCourse(className, cdn2, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
			assertEquals("CDN must be unique! ", e.getMessage());
		}
		// Make sure course with identical cdn does not get saved
		assertEquals(1,cm.numberOfCourses());
	}
	/*
	@Test
	public void testCourseCdnNull() {
		assertEquals(0,cm.numberOfCourses());
		
		className = "ECSE";
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			Integer cdnNull = null;
			cc.createCourse(className, cdnNull, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
			assertEquals("CDN cannot be null/empty! ", e.getMessage());
		}
		catch (NumberFormatException e) {
		}
		// Make sure course with null cdn does not get saved
		assertEquals(0,cm.numberOfCourses());
	}
	*/
	@Test
	public void testCourseCdnNegative() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = -1;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
			assertEquals("CDN must be positive! ", e.getMessage());
			// Make sure course with null cdn does not get saved
			assertEquals(0,cm.numberOfCourses());
		}
	}
	
	@Test
	public void testCourseGraderTaTimeBudgetNegative() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) -10.00;
		taTimeBudget = (float) -10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
			assertEquals("Grader Budget must be positive! TA Budget must be positive! ", e.getMessage());
			assertEquals(0,cm.numberOfCourses());
		}
	}
	
	/*
	@Test
	public void testCourseGraderTaTimeBudgetEmpty() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
			assertEquals("Grader Budget must be positive! TA Budget must be positive! ", e.getMessage());
			assertEquals(0,cm.numberOfCourses());
		}
	}
	*/
	
	/*
	@Test
	public void testCourseCdn() {
		fail("Not yet implemented");
	}
	*/
	
	@Test
	public void testSetClassName() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		String classNameNew = "COMP250";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		assertEquals(1,cm.numberOfCourses());
		cm.getCourse(0).setClassName(classNameNew);
		assertEquals("COMP250",cm.getCourse(0).getClassName());
	}

	@Test
	public void testSetGraderTimeBudget() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		float newGraderTimeBudget = (float) 15.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		assertEquals(1,cm.numberOfCourses());
		cm.getCourse(0).setGraderTimeBudget(newGraderTimeBudget);
		// assertEquals (expected, actual, delta)
		assertEquals((float)15.00,(float)cm.getCourse(0).getGraderTimeBudget(),0);
	}

	@Test
	public void testSetTaTimeBudget() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		float newTaTimeBudget = (float) 69.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		assertEquals(1,cm.numberOfCourses());
		cm.getCourse(0).setTaTimeBudget(newTaTimeBudget);
		// assertEquals (expected, actual, delta)
		assertEquals((float)69.00,(float)cm.getCourse(0).getTaTimeBudget(),0);
	}

	@Test
	public void testGetClassName() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		assertEquals(1,cm.numberOfCourses());
		assertEquals("ECSE",cm.getCourse(0).getClassName());
	}

	@Test
	public void testGetCdn() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		assertEquals(1,cm.numberOfCourses());
		assertEquals(101,cm.getCourse(0).getCdn());
	}

	@Test
	public void testGetGraderTimeBudget() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		assertEquals(1,cm.numberOfCourses());
		assertEquals(10.00,cm.getCourse(0).getGraderTimeBudget(),0);
	}

	@Test
	public void testGetTaTimeBudget() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		assertEquals(1,cm.numberOfCourses());
		assertEquals(10.00,cm.getCourse(0).getTaTimeBudget(),0);
	}
	@Test
	public void testAddJobTimeTimeStringDoubleStringInstructor() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		// Time Constructor is in milliseconds
		Time aStartTime = new Time(36000000);
		Time aEndTime = new Time(36000000);
		String aDay = "Monday";
		Double aSalary = 15.00;
		String aRequirements = "Bachelors";
		
		
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		assertEquals(1,cm.numberOfCourses());
		cm.getCourse(0).addJob(aStartTime, aEndTime, aDay, aSalary, aRequirements, aInstructor);
		assertTrue(cm.getCourse(0).hasJobs());
	}

	@Test
	public void testAddJobJob_getJob() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		String className2 = "COMP251";
		cdn = 101;
		int cdn2 = 120;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		// Time Constructor is in milliseconds
		Time aStartTime = new Time(36000000);
		Time aEndTime = new Time(36000000);
		String aDay = "Monday";
		Double aSalary = 15.00;
		String aRequirements = "Bachelors";
		
		
		
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		Job aJob = new Job(aStartTime, aEndTime, aDay, aSalary, aRequirements, cm.getCourse(0), aInstructor);
		assertEquals(1,cm.numberOfCourses());
		cm.getCourse(0).addJob(aJob);
		assertTrue(cm.getCourse(0).hasJobs());
		assertEquals(aJob,cm.getCourse(0).getJob(0));
		cm.getCourse(0).getJobs();
		// Adding identical job will yield false
		assertFalse(cm.getCourse(0).addJob(aJob));
	}
	
	@Test
	public void testAddJobTwoJobs() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		String className2 = "COMP251";
		cdn = 101;
		int cdn2 = 120;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		// Time Constructor is in milliseconds
		Time aStartTime = new Time(36000000);
		Time aEndTime = new Time(36000000);
		String aDay = "Monday";
		Double aSalary = 15.00;
		String aRequirements = "Bachelors";
		
		
		
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
			cc.createCourse(className2, cdn2, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		Job aJob = new Job(aStartTime, aEndTime, aDay, aSalary, aRequirements, cm.getCourse(0), aInstructor);
		Job aJob2 = new Job(aStartTime, aEndTime, aDay, aSalary, aRequirements, cm.getCourse(1), aInstructor);
		// Two number of courses contained in course manager
		assertEquals(2,cm.numberOfCourses());
		// return false because aJob already has course that it is being added to
		assertFalse(cm.getCourse(0).addJob(aJob));
		assertTrue(cm.getCourse(0).addJob(aJob2));
		assertTrue(cm.getCourse(0).hasJobs());
		assertEquals(aJob,cm.getCourse(0).getJob(0));
		// returns the list List<Job>
		cm.getCourse(0).getJobs();
		// Adding identical job will yield false
		assertFalse(cm.getCourse(0).addJob(aJob));
		// Check to see that no job is added if input is null
	}

	@Test
	public void testNumberOfJob_getJob_hasJobs_indexOfJob() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		// Time Constructor is in milliseconds
		Time aStartTime = new Time(36000000);
		Time aEndTime = new Time(36000000);
		String aDay = "Monday";
		Double aSalary = 15.00;
		String aRequirements = "Bachelors";
		
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		Job aJob = new Job(aStartTime, aEndTime, aDay, aSalary, aRequirements, cm.getCourse(0), aInstructor);
		assertEquals(1,cm.numberOfCourses());
		cm.getCourse(0).addJob(aJob);
		assertTrue(cm.getCourse(0).hasJobs());
		assertEquals(aJob,cm.getCourse(0).getJob(0));
		//make sure correct number of jobs
		assertEquals(1,cm.getCourse(0).numberOfJobs());
		//make sure it hasJobs
		assertTrue(cm.getCourse(0).hasJobs());
		//make sure correct index of aJob
		assertEquals(0,cm.getCourse(0).indexOfJob(aJob));
		
	}
	@Test
	public void testMinimumNumberOfJobs() {
		assertEquals(0,Course.minimumNumberOfJobs());
	}

	@Test
	public void testRemoveJob() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		// Time Constructor is in milliseconds
		Time aStartTime = new Time(36000000);
		Time aEndTime = new Time(36000000);
		String aDay = "Monday";
		String aDay2 = "Tuesday";
		Double aSalary = 15.00;
		String aRequirements = "Bachelors";
		
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		Job aJob = new Job(aStartTime, aEndTime, aDay, aSalary, aRequirements, cm.getCourse(0), aInstructor);
		Job aJob2 = new Job(aStartTime, aEndTime, aDay2, aSalary, aRequirements, cm.getCourse(0), aInstructor);
		assertEquals(1,cm.numberOfCourses());
		cm.getCourse(0).addJob(aJob);
		cm.getCourse(0).addJob(aJob2);
		assertTrue(cm.getCourse(0).hasJobs());
		assertEquals(aJob,cm.getCourse(0).getJob(0));
		assertFalse(cm.getCourse(0).removeJob(aJob));
		assertFalse(cm.getCourse(0).removeJob(aJob2));
		cm.getCourse(0).delete();
		assertFalse(cm.getCourse(0).hasJobs());
	}

	@Test
	public void testAddJobAtAvailableIndex() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		String className2 = "COMP251";
		String className3 = "MATH363";
		cdn = 101;
		int cdn2 = 204;
		int cdn3 = 123;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		
		Time aStartTime = new Time(36000000);
		Time aEndTime = new Time(36000000);
		String aDay = "Tuesday";
		Double aSalary = 15.00;
		String aRequirements = "Bachelors";
		
				
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
			cc.createCourse(className2, cdn2, graderTimeBudget, taTimeBudget);
			cc.createCourse(className3, cdn3, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		Job aJob = new Job(aStartTime, aEndTime, aDay, aSalary, aRequirements,cm.getCourse(0),aInstructor);
		// this job already exists to this course, hence it will return false
		assertFalse(cm.getCourse(0).addJobAt(aJob, 1));
		assertFalse(cm.getCourse(0).addJobAt(aJob, -1));
		// this job is not assigned to the second course created
		assertTrue(cm.getCourse(1).addJobAt(aJob, 0));
		assertTrue(cm.getCourse(2).addJobAt(aJob, -1));
		
	}

	@Test
	public void testAddOrMoveJobAt() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		String className2 = "COMP251";
		cdn = 101;
		int cdn2 = 204;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		
		Time aStartTime = new Time(36000000);
		Time aEndTime = new Time(36000000);
		String aDay = "Tuesday";
		Double aSalary = 15.00;
		String aRequirements = "Bachelors";
		
				
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
			cc.createCourse(className2, cdn2, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		Job aJob = new Job(aStartTime, aEndTime, aDay, aSalary, aRequirements,cm.getCourse(0),aInstructor);
		// this job already exists to this course, hence it will move it to index 0
		assertTrue(cm.getCourse(0).addOrMoveJobAt(aJob, 0));
		// this will test for when index is negative
		assertTrue(cm.getCourse(0).addOrMoveJobAt(aJob, -1));
		// this job is not assigned to the second course hence it will be added to index 0
		assertTrue(cm.getCourse(1).addOrMoveJobAt(aJob, 0));
		// this will test for when the index is larger than the size of jobs list
		assertTrue(cm.getCourse(0).addOrMoveJobAt(aJob, 5));
		// this will test for the other branch for when the index is larger than the numberOfJobs
		assertTrue(cm.getCourse(0).addOrMoveJobAt(aJob, cm.getCourse(0).numberOfJobs()+1));
	}

	@Test
	public void testDelete() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		
		Time aStartTime = new Time(36000000);
		Time aEndTime = new Time(36000000);
		String aDay = "Monday";
		String aDay2 = "Tuesday";
		Double aSalary = 15.00;
		String aRequirements = "Bachelors";
		
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		Job aJob = new Job(aStartTime, aEndTime, aDay, aSalary, aRequirements, cm.getCourse(0), aInstructor);
		Job aJob2 = new Job(aStartTime, aEndTime, aDay2, aSalary, aRequirements, cm.getCourse(0), aInstructor);
		cm.getCourse(0).addJob(aJob);
		cm.getCourse(0).addJob(aJob2);
		cm.getCourse(0).delete();
		assertFalse(cm.getCourse(0).hasJobs());
		
	}

	@Test
	public void testToString() {
		assertEquals(0,cm.numberOfCourses());

		className = "ECSE";
		cdn = 101;
		graderTimeBudget = (float) 10.00;
		taTimeBudget = (float) 10.00;
		CourseController cc = new CourseController(cm,CourseController.COURSE_FILE_NAME);

		try {
			cc.createCourse(className, cdn, graderTimeBudget, taTimeBudget);
		}
		catch (InputException e) {
		}
		assertEquals(cm.getCourse(0).getClass().getName() + 
				'@' + Integer.toHexString(cm.getCourse(0).hashCode()) + "["+
	            "className" + ":" + cm.getCourse(0).getClassName()+ "," +
	            "cdn" + ":" + cm.getCourse(0).getCdn()+ "," +
	            "graderTimeBudget" + ":" + cm.getCourse(0).getGraderTimeBudget()+ "," +
	            "taTimeBudget" + ":" + cm.getCourse(0).getTaTimeBudget()+ "]",
	            cm.getCourse(0).toString());
	}
}
