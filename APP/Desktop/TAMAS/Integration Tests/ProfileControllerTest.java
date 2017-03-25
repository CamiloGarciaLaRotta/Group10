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
	private static String outputFile;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		outputFile = "test" + File.separator + "testProfileController";
		PersistenceXStream.initializeProfileManager(outputFile);
		
	}

	@Before
	public void setUp() throws Exception {
		pm = new ProfileManager();
		pc = new ProfileController(pm, outputFile);
	}

	@After
	public void tearDown() throws Exception {
		pm.delete();
		assert (pm.getAdmins().size() == 0);
		assert (pm.getInstructors().size() == 0);
		assert (pm.getStudents().size() == 0);
	}

	@Test
	public void testCreateProfileValid() throws InputException {
		assert(pm.getAdmins().size() == 0);
		assert(pm.getStudents().size() == 0);
		assert(pm.getInstructors().size() == 0);
		
		pc.addInstructorToSystem("Boogie", "Goodman", "Joseph", "Stalin");
		pc.addAdminToSystem("MartinLutherKing", "Was not a man", "Of Tolerance", "But Social Revolution");
		pc.addStudentToSystem("Seize", "The", "Means", "Of", "Production");
		
		pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
		
		assertEquals(pm.getInstructor(0).getFirstName(), "Joseph");
		assertEquals(pm.getAdmin(0).getUsername(), "MartinLutherKing");
		assertEquals(pm.getStudent(0).getPassword(), "The");
		
	}

	@Test
	public void testCreateProfileInvalid(){
		assert(pm.getAdmins().size() == 0);
		assert(pm.getStudents().size() == 0);
		assert(pm.getInstructors().size() == 0);
		
		try {
			pc.addInstructorToSystem(null, "eh", "guy", "Jones");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(pm.getInstructors().size(), 0);
		}
		
		try {
			pc.addAdminToSystem("", "eh", "guy", "Jones");
		} catch (InputException e) {
			pm = (ProfileManager) PersistenceXStream.loadFromXMLwithXStream();
			assert(pm.getAdmins().size() == 0);
		}
		

	}

}