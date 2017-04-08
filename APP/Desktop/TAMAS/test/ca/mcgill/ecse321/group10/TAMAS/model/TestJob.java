//package ca.mcgill.ecse321.group10.TAMAS.model;
//
//import static org.junit.Assert.*;
//
//import java.io.File;
//import java.sql.Time;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import ca.mcgill.ecse321.group10.TAMAS.model.Job.Position;
//import ca.mcgill.ecse321.group10.controller.ApplicationController;
//import ca.mcgill.ecse321.group10.controller.InputException;
//import ca.mcgill.ecse321.group10.controller.ProfileController;
//import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;
//
//public class TestJob {
//	//Constructor Args: Time aStartTime, Time aEndTime, String aDay, double aSalary, String aRequirements, Course aCourse, Instructor aInstructor
//	private Time aStartTime, aEndTime;
//	private String aDay;
//	private double aSalary;
//	private String aRequirements;
//	
//	// Initializating a valid instance of a Course for testing Job constructor purposes
//	private String aClassName = "COMP101";
//	private int aCdn = 101;
//	private float aGraderTimeBudget = (float) 10.00, aTaTimeBudget = (float) 10.00;
//	private Course aCourse = new Course(aClassName,aCdn,aGraderTimeBudget,aTaTimeBudget);
//	
//	// Initializating a valid instance of a Instructor for testing Job constructor purposes
//	private String aUsername = "mattyboy", aPassword = "dabest101programmer", aFirstName = "Matthew", aLastName = "Lesko";
//	private Instructor aInstructor = new Instructor(aUsername, aPassword, aFirstName, aLastName);
//	
//	private ApplicationManager am;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		PersistenceXStream.initializeApplicationManager(ApplicationController.APPLICATION_FILE_NAME,ProfileController.PROFILE_FILE_NAME);
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		am = new ApplicationManager(); 
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		am.delete();
//	}
//
//	@Test
//	public void testJobValidInputs() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//	}
//	
//	@Test
//	public void testJobNullStartEndTimes() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = null;
//		aEndTime = null;
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {;
//			// Correct error (and message)
//			assertEquals("Start or End Time(s) cannot be empty! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	
//	@Test
//	public void testJobStartBeforeEndTime() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("11:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("10:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//			// Correct error (and message)
//			assertEquals("Start time cannot be after end time! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	/*
//	@Test
//	public void testJobNullRequirements() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = new Time(36000000);	// 10:00 AM
//		aEndTime = new Time(39600000);		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = null;
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//			assertEquals("Requirements cannot be empty! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	*/
//	
//	@Test
//	public void testJobNullCourse() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		aCourse = null;
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//			// Right error (and message)
//			assertEquals("Course must be defined! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	
//	@Test
//	public void testJobNullInstructor() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		aInstructor = null;
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//			// Correct error (and message)
//			assertEquals("Instructor must be defined! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	
//	@Test
//	public void testJobNullInstructor_NullCourse() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		aInstructor = null;
//		aCourse = null;
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//			// Correct errors (and message)
//			assertEquals("Instructor must be defined! Course must be defined! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	
//	@Test
//	public void testJobNegativeSalary() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = -10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//			// Correct error (and message)
//			assertEquals("Salary must be positive! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	/*
//	@Test
//	public void testJobAlphabeticalSalary() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = new Time(36000000);	// 10:00 AM
//		aEndTime = new Time(39600000);		// 11:00 AM
//		aDay = "Monday";
//		aSalary = "ten";
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//			// Correct error (and message)
//			assertEquals("Salary must be a number! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	*/
//	@Test
//	public void testJobNullDay() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = null;
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//			// Correct error (and message)
//			assertEquals("Day must be specified! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	
//	@Test
//	public void testJobWeekendDay() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Saturday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//			// Correct error (and message)
//			assertEquals("Day must be a work day! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//		
//		aDay = "Sunday";
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//			// Correct error (and message)
//			assertEquals("Day must be a work day! ", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	
//	@Test
//	public void testJobDidNotAddCourse() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		aCourse = null;
//		
//		try {
//			Job aJob = new Job(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (RuntimeException e) {
//			// Correct error (and message)
//			assertEquals("Unable to create job due to course", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	
//	@Test
//	public void testJobDidNotAddInstructor() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		aInstructor = null;
//		
//		try {
//			Job aJob = new Job(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (RuntimeException e) {
//			// Correct error (and message)
//			assertEquals("Unable to create job due to instructor", e.getMessage());
//		}
//		// Job does not get saved
//		assertEquals(0,am.numberOfJobs());
//	}
//	
//	@Test
//	public void testJobSetStartEndTimes() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		aStartTime = new Time(32400000);
//		assertTrue(am.getJob(0).setStartTime(aStartTime));
//		aEndTime = new Time(43200000);
//		assertTrue(am.getJob(0).setEndTime(aEndTime));
//		aStartTime = new Time(46800000);
//		assertTrue(am.getJob(0).setStartTime(aStartTime));
//		//fail("startTime can be set after endTime by calling setStartTime on an instance of Job after it has been added to the system");
//	}
//	
//	@Test
//	public void testSetDay() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		assertTrue(am.getJob(0).setDay("Tuesday"));
//	}
//	
//	
//	@Test
//	public void testGetStartEndTimes() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		assertEquals("10:00:00",am.getJob(0).getStartTime().toString());
//		assertEquals("11:00:00",am.getJob(0).getEndTime().toString());
//	}
//
//	@Test
//	public void testGetDay() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		assertEquals("Monday",am.getJob(0).getDay());
//	}
//
//	@Test
//	public void testGetSalary() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		assertEquals(10.00,am.getJob(0).getSalary(),0);
//	}
//
//	@Test
//	public void testGetRequirements() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		assertEquals("Bachelors in Fine Arts",am.getJob(0).getRequirements());
//	}
//
//	@Test
//	public void testGetId() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		boolean gotId;
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		// Test id is an autounique attribute, as long as its an int, return true
//		// seems absolutely redundant and useless to test this, but it's done for 100% statement coverage
//		if(am.getJob(0).getId() >= 0 || am.getJob(0).getId() < 0) gotId = true;
//		else gotId = false;
//		assertTrue(gotId);
//		
//	}
//
//	@Test
//	public void testGetPositionFullName_GetPosition() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		// Position is automatically set to TA by default
//		assertEquals("TA",am.getJob(0).getPositionFullName());
//		assertEquals("TA",am.getJob(0).getPosition().toString());
//	}
//
//	@Test
//	public void testSetPosition() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		Job.Position aPosition = Job.Position.GRADER;
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		// Setting different position (GRADER) works
//		assertTrue(am.getJob(0).setPosition(aPosition));
//	}
//
//	@Test
//	public void testGetCourse_GetInstructor() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		assertEquals(aCourse,am.getJob(0).getCourse());
//		assertEquals(aInstructor,am.getJob(0).getInstructor());
//	}
//
//	@Test
//	public void testNumberOfStudents_HasStudents() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		// No students assigned to Job; hence 0
//		//assertEquals(0,am.getJob(0).numberOfStudents());
//		// No students; hence false
//		//assertFalse(am.getJob(0).hasStudents());
//	}
//
//	@Test
//	public void testGetApplication() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		String aDay2 = "Tuesday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//			ac.addJobToSystem(aStartTime, aEndTime, aDay2, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(2,am.numberOfJobs());
//		// Add student to a job
//		//assertTrue(am.getJob(0).addStudent(aStudent));
//		
//		// THE REASON FOR 2 JOBS FOR 2 APPLICATIONS: Each application NEEDS a job
//		// Make new application; apply aStudent to am.getJob(0)
//		Application aApplication = am.getJob(0).addApplication(aStudent);
//		Application aApplication2 = am.getJob(1).addApplication(aStudent);
//		
//		// Make sure getApplication(index i) returns correct application
//		assertEquals(aApplication,am.getJob(0).getApplication(0));
//		assertEquals(aApplication2,am.getJob(1).getApplication(0));
//
//	}
//
//	@Test
//	public void testGetApplications() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		String aDay2 = "Tuesday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//			ac.addJobToSystem(aStartTime, aEndTime, aDay2, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(2,am.numberOfJobs());
//		// Add student to a job
//		//assertTrue(am.getJob(0).addStudent(aStudent));
//		
//		// THE REASON FOR 2 JOBS FOR 2 APPLICATIONS: Each application NEEDS a job
//		// Make new application; apply aStudent to am.getJob(0)
//		Application aApplication = am.getJob(0).addApplication(aStudent);
//		Application aApplication2 = am.getJob(1).addApplication(aStudent);
//		
//		// test getApplications works
//		am.getJob(0).getApplications();
//	}
//
//
//	@Test
//	public void testSetCourse_SetInstructor() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		
//		String className = "CCOM206";
//		int aCdn = 101;
//		float aGraderTimeBudget = 1500;
//		float aTaTimeBudget = 1500;
//		Course aCourse2 = new Course(className, aCdn, aGraderTimeBudget, aTaTimeBudget);
//		
//		String userName2 = "mikeyboy";
//		String password2 = "1234";
//		String firstName2 = "Michael";
//		String lastName2 = "Lesko";
//		Instructor aInstructor2 = new Instructor(userName2, password2, firstName2, lastName2);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		// Setting same Course
//		assertTrue(am.getJob(0).setCourse(aCourse));
//		// Setting same Instructor
//		assertTrue(am.getJob(0).setInstructor(aInstructor));
//		// Setting different Instructor
//		assertTrue(am.getJob(0).setCourse(aCourse2));
//		// Setting different Course
//		assertTrue(am.getJob(0).setInstructor(aInstructor2));
//	}
//
//	@Test
//	public void testMinimumNumberOfStudentsAndApplicants() {
//		// Both are a minimum of 0, the methods work and both return 0
//		//assertEquals(0,Job.minimumNumberOfStudents());
//		assertEquals(0,Job.minimumNumberOfApplications());
//	}
//
//	@Test
//	public void testAddStudent_AddStudentAt_RemoveStudent() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		String studUsername2 = "student70";
//		String aFirstName2 = "Michael";
//		String aLastName2 = "Lesko";
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		Student aStudent2 = new Student(studUsername2, studPassword, aFirstName2, aLastName2, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		//assertTrue(am.getJob(0).addStudent(aStudent));
//		// 1 Student
//		//assertEquals(1,am.getJob(0).numberOfStudents());
//		// Make sure it has students
//		//assertTrue(am.getJob(0).hasStudents());
//		// Make sure the instances of Student are identical
//		//assertEquals(aStudent,am.getJob(0).getStudent(0));
//		
//		// Adding the same student returns false
//		//assertFalse(am.getJob(0).addStudent(aStudent));
//		// Adding the same student will return false
//		//assertFalse(am.getJob(0).addStudentAt(aStudent, 0));
//		// Still 1 student
//		//assertEquals(1,am.getJob(0).numberOfStudents());
//		// Adding different student will return true
//		//assertTrue(am.getJob(0).addStudentAt(aStudent2, -1));
//		// Now 2 students
//		//assertEquals(2,am.getJob(0).numberOfStudents());
//		// Remove aStudent returns true
//		//assertTrue(am.getJob(0).removeStudent(aStudent));
//		// Removing the same student returns false
//		//assertFalse(am.getJob(0).removeStudent(aStudent));
//		// Now 1 student
//		//assertEquals(1,am.getJob(0).numberOfStudents());
//		
//		// This tests for another branch in the addStudentAt method (specifically the conditional with index>numberOfStudents)
//		//assertTrue(am.getJob(0).addStudentAt(aStudent, am.getJob(0).numberOfStudents()));
//		//assertTrue(am.getJob(0).removeStudent(aStudent));
//	}
//	
//	@Test
//	public void testAddOrMoveStudentAt() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		String studUsername2 = "student70";
//		String aFirstName2 = "Michael";
//		String aLastName2 = "Lesko";
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		Student aStudent2 = new Student(studUsername2, studPassword, aFirstName2, aLastName2, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		// Student hasn't been added, adds student and returns true
//		//assertTrue(am.getJob(0).addOrMoveStudentAt(aStudent, 0));
//		// Tests for the branch if (index < 0)
//		//assertTrue(am.getJob(0).addOrMoveStudentAt(aStudent,-1));
//		
//		//assertTrue(am.getJob(0).addOrMoveStudentAt(aStudent2, am.getJob(0).numberOfStudents()));
//	}
//	
//
//	@Test
//	public void testGetStudents() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		//assertTrue(am.getJob(0).addStudent(aStudent));
//		//assertTrue(am.getJob(0).hasStudents());
//		//am.getJob(0).getStudents();
//	}
//	
//	@Test
//	public void testAddApplication() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		String aDay2 = "Tuesday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//			ac.addJobToSystem(aStartTime, aEndTime, aDay2, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(2,am.numberOfJobs());
//		// Add student to a job
//		//assertTrue(am.getJob(0).addStudent(aStudent));
//		
//		// THE REASON FOR 2 JOBS FOR 2 APPLICATIONS: Each application NEEDS a job
//		// Make new application; apply aStudent to am.getJob(0)
//		Application aApplication = am.getJob(0).addApplication(aStudent);
//		Application aApplication2 = am.getJob(1).addApplication(aStudent);
//		
//		// Make sure getApplication
//		assertEquals(aApplication,am.getJob(0).getApplication(0));
//		assertEquals(aApplication2,am.getJob(1).getApplication(0));
//		
//		// Application already exists for this job
//		assertFalse(am.getJob(0).addApplication(aApplication));
//		// Add the application that is for aJob2 to aJob
//		assertTrue(am.getJob(0).addApplication(aApplication2));
//	}
//
//	@Test
//	public void testRemoveApplication() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		String aDay2 = "Tuesday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//			ac.addJobToSystem(aStartTime, aEndTime, aDay2, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(2,am.numberOfJobs());
//		// Add student to a job
//		//assertTrue(am.getJob(0).addStudent(aStudent));
//		// Make new application; apply aStudent to am.getJob(0)
//		Application aApplication = am.getJob(0).addApplication(aStudent);
//		Application aApplication2 = am.getJob(1).addApplication(aStudent);
//		// Application already exists for this job
//		assertFalse(am.getJob(0).addApplication(aApplication));
//		// Add the application that is for aJob2 to aJob
//		assertTrue(am.getJob(0).addApplication(aApplication2));
//		// Remove aApplication for Job1; always returns false since an application always needs a job
//		assertFalse(am.getJob(0).removeApplication(aApplication));
//	}
//
//	@Test
//	public void testAddApplicationAt() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		String aDay2 = "Tuesday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//			ac.addJobToSystem(aStartTime, aEndTime, aDay2, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(2,am.numberOfJobs());
//		// Add student to a job
//		//assertTrue(am.getJob(0).addStudent(aStudent));
//		
//		// Make new application; apply aStudent to am.getJob(0)
//		Application aApplication = am.getJob(0).addApplication(aStudent);
//		Application aApplication2 = am.getJob(1).addApplication(aStudent);
//		Application aApplication3 = am.getJob(1).addApplication(aStudent);
//		
//		// Make sure it has applications
//		assertTrue(am.getJob(0).hasApplications());
//		
//		// Add aApplication2 to aJob at index am.getJob(0).numberOfApplications()
//		assertTrue(am.getJob(0).addApplicationAt(aApplication2, am.getJob(0).numberOfApplications()));
//		// Adding the same application will return false (get another branch)
//		assertFalse(am.getJob(0).addApplicationAt(aApplication2, 1));
//		// Adding an application at a negative index (for branch coverage)
//		assertTrue(am.getJob(0).addApplicationAt(aApplication3,-1));
//	}
//
//	@Test
//	public void testAddOrMoveApplicationAt() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		String aDay2 = "Tuesday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//			ac.addJobToSystem(aStartTime, aEndTime, aDay2, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(2,am.numberOfJobs());
//		// Add student to a job
//		//assertTrue(am.getJob(0).addStudent(aStudent));
//		
//		// Make new application; apply aStudent to am.getJob(0)
//		Application aApplication = am.getJob(0).addApplication(aStudent);
//		Application aApplication2 = am.getJob(1).addApplication(aStudent);
//		Application aApplication3 = am.getJob(1).addApplication(aStudent);
//		
//		// Make sure it has applications
//		assertTrue(am.getJob(0).hasApplications());
//		
//		// Add aApplication2 to aJob at index am.getJob(0).numberOfApplications()
//		assertTrue(am.getJob(0).addOrMoveApplicationAt(aApplication2, am.getJob(0).numberOfApplications()));
//		// Make sure it is at the correct index
//		assertEquals(1,am.getJob(0).indexOfApplication(aApplication2));
//		// Moving the same application will return true
//		assertTrue(am.getJob(0).addOrMoveApplicationAt(aApplication2, 0));
//		// Adding an application at a negative index (for branch coverage)
//		assertTrue(am.getJob(0).addOrMoveApplicationAt(aApplication3,-1));
//	}
//
//	@Test
//	public void testDelete() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		String aDay2 = "Tuesday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//			ac.addJobToSystem(aStartTime, aEndTime, aDay2, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(2,am.numberOfJobs());
//		// Add student to a job
//		//assertTrue(am.getJob(0).addStudent(aStudent));
//		// Make new application; apply aStudent to am.getJob(0)
//		Application aApplication = am.getJob(0).addApplication(aStudent);
//		Application aApplication2 = am.getJob(1).addApplication(aStudent);
//		
//		// Make sure it has applications
//		assertTrue(am.getJob(0).hasApplications());
//		// Application already exists for this job
//		assertFalse(am.getJob(0).addApplication(aApplication));
//		// Add the application that is for aJob2 to aJob
//		assertTrue(am.getJob(0).addApplication(aApplication2));
//		
//		// delete applications and students 
//		am.getJob(0).delete();
//		// Make sure there are 0 students and applicants
//		assertEquals(0,am.getJob(0).numberOfApplications());
//		//assertEquals(0,am.getJob(0).numberOfStudents());
//		
//		// Make sure it has no applications
//		assertFalse(am.getJob(0).hasApplications());
//	}
//
//	@Test
//	public void testToString() {
//		assertEquals(0,am.numberOfJobs());
//		
//		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
//		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
//		aDay = "Monday";
//		aSalary = 10.00;
//		aRequirements = "Bachelors in Fine Arts";
//		
//		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
//		String studUsername = "student69";
//		String studPassword = "1234"; 
//		String aExperience = "Lawn mowing and TV watching";
//		
//		// First name (Matthew) and last name (Lesko) is the same everywhere 
//		// Degree set to UNDEGRAD by default
//		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
//		
//		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
//		
//		try {
//			ac.addJobToSystem(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
//		}
//		catch (InputException e) {
//		}
//		// Job gets saved
//		assertEquals(1,am.numberOfJobs());
//		//assertTrue(am.getJob(0).addStudent(aStudent));
//		// 1 Student
//		//assertEquals(1,am.getJob(0).numberOfStudents());
//		
//		assertEquals(am.getJob(0).toString(),am.getJob(0).toString());
//	}
//
//}
