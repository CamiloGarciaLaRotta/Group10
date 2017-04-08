import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Course;
import ca.mcgill.ecse321.group10.TAMAS.model.Instructor;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ApplicationControllerTest {
	private ApplicationController ac;
	private static ApplicationManager am;
	private ProfileController pc;
	private static ProfileManager pm;
	private static String outputFile1 = "output" + File.separator + "testApplicationController";
	private static String outputFile2 = "output" + File.separator + "testProfileController";
	private static File f1;
	private static File f2;
	private static Time timeBefore = new Time(10);
	private static Time timeAfter = new Time(100);

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		f1 = new File(outputFile1);
		f2 = new File(outputFile2);
		pm = PersistenceXStream.initializeProfileManager(outputFile2);
		am = PersistenceXStream.initializeApplicationManager(outputFile1, outputFile2);
	}
	

	@Before
	public void setUp() throws Exception {
		f1 = new File(outputFile1);
		f2 = new File(outputFile2);
		am = (ApplicationManager) PersistenceXStream.loadFromXMLwithXStream();
		//am = new ApplicationManager();
		//pm = new ProfileManager();
		ac = new ApplicationController(am, outputFile1);
		assertEquals(0,am.getApplications().size());
	}

	@After
	public void tearDown() throws Exception {
		am = (ApplicationManager)PersistenceXStream.loadFromXMLwithXStream();
		am.delete();
		pm.delete();
		//f1.delete();
		//f2.delete();
		assertEquals(0,am.getApplications().size());
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		f1.delete();
		f2.delete();
	}
	
	// Refactored Job.Position.TA <- Job.Position.TUTORIAL
	// Added aLabBudget input for appropriate parameter
	@Test
	public void testModifyJobAndCreateApplication() throws InputException {
		Course aCourse = new Course("testCourse",1,2,3,4);
		Instructor aInstructor = new Instructor("User","Pass", "first", "Last");
		ac.addJobToSystem(45, "Monday", 1000, "None", aCourse, aInstructor, Job.Position.TUTORIAL);
		
		am = (ApplicationManager) PersistenceXStream.loadFromXMLwithXStream();
		assertEquals(1,am.getJobs().size());
		
		ac.modifyJobPosition(0, Job.Position.TUTORIAL);
		am = (ApplicationManager) PersistenceXStream.loadFromXMLwithXStream();
		assertEquals(Job.Position.TUTORIAL, am.getJob(0).getPosition());
		
		Student aStudent = new Student("user", "pass", "James", "McGill", "exp");
		
		ac.createApplication(aStudent, am.getJob(0));
		am = (ApplicationManager) PersistenceXStream.loadFromXMLwithXStream();
		assertEquals(1,am.getApplications().size());
	}
	
	@Test
	public void testAddJobToSystemInvalid(){
		Course aCourse = new Course("testCourse",1,2,3,4);
		Instructor aInstructor = new Instructor("User","Pass", "first", "Last");
		
		try {
			ac.addJobToSystem(45, "Monday", 1000, "None", null, aInstructor, Job.Position.TUTORIAL);
		} catch (Exception e) {
			am = (ApplicationManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals("Course must be defined! ", e.getMessage());
			assertEquals(0,am.getApplications().size());
		}
		
		try {
			ac.addJobToSystem(45, "Monday", 1000, "None", aCourse, null, Job.Position.TUTORIAL);
		} catch (Exception e) {
			am = (ApplicationManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals("Instructor must be defined! ", e.getMessage());
			assertEquals(0,am.getApplications().size());
		}
		try {
			ac.addJobToSystem(45, "Monday", -1000, "None", aCourse, aInstructor, Job.Position.TUTORIAL);
		} catch (Exception e) {
			am = (ApplicationManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(0,am.getApplications().size());
		}
	}
}

