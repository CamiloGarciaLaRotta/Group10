package ca.mcgill.ecse321.group10.TAMAS.model;

import static org.junit.Assert.*;

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

public class TestApplication {
	
	private ApplicationManager am;
	private ApplicationController ac;
	
	//Initialise valid student profile
		private String studentUsername = "GiGiBuffo", studentPassword = "calcio", studentFirstName="Gianluigi", studentLastName = "Buffon", experience = "Being a badass";
		private Student aStudent = new Student(studentUsername, studentPassword, studentFirstName, studentLastName,experience);
	//Initialise valid Course
		private String aClassName = "FACC100";
		private int aCdn = 666;
		private float aGraderTimeBudget = (float) 10.00, aTaTimeBudget = (float) 10.00;
		private Course aCourse = new Course(aClassName,aCdn,aGraderTimeBudget,aTaTimeBudget);
	
	//initialise valid Instructor
	private String profUsername = "DMastropietro", profPassword = "casalciprano", profFirstName = "Domenico", profLastName = "Mastropietro";
	private Instructor aInstructor = new Instructor(profUsername, profPassword, profFirstName, profLastName);

	
	//initialise valid job
	private Time aStartTime =  (Time.valueOf("8:00:00")), aEndTime =  (Time.valueOf("10:00:00"));
	private String aDay = "Wednesday";
	private double aSalary = 5.75;
	private String aRequirements = "None";
	private Job aJob = new Job(aStartTime,aEndTime,aDay,aSalary, aRequirements, aCourse, aInstructor);
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeApplicationManager(ApplicationController.APPLICATION_FILE_NAME,ProfileController.PROFILE_FILE_NAME);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
//		am = new ApplicationManager();
		am = PersistenceXStream.initializeApplicationManager(ApplicationController.APPLICATION_FILE_NAME,ProfileController.PROFILE_FILE_NAME);
		ApplicationController ac = new ApplicationController(am, ApplicationController.APPLICATION_FILE_NAME);

	}

	@After
	public void tearDown() throws Exception {
		am.delete();
	}

	@Test
	public void testApplicationValidJobAndStudent() {
		
		ApplicationController ac = new ApplicationController(am, ApplicationController.APPLICATION_FILE_NAME);
		assertEquals(0, am.numberOfApplications());
		
		try{
			ac.createApplication(aStudent, aJob);
		}
		catch(Exception e){
			System.out.println("WTF");
		}
		finally {
			assertEquals(1, am.getApplications().size());
		}
		
		
		//assert ID
		
		//assert student
		
		//assert job
		
		
	}
	
	@Test
	public void testApplicationNoStudent(){
		
		assertEquals(0, am.numberOfApplications());
		
		try{
			ac.createApplication(null,aJob);
		}
		catch(Exception e){
		}
		
		assertEquals(0, am.numberOfApplications());
		
	}
	
	
	@Test
	public void testApplicationNoJob(){
		assertEquals(0, am.numberOfApplications());
		
		try{
			ac.createApplication(aStudent,null);
		}
		catch(Exception e){
		}
		
		assertEquals(0, am.numberOfApplications());		
	}
	
	@Test
	public void testDeleteApplication(){

	}
	
	@Test
	public void testToString(){
		
	}
	
	@Test
	public void testSetStudent(){
		
	}
	
	@Test
	public void testSetJob(){
		
	}
}
