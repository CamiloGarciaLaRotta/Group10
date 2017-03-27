package ca.mcgill.ecse321.group10.TAMAS.model;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class TestLaboratory {

	//public Laboratory(Time aStartTime, Time aEndTime, String aDay, double aSalary, String aRequirements, Course aCourse, Instructor aInstructor) constructor
	private Time aStartTime, aEndTime;
	private String aDay;
	private double aSalary;
	private String aRequirements;
	
	// Initializating a valid instance of a Course for testing Laboratory constructor purposes
	private String aClassName = "COMP101";
	private int aCdn = 101;
	private float aGraderTimeBudget = (float) 10.00, aTaTimeBudget = (float) 10.00;
	private Course aCourse = new Course(aClassName,aCdn,aGraderTimeBudget,aTaTimeBudget);
	
	// Initializating a valid instance of a Instructor for testing Laboratory constructor purposes
	private String aUsername = "mattyboy", aPassword = "dabest101programmer", aFirstName = "Matthew", aLastName = "Lesko";
	private Instructor aInstructor = new Instructor(aUsername, aPassword, aFirstName, aLastName);
	
	private ApplicationManager am;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeApplicationManager("output"+File.separator+"testApplications.xml","output"+File.separator+"testProfiles.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		am = new ApplicationManager();
	}

	@After
	public void tearDown() throws Exception {
		am.delete();
	}
	
	@Test
	public void testLaboratory() {
		
		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
		
		Laboratory aLaboratory = new Laboratory(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
		// Laboratory successfully added to application manager
		assertTrue(am.addJob(aLaboratory));
	}
	
	@Test
	public void testDelete() {

		aStartTime = (Time.valueOf("10:00:00"));	// 10:00 AM
		aEndTime = (Time.valueOf("11:00:00"));		// 11:00 AM
		aDay = "Monday";
		aSalary = 10.00;
		aRequirements = "Bachelors in Fine Arts";
		ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
		
		Laboratory aLaboratory = new Laboratory(aStartTime, aEndTime, aDay, aSalary, aRequirements, aCourse, aInstructor);
		// Laboratory successfully added to application manager
		assertTrue(am.addJob(aLaboratory));
		aLaboratory.delete();
		
	}
}