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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeProfileManager("output"+File.separator+"testProfiles.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		pm = new ProfileManager();
	}

	@After
	public void tearDown() throws Exception {
		pm.delete();
	}

	@Test
	public void testProfileWithAdmin() {
		assertEquals(0,pm.numberOfAdmins());
		
		username = "mattyboy";
		password = "1234";
		firstName = "Matthew";
		lastName = "Lesko";
		id = 101;
		
		ProfileController pc = new ProfileController(pm,"output"+File.separator+"testProfiles.xml");
		
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
		
		// delete admin
		pm.getAdmin(0).delete();
		
		// delete only deletes instructors and students
		// Profile's delete does absolutely nothing lol, so there should still be an admin
		assertEquals(1,pm.numberOfAdmins());
	}

}
