package ca.mcgill.ecse321.group10.TAMAS.model;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class TestProfile {
	  private String username;
	  private String password;
	  private String firstName;
	  private String lastName;
	  private int id;
	  private ProfileManager pm;

	  Job j,j2;
	  
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//PersistenceXStream.initializeProfileManager("testprofiles.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		username = "randinator";
		password = "tpb";
		firstName = "Randy";
		lastName = "Bobandy";
		j = new Job(100.0f, "Monday", 10.0, "", new Course("Trailer Park Supervision",1,1000,1000,1000), new Instructor("theliquor","ricky","Jim","Lahey"));
		j2 = new Job(50.0f, "Tuesday", 10.0, "", new Course("Fixing Lawnmowers",1,1000,1000,1000), new Instructor("theliquor","ricky","Jim","Lahey"));
	}

	@After
	public void tearDown() throws Exception {
		pm.delete();
		PersistenceXStream.setFilename("testprofiles.xml");
		PersistenceXStream.saveToXMLwithXStream(pm);
	}

	@Test
	public void testProfileWithAdmin() {
		assertEquals(0,pm.numberOfAdmins());
		
		username = "mattyboy";
		password = "1234";
		firstName = "Matthew";
		lastName = "Lesko";
		id = 101;
		
		ProfileController pc = new ProfileController(pm, "testprofiles.xml");
		
		try {
			pc.addAdminToSystem(username, password, firstName, lastName);
		}
		catch (InputException e) {
			
		}
		// Profile Saved as Admin successfully
		assertEquals(1,pm.numberOfAdmins());
		
		// Setter Methods
		assertTrue(pm.getAdmin(0).setUsername(username));
		assertTrue(pm.getAdmin(0).setPassword(password));
		assertTrue(pm.getAdmin(0).setFirstName(firstName));
		assertTrue(pm.getAdmin(0).setLastName(lastName));
		
		// Getter methods
		assertEquals(username,pm.getAdmin(0).getUsername());
		assertEquals(password,pm.getAdmin(0).getPassword());
		assertEquals(firstName,pm.getAdmin(0).getFirstName());
		assertEquals(lastName,pm.getAdmin(0).getLastName());
		
		// id is autounique
		boolean isInt = false; 
		if ( pm.getAdmin(0).getId() >= 0 || pm.getAdmin(0).getId() < 0) isInt = true;
		assertTrue(isInt);
		
		// to string works
		assertEquals(pm.getAdmin(0).toString(),pm.getAdmin(0).toString());
		
		try {
			pc.addAdminToSystem(username, password, firstName, lastName);
		}
		catch (InputException e) {
			
		}
		// Profile Saved as Admin successfully
		assertEquals(1,pm.numberOfAdmins());
	}
	
	@Test
	public void testUsernameFree() {
		assertEquals(0,pm.getStudents().size());
		ProfileController pc = new ProfileController(pm, "testprofiles.xml");

		try {
			pc.addStudentToSystem("randinator",password,firstName,lastName,"");
			pc.addInstructorToSystem("microphoneassassin",password,firstName,lastName);
			pc.addAdminToSystem("sexian",password,firstName,lastName);
		} catch (Exception e) {
			
		}
		
		assertEquals(3,pm.getStudents().size() + pm.getInstructors().size() + pm.getAdmins().size());
		
		try {
			pc.addInstructorToSystem("randinator","hhfdsf","sdad","sdasd");
		}catch(Exception e) {}
		assertEquals(1,pm.getInstructors().size());
		try {
			pc.addInstructorToSystem("microphoneassassin","hhfdsf","sdad","sdasd");
		}catch(Exception e) {}
		assertEquals(1,pm.getInstructors().size());
		try {
			pc.addInstructorToSystem("sexian","hhfdsf","sdad","sdasd");
		}catch(Exception e) {}
		assertEquals(1,pm.getInstructors().size());
		try {
			pc.addInstructorToSystem("conky","hhfdsf","sdad","sdasd");
		}catch(Exception e) {}
		assertEquals(2,pm.getInstructors().size());
		try {
			pc.addStudentToSystem("conky","hhfdsf","sdad","sdasd","");
		}catch(Exception e) {}
		assertEquals(1,pm.getStudents().size());
	}
	
	@Test
	public void testAddCourseToInstructor() {
		ProfileController pc = new ProfileController(pm, "testprofiles.xml");
		Course course = new Course("Trailer Park Supervision",1,1000,1000,1000);
		try {
			pc.addInstructorToSystem("randinator", "sdfsdf", "sdfjkfsd", "dlsdfl");
		}catch(Exception e) {}
		assertEquals(1,pm.getInstructors().size());
		assertEquals(0,pm.getInstructor(0).getCourses().size());
		pc.addCourseToInstructor(0, course);
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		assertEquals(1,pm.getInstructor(0).getCourses().size());
		assertEquals(true,pm.getInstructor(0).getCourse(0).getClassName().equals(course.getClassName()));
	}
	
	@Test
	public void testModifyProfiles() {
		//Modify Student
		ProfileController pc = new ProfileController(pm, "testprofiles.xml");
		try {
			pc.addStudentToSystem("randinator", "243243", "sgdfg", "sdfhsjkdf", "");
			pc.addInstructorToSystem("microphoneassassin", "123fsd", "sdfdfkldj", "dfjkl");
			pc.addAdminToSystem("sexian", "123fsd", "baba", "dfjkl");
		} catch(Exception e) {}
		assertEquals(3,pm.getStudents().size() + pm.getInstructors().size() + pm.getAdmins().size());
		
		try {
			pc.modifyStudent("randinator", null, null, null, null, null);
		} catch(Exception ex) {}
		try {
			pc.modifyInstructor("microphoneassassin", "", "", "");
		} catch(Exception ex) {}
		try {
			pc.modifyAdmin("sexian", "","","");
		} catch(Exception ex) {}
		assertEquals(true,pm.getStudent(0).getPassword().equals("243243"));
		assertEquals(true,pm.getInstructor(0).getPassword().equals("123fsd"));
		assertEquals(true,pm.getAdmin(0).getPassword().equals("123fsd"));
		
		try {
			pc.modifyStudent("randinator", "bobandy", "asdsad", "asdasd", "asdasd", Student.Degree.UNDERGRAD);
		} catch(Exception ex) {}
		try {
			pc.modifyInstructor("microphoneassassin", "jroc", "asdasd", "asdasd");
		} catch(Exception ex) {}
		try {
			pc.modifyAdmin("sexian", "joolian","asdasd","asdasd");
		} catch(Exception ex) {}
		assertEquals(true,pm.getStudent(0).getPassword().equals("bobandy"));
		assertEquals(true,pm.getInstructor(0).getPassword().equals("jroc"));
		assertEquals(true,pm.getAdmin(0).getPassword().equals("joolian"));
		
		try {
			pc.modifyStudent("blahblah", "bobandy", "asdsad", "asdasd", "asdasd", Student.Degree.UNDERGRAD);
		} catch(Exception ex) {}
		try {
			pc.modifyInstructor("randinator", "jroc", "asdasd", "asdasd");
		} catch(Exception ex) {}
		try {
			pc.modifyAdmin("randinator", "joolian","asdasd","asdasd");
		} catch(Exception ex) {}
		assertEquals(true,pm.getStudent(0).getPassword().equals("bobandy"));
		assertEquals(true,pm.getInstructor(0).getPassword().equals("jroc"));
		assertEquals(true,pm.getAdmin(0).getPassword().equals("joolian"));
	}
	
	@Test
	public void testValidateProfile() {
		ProfileController pc = new ProfileController(pm, "testprofiles.xml");
		try {
			pc.addInstructorToSystem("randinator", "hello", "Randy", "Bobandy");
		}catch(Exception ex) {}

		try {
			pc.addInstructorToSystem(null, null, null, null);
		} catch(InputException e) {
			assertEquals(true,e.getMessage().equals("Username cannot be empty! Password cannot be empty! First and last names cannot be empty! "));
		}
		
		try {
			pc.addInstructorToSystem("rand", null, null, null);
		} catch(InputException e) {
			assertEquals(true,e.getMessage().equals("Password cannot be empty! First and last names cannot be empty! "));
		}
		try {
			pc.addInstructorToSystem("rand", "cheeseburger", "", "");
		} catch(InputException e) {
			assertEquals(true,e.getMessage().equals("First and last names cannot be empty! "));
		}
		try {
			pc.addInstructorToSystem("rand", "cheeseburger", "Randy", "");
		} catch(InputException e) {
			assertEquals(true,e.getMessage().equals("First and last names cannot be empty! "));
		}
		try {
			pc.addInstructorToSystem("", "cheeseburger", "", "Bobandy");
		} catch(InputException e) {
			assertEquals(true,e.getMessage().equals("Username cannot be empty! First and last names cannot be empty! "));
		}
		try {
			pc.addInstructorToSystem("", "", "Randy", null);
		} catch(InputException e) {
			assertEquals(true,e.getMessage().equals("Username cannot be empty! Password cannot be empty! First and last names cannot be empty! "));
		}
		try {
			pc.addInstructorToSystem("", "", null, "Bobandy");
		} catch(InputException e) {
			assertEquals(true,e.getMessage().equals("Username cannot be empty! Password cannot be empty! First and last names cannot be empty! "));
		}
	}

	
	@Test
	public void testAddAndRemoveJobOffer() {
		ProfileController pc = new ProfileController(pm, "testprofiles.xml");
		try {
			pc.addStudentToSystem("randinator", "tpb", "Randy", "Bobandy", "");
		} catch(Exception e) {}
		assertEquals(0,pm.getStudent(0).getJobs().size());
		pc.offerJobToStudent(new Student("sexian","tpb","Julian","Swayze",""),j);
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		assertEquals(0,pm.getStudent(0).getJobs().size());
		pc.offerJobToStudent(pm.getStudent(0), j);
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		assertEquals(1,pm.getStudent(0).getJobs().size());
		pc.removeJobOfferFromStudent(new Student("sexian","tpb","Julian","Swayze",""),j);
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		assertEquals(1,pm.getStudent(0).getJobs().size());
		pc.removeJobOfferFromStudent(pm.getStudent(0), j2);
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		assertEquals(1,pm.getStudent(0).getJobs().size());
		pc.removeJobOfferFromStudent(pm.getStudent(0), j);
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		assertEquals(0,pm.getStudent(0).getJobs().size());
	}
	
	@Test
	public void testAcceptJob() {
		ProfileController pc = new ProfileController(pm, "testprofiles.xml");
		try {
			pc.addStudentToSystem("randinator", "tpb", "Randy", "Bobandy", "");
		} catch(Exception e) {}
		assertEquals(true,pm.getStudent(0).getHoursLeft() == 180.0f);
		try {
			pc.acceptJob(new Student("bandy", "asdadas","asdsd","sdasd","sd"),j);
		}catch(Exception ex) {}
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		assertEquals(true,pm.getStudent(0).getHoursLeft() == 180.0f);
		try {
			pc.acceptJob(pm.getStudent(0),j);
		}catch(Exception ex) {}
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		assertEquals(true,pm.getStudent(0).getHoursLeft() == 80.0f);
		try {
			pc.acceptJob(pm.getStudent(0),j2);
		}catch(Exception ex) {}
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		assertEquals(true,pm.getStudent(0).getHoursLeft() == 30.0f);
		try {
			pc.acceptJob(pm.getStudent(0),j2);
		}catch(Exception ex) {}
		pm = PersistenceXStream.initializeProfileManager("testprofiles.xml");
		assertEquals(true,pm.getStudent(0).getHoursLeft() == 30.0f);
	}

}
