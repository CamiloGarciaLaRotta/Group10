import static org.junit.Assert.*;

import java.io.File;

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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		pm = new ProfileManager();
		pc = new ProfileController(pm, "output" + File.separator + "testProfileManager");
	}

	@After
	public void tearDown() throws Exception {
		pm.delete();
	}

	@Test
	public void testCreateProfile() {
		Profile testProfile = new Profile("Boogie", "Gulag","Joseph", "Stalin");
	}

}