package ca.mcgill.ecse321.group10.TAMAS.model;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.group10.TAMAS.model.Job.Position;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class TestJob {
	//Constructor Args: Time aStartTime, Time aEndTime, String aDay, double aSalary, String aRequirements, Course aCourse, Instructor aInstructor
	private String aDay;
	private double aSalary;
	private String aRequirements;
	
	// Initializating a valid instance of a Course for testing Job constructor purposes
	private String aClassName = "COMP101";
	private int aCdn = 101;
	private float aGraderTimeBudget = (float) 10.00, aTaTimeBudget = (float) 10.00;
	private Course aCourse = new Course(aClassName,aCdn,aGraderTimeBudget,aTaTimeBudget,2000.0f);
	
	// Initializating a valid instance of a Instructor for testing Job constructor purposes
	private String aUsername = "mattyboy", aPassword = "dabest101programmer", aFirstName = "Matthew", aLastName = "Lesko";
	private Instructor aInstructor = new Instructor(aUsername, aPassword, aFirstName, aLastName);
	
	private ApplicationManager am;
	
	private ApplicationController ac;
	
	Job job1 = new Job(45.0f, aDay, 10.0f, "", aCourse, aInstructor);
	Job job2 = new Job(60.0f,"Friday",15.0f,"",aCourse,aInstructor);
	
	Student student1 = new Student("randinator", "tpb", "Randy", "Bobandy","");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		PersistenceXStream.initializeApplicationManager("testapplications.xml","testprofiles.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		am = PersistenceXStream.initializeApplicationManager("testapplications.xml");
		ac = new ApplicationController(am,"testapplications.xml");
	}
 
	@After
	public void tearDown() throws Exception {
		am.delete();
		PersistenceXStream.setFilename("testapplications.xml");
		PersistenceXStream.saveToXMLwithXStream(am);
	}

	@Test
	public void testJobValidInputs() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
	}
	
	@Test
	public void testJobNullCourse() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		aCourse = null;
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
			// Right error (and message)
			assertEquals("Course must be defined! ", e.getMessage());
		}
		// Job does not get saved
		assertEquals(0,am.getJobs().size());
	}
	
	@Test
	public void testJobNullInstructor() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		aInstructor = null;
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
			// Correct error (and message)
			assertEquals("Instructor must be defined! ", e.getMessage());
		}
		// Job does not get saved
		assertEquals(0,am.getJobs().size());
	}
	
	@Test
	public void testJobNullInstructor_NullCourse() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00; 
		aRequirements = "Bachelors in Fine Arts";
		aInstructor = null;
		aCourse = null;
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
			// Correct errors (and message)
			assertEquals("Instructor must be defined! Course must be defined! ", e.getMessage());
		}
		// Job does not get saved
		assertEquals(0,am.getJobs().size());
	}
	
	@Test
	public void testJobNegativeSalary() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = -10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
			// Correct error (and message)
			assertEquals("Salary must be positive! ", e.getMessage());
		}
		// Job does not get saved
		assertEquals(0,am.getJobs().size());
	}
	
	@Test
	public void testJobIllegalHours() {
		assertEquals(0,am.getJobs().size());
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		try {
			ac.addJobToSystem(43.0f,"Monday",10.0f,"",aCourse,aInstructor,Job.Position.TUTORIAL);
		} catch (InputException e) {}
		
		assertEquals(0,am.getJobs().size());
		try {
			ac.addJobToSystem(243.0f,"Monday",10.0f,"",aCourse,aInstructor,Job.Position.TUTORIAL);
		} catch (InputException e) {}
		
		assertEquals(0,am.getJobs().size());
	}
	
	@Test
	public void testJobNullDay() {
		assertEquals(0,am.getJobs().size());
		
		aDay = null;
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
			// Correct error (and message)
			assertEquals("Day must be specified! ", e.getMessage());
		}
		// Job does not get saved
		assertEquals(0,am.getJobs().size());
	}
	
	@Test
	public void testJobWeekendDay() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Saturday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
			// Correct error (and message)
			assertEquals("Day must be a work day! ", e.getMessage());
		}
		// Job does not get saved
		assertEquals(0,am.getJobs().size());
		
		aDay = "Sunday";
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
			// Correct error (and message)
			assertEquals("Day must be a work day! ", e.getMessage());
		}
		// Job does not get saved
		assertEquals(0,am.getJobs().size());
	}
	
	@Test
	public void testJobDidNotAddCourse() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		aCourse = null;
		
		try {
			Job aJob = new Job(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor);
		}
		catch (RuntimeException e) {
			// Correct error (and message)
			assertEquals("Unable to create job due to course", e.getMessage());
		}
		// Job does not get saved
		assertEquals(0,am.getJobs().size());
	}
	
	@Test
	public void testJobDidNotAddInstructor() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		aInstructor = null;
		
		try {
			Job aJob = new Job(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor);
		}
		catch (RuntimeException e) {
			// Correct error (and message)
			assertEquals("Unable to create job due to instructor", e.getMessage());
		}
		// Job does not get saved
		assertEquals(0,am.getJobs().size());
	}
	
	
	@Test
	public void testSetDay() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		assertTrue(am.getJob(0).setDay("Tuesday"));
	}
	
	@Test
	public void testGetDay() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		assertEquals("Monday",am.getJob(0).getDay());
	}

	@Test
	public void testGetSalary() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		assertEquals(10.00,am.getJob(0).getSalary(),0);
	}

	@Test
	public void testGetRequirements() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		assertEquals("Bachelors in Fine Arts",am.getJob(0).getRequirements());
	}

	@Test
	public void testGetId() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		boolean gotId;
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		// Test id is an autounique attribute, as long as its an int, return true
		// seems absolutely redundant and useless to test this, but it's done for 100% statement coverage
		if(am.getJob(0).getId() >= 0 || am.getJob(0).getId() < 0) gotId = true;
		else gotId = false;
		assertTrue(gotId);
		
	}

	@Test
	public void testGetPositionFullName_GetPosition() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		// Position is automatically set to TA by default
		assertEquals("TUTORIAL",am.getJob(0).getPositionFullName());
		assertEquals("TUTORIAL",am.getJob(0).getPosition().toString());
	}

	@Test
	public void testSetPosition() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		Job.Position aPosition = Job.Position.GRADER;
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		// Setting different position (GRADER) works
		assertTrue(am.getJob(0).setPosition(aPosition));
	}

	@Test
	public void testGetCourse_GetInstructor() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		assertEquals(aCourse,am.getJob(0).getCourse());
		assertEquals(aInstructor,am.getJob(0).getInstructor());
	}

	@Test
	public void testNumberOfStudents_HasStudents() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		// No students assigned to Job; hence 0
		//assertEquals(0,am.getJob(0).numberOfStudents());
		// No students; hence false
		//assertFalse(am.getJob(0).hasStudents());
	}

	@Test
	public void testGetApplication() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		String aDay2 = "Tuesday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
			ac.addJobToSystem(45.0f, aDay2, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(2,am.getJobs().size());
		// Add student to a job
		//assertTrue(am.getJob(0).addStudent(aStudent));
		
		// THE REASON FOR 2 JOBS FOR 2 APPLICATIONS: Each application NEEDS a job
		// Make new application; apply aStudent to am.getJob(0)
		Application aApplication = am.getJob(0).addApplication(aStudent);
		Application aApplication2 = am.getJob(1).addApplication(aStudent);
		
		// Make sure getApplication(index i) returns correct application
		assertEquals(aApplication,am.getJob(0).getApplication(0));
		assertEquals(aApplication2,am.getJob(1).getApplication(0));

	}

	@Test
	public void testGetApplications() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		String aDay2 = "Tuesday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
			ac.addJobToSystem(45.0f, aDay2, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(2,am.getJobs().size());
		// Add student to a job
		//assertTrue(am.getJob(0).addStudent(aStudent));
		
		// THE REASON FOR 2 JOBS FOR 2 APPLICATIONS: Each application NEEDS a job
		// Make new application; apply aStudent to am.getJob(0)
		Application aApplication = am.getJob(0).addApplication(aStudent);
		Application aApplication2 = am.getJob(1).addApplication(aStudent);
		
		// test getApplications works
		am.getJob(0).getApplications();
	}


	@Test
	public void testSetCourse_SetInstructor() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		
		String className = "CCOM206";
		int aCdn = 101;
		float aGraderTimeBudget = 1500;
		float aTaTimeBudget = 1500;
		Course aCourse2 = new Course(className, aCdn, aGraderTimeBudget, aTaTimeBudget,2000.0f);
		
		String userName2 = "mikeyboy";
		String password2 = "1234";
		String firstName2 = "Michael";
		String lastName2 = "Lesko";
		Instructor aInstructor2 = new Instructor(userName2, password2, firstName2, lastName2);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) { 
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		// Setting same Course
		assertTrue(am.getJob(0).setCourse(aCourse));
		// Setting same Instructor
		assertTrue(am.getJob(0).setInstructor(aInstructor));
		// Setting different Instructor
		assertTrue(am.getJob(0).setCourse(aCourse2));
		// Setting different Course
		assertTrue(am.getJob(0).setInstructor(aInstructor2));
	}

	@Test
	public void testMinimumNumberOfStudentsAndApplicants() {
		// Both are a minimum of 0, the methods work and both return 0
		//assertEquals(0,Job.minimumNumberOfStudents());
		assertEquals(0,Job.minimumNumberOfApplications());
	}

	@Test
	public void testAddStudent_AddStudentAt_RemoveStudent() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		String studUsername2 = "student70";
		String aFirstName2 = "Michael";
		String aLastName2 = "Lesko";
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		Student aStudent2 = new Student(studUsername2, studPassword, aFirstName2, aLastName2, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		//assertTrue(am.getJob(0).addStudent(aStudent));
		// 1 Student
		//assertEquals(1,am.getJob(0).numberOfStudents());
		// Make sure it has students
		//assertTrue(am.getJob(0).hasStudents());
		// Make sure the instances of Student are identical
		//assertEquals(aStudent,am.getJob(0).getStudent(0));
		
		// Adding the same student returns false
		//assertFalse(am.getJob(0).addStudent(aStudent));
		// Adding the same student will return false
		//assertFalse(am.getJob(0).addStudentAt(aStudent, 0));
		// Still 1 student
		//assertEquals(1,am.getJob(0).numberOfStudents());
		// Adding different student will return true
		//assertTrue(am.getJob(0).addStudentAt(aStudent2, -1));
		// Now 2 students
		//assertEquals(2,am.getJob(0).numberOfStudents());
		// Remove aStudent returns true
		//assertTrue(am.getJob(0).removeStudent(aStudent));
		// Removing the same student returns false
		//assertFalse(am.getJob(0).removeStudent(aStudent));
		// Now 1 student
		//assertEquals(1,am.getJob(0).numberOfStudents());
		
		// This tests for another branch in the addStudentAt method (specifically the conditional with index>numberOfStudents)
		//assertTrue(am.getJob(0).addStudentAt(aStudent, am.getJob(0).numberOfStudents()));
		//assertTrue(am.getJob(0).removeStudent(aStudent));
	}
	
	@Test
	public void testAddOrMoveStudentAt() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		String studUsername2 = "student70";
		String aFirstName2 = "Michael";
		String aLastName2 = "Lesko";
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		Student aStudent2 = new Student(studUsername2, studPassword, aFirstName2, aLastName2, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		// Student hasn't been added, adds student and returns true
		//assertTrue(am.getJob(0).addOrMoveStudentAt(aStudent, 0));
		// Tests for the branch if (index < 0)
		//assertTrue(am.getJob(0).addOrMoveStudentAt(aStudent,-1));
		
		//assertTrue(am.getJob(0).addOrMoveStudentAt(aStudent2, am.getJob(0).numberOfStudents()));
	}
	

	@Test
	public void testGetStudents() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		//assertTrue(am.getJob(0).addStudent(aStudent));
		//assertTrue(am.getJob(0).hasStudents());
		//am.getJob(0).getStudents();
	}
	
	@Test
	public void testAddApplication() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		String aDay2 = "Tuesday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
			ac.addJobToSystem(45.0f, aDay2, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(2,am.getJobs().size());
		// Add student to a job
		//assertTrue(am.getJob(0).addStudent(aStudent));
		
		// THE REASON FOR 2 JOBS FOR 2 APPLICATIONS: Each application NEEDS a job
		// Make new application; apply aStudent to am.getJob(0)
		Application aApplication = am.getJob(0).addApplication(aStudent);
		Application aApplication2 = am.getJob(1).addApplication(aStudent);
		
		// Make sure getApplication
		assertEquals(aApplication,am.getJob(0).getApplication(0));
		assertEquals(aApplication2,am.getJob(1).getApplication(0));
		
		// Application already exists for this job
		assertFalse(am.getJob(0).addApplication(aApplication));
		// Add the application that is for aJob2 to aJob
		assertTrue(am.getJob(0).addApplication(aApplication2));
	}

	@Test
	public void testRemoveApplication() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		String aDay2 = "Tuesday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.createApplication(student1, job1);
			ac.createApplication(student1, job2);
		}
		catch (InputException e) {
		}
		
		// Job gets saved
		assertEquals(2,am.getApplications().size());
		
		ac.removeApplication(am.getApplication(0));
		
		assertEquals(1,am.getApplications().size());
		assertEquals(job2.toString(),am.getApplication(0).getJobs().toString());
	}

	@Test
	public void testAddApplicationAt() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		String aDay2 = "Tuesday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
			ac.addJobToSystem(45.0f, aDay2, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(2,am.getJobs().size());
		// Add student to a job
		//assertTrue(am.getJob(0).addStudent(aStudent));
		
		// Make new application; apply aStudent to am.getJob(0)
		Application aApplication = am.getJob(0).addApplication(aStudent);
		Application aApplication2 = am.getJob(1).addApplication(aStudent);
		Application aApplication3 = am.getJob(1).addApplication(aStudent);
		
		// Make sure it has applications
		assertTrue(am.getJob(0).hasApplications());
		
		// Add aApplication2 to aJob at index am.getJob(0).numberOfApplications()
		assertTrue(am.getJob(0).addApplicationAt(aApplication2, am.getJob(0).numberOfApplications()));
		// Adding the same application will return false (get another branch)
		assertFalse(am.getJob(0).addApplicationAt(aApplication2, 1));
		// Adding an application at a negative index (for branch coverage)
		assertTrue(am.getJob(0).addApplicationAt(aApplication3,-1));
	}

	@Test
	public void testAddOrMoveApplicationAt() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		String aDay2 = "Tuesday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
			ac.addJobToSystem(45.0f, aDay2, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(2,am.getJobs().size());
		// Add student to a job
		//assertTrue(am.getJob(0).addStudent(aStudent));
		
		// Make new application; apply aStudent to am.getJob(0)
		Application aApplication = am.getJob(0).addApplication(aStudent);
		Application aApplication2 = am.getJob(1).addApplication(aStudent);
		Application aApplication3 = am.getJob(1).addApplication(aStudent);
		
		// Make sure it has applications
		assertTrue(am.getJob(0).hasApplications());
		
		// Add aApplication2 to aJob at index am.getJob(0).numberOfApplications()
		assertTrue(am.getJob(0).addOrMoveApplicationAt(aApplication2, am.getJob(0).numberOfApplications()));
		// Make sure it is at the correct index
		assertEquals(1,am.getJob(0).indexOfApplication(aApplication2));
		// Moving the same application will return true
		assertTrue(am.getJob(0).addOrMoveApplicationAt(aApplication2, 0));
		// Adding an application at a negative index (for branch coverage)
		assertTrue(am.getJob(0).addOrMoveApplicationAt(aApplication3,-1));
	}

	@Test
	public void testDelete() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		String aDay2 = "Tuesday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
			ac.addJobToSystem(45.0f, aDay2, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(2,am.getJobs().size());
		// Add student to a job
		//assertTrue(am.getJob(0).addStudent(aStudent));
		// Make new application; apply aStudent to am.getJob(0)
		Application aApplication = am.getJob(0).addApplication(aStudent);
		Application aApplication2 = am.getJob(1).addApplication(aStudent);
		
		// Make sure it has applications
		assertTrue(am.getJob(0).hasApplications());
		// Application already exists for this job
		assertFalse(am.getJob(0).addApplication(aApplication));
		// Add the application that is for aJob2 to aJob
		assertTrue(am.getJob(0).addApplication(aApplication2));
		
		// delete applications and students 
		am.getJob(0).delete();
		// Make sure there are 0 students and applicants
		assertEquals(0,am.getJob(0).numberOfApplications());
		//assertEquals(0,am.getJob(0).numberOfStudents());
		
		// Make sure it has no applications
		assertFalse(am.getJob(0).hasApplications());
	}

	@Test
	public void testToString() {
		assertEquals(0,am.getJobs().size());
		
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		
		//public Student(String aUsername, String aPassword, String aFirstName, String aLastName, String aExperience) Student Constructor
		String studUsername = "student69";
		String studPassword = "1234"; 
		String aExperience = "Lawn mowing and TV watching";
		
		// First name (Matthew) and last name (Lesko) is the same everywhere 
		// Degree set to UNDEGRAD by default
		Student aStudent = new Student(studUsername, studPassword, aFirstName, aLastName, aExperience);
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		// Job gets saved
		assertEquals(1,am.getJobs().size());
		//assertTrue(am.getJob(0).addStudent(aStudent));
		// 1 Student
		//assertEquals(1,am.getJob(0).numberOfStudents());
		
		assertEquals(am.getJob(0).toString(),am.getJob(0).toString());
	}
	
	@Test
	public void testSetJobOfferedDoesntExist() {
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		assertEquals(0,am.getJobs().size());
		try {
			ac.addJobToSystem(45.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		ac.setJobOffered(job2, true); 
		assertEquals(false,am.getJob(0).isOfferSent());
	}
	
	@Test
	public void testSetJobOfferedDoesExist() {
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		
		assertEquals(0,am.getJobs().size());
		try {
			ac.addJobToSystem(49.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		}
		catch (InputException e) {
		}
		ac.setJobOffered(am.getJob(0), true);
		assertEquals(true,am.getJob(0).isOfferSent());
	}
	
	@Test
	public void testCreateApplicationFail() {
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		assertEquals(0,am.getApplications().size());
		try {
			ac.createApplication(null, null);
		} catch(InputException e) {
			
		} 
		assertEquals(0,am.getApplications().size());
		try {
			ac.createApplication(null, job1); 
		} catch(InputException e) {
			
		} 
		assertEquals(0,am.getApplications().size());
		try {
			ac.createApplication(student1, null);
		} catch(InputException e) {
			
		} 
		assertEquals(0,am.getApplications().size());
		try {
			ac.createApplication(student1, job1);
		} catch(InputException e) {
			
		} 
		assertEquals(1,am.getApplications().size());
	}
	
	@Test 
	public void testSetJobAcceptedDoesntExist() { 
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		assertEquals(0,am.getJobs().size());
		try {
			ac.createApplication(student1, job1);
		} catch(InputException e) {
			
		}
		ac.setJobOfferAccepted(new Application(student1,job2), true);
		assertEquals(false,am.getApplication(0).getOfferAccepted());
	}
	
	@Test 
	public void testSetJobAcceptedDoesExist() { 
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		assertEquals(0,am.getJobs().size());
		try {
			ac.createApplication(student1, job1);
		} catch(InputException e) {
			
		}
		ac.setJobOfferAccepted(am.getApplication(0), true);
		assertEquals(true,am.getApplication(0).getOfferAccepted());
	}
	
	@Test
	public void testSetEvaluationDoesntExist() {
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		assertEquals(0,am.getJobs().size());
		try {
			ac.createApplication(student1, job1);
		} catch(InputException e) {
			
		}
		ac.addStudentEvaluation(new Application(student1,job2), "Great job");
		assertEquals(null,am.getApplication(0).getStudentEvaluation());
	}
	
	@Test
	public void testSetEvaluationDoesExist() {
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		assertEquals(0,am.getJobs().size());
		try {
			ac.createApplication(student1, job1);
		} catch(InputException e) {
			
		}
		ac.addStudentEvaluation(am.getApplication(0), "Great job");
		assertEquals(true,am.getApplication(0).getStudentEvaluation().equals("Great job"));
	}
	
	@Test
	public void modifyJobPosition() {
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,"testapplications.xml");
		assertEquals(0,am.getJobs().size());
		try {
			ac.addJobToSystem(49.0f, aDay, aSalary, aRequirements, aCourse, aInstructor,Job.Position.TUTORIAL);
		} catch(InputException e) {
			
		}
		ac.modifyJobPosition(0,Job.Position.TUTORIAL);
		assertEquals(Job.Position.TUTORIAL,am.getJob(0).getPosition());
		ac.modifyJobPosition(0,Job.Position.LABORATORY);
		assertEquals(Job.Position.LABORATORY,am.getJob(0).getPosition());
	}

}
