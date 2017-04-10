import static org.junit.Assert.*;

import java.io.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ca.mcgill.ecse321.group10.TAMAS.model.*;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;
import ca.mcgill.ecse321.group10.controller.*;

public class ProfileControllerTest {
	private ProfileController pc;
	private ProfileManager pm;
	private static String outputFile = "testprofiles.xml";
	

	@Before
	public void setUp() throws Exception {
		PersistenceXStream.initializeProfileManager(outputFile);
		pm = new ProfileManager();
		pc = new ProfileController(pm, outputFile);
		
		assertTrue(pm.getAdmins().size() == 0);
		assertTrue(pm.getStudents().size() == 0);
		assertTrue(pm.getInstructors().size() == 0);
	}

	@After
	public void tearDown() throws Exception {
		pm.delete();
		File f = new File(outputFile);
		f.delete();
		assertTrue(pm.getAdmins().size() == 0);
		assertTrue(pm.getInstructors().size() == 0);
		assertTrue(pm.getStudents().size() == 0);
	}

	@Test
	public void testCreateProfileValid() throws InputException {
		
		pc.addInstructorToSystem("Boogie", "Goodman", "Joseph", "Stalin");
		pc.addAdminToSystem("MartinLutherKing", "Was not a man", "Of Tolerance", "But Social Revolution");
		pc.addStudentToSystem("Seize", "The", "Means", "Of", "Production");
		pc.addStudentToSystem("exp", "is", "blank", "test", "");
		
		
		Course course = new Course("history", 1,2,3,4);
		pc.addCourseToInstructor(0, course);
		
		pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
		
		
		assertEquals(pm.getInstructor(0).getFirstName(), "Joseph");
		assertEquals(pm.getAdmin(0).getUsername(), "MartinLutherKing");
		assertEquals(pm.getStudent(0).getPassword(), "The");
		
	}

	@Test
	public void testCreateProfileInvalid(){
		
		try {
			pc.addInstructorToSystem(null, "eh", "guy", "Jones");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(0, pm.getInstructors().size());
		}
		try {
			pc.addInstructorToSystem("", "eh", "guy", "Jones");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(0, pm.getInstructors().size());
		}
		try {
			pc.addInstructorToSystem("man", "", "guy", "Jones");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(0, pm.getInstructors().size());
		}
		try {
			pc.addInstructorToSystem("man", "eh", "", "Jones");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(0, pm.getInstructors().size());
			
		}
		
		try {
			pc.addAdminToSystem(null, "eh", "guy", "Jones");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getAdmins().size() == 0);
		}
		try {
			pc.addAdminToSystem("", "eh", "guy", "Jones");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getAdmins().size() == 0);
		}
		try {
			pc.addAdminToSystem("man", "", "guy", "Jones");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getAdmins().size() == 0);
		}
		try {
			pc.addAdminToSystem("man", "eh", "", "Jones");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getAdmins().size() == 0);
		}
		
		try {
			pc.addStudentToSystem(null, "eh", "guy", "Jones", "abc");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getStudents().size() == 0);
		}
		try {
			pc.addStudentToSystem("", "eh", "guy", "Jones", "abc");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getStudents().size() == 0);
		}
		try {
			pc.addStudentToSystem("man", "", "guy", "Jones", "abc");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getStudents().size() == 0);
		}
		try {
			pc.addStudentToSystem("man", "eh", "", "Jones", "abc");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getStudents().size() == 0);
		}
		

	}
	
	@Test
	public void testUsernameTaken() {
		try {
			pc.addStudentToSystem("user1", "pass","first","last", "exp");
		} catch (InputException e1) {
			// TODO Auto-generated catch block
		}
		
		try{
			pc.addStudentToSystem("user1", "pass","first","last", "exp");
		} catch(Exception e){
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getStudents().size() == 1);
		}
		
		try{
			pc.addInstructorToSystem("user2", "pass","first","last");
		} catch(Exception e){
		}
		
		try{
			pc.addInstructorToSystem("user2", "pass","first","last");
		} catch(Exception e){
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getInstructors().size() == 1);
		}
		try{
			pc.addAdminToSystem("user3", "pass","first","last");
		} catch(Exception e){
		}
		try{
			pc.addAdminToSystem("user3", "pass","first","last");
		} catch(Exception e){
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertTrue(pm.getAdmins().size() == 1);
		}
	}

}
