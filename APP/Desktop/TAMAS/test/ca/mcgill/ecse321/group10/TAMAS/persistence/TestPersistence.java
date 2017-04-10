package ca.mcgill.ecse321.group10.TAMAS.persistence;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class TestPersistence {

	private ArrayList<Integer> constants;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		constants = PersistenceXStream.initializeConstants("testconstants.xml");
	}

	@After
	public void tearDown() throws Exception {
		constants.clear();
		constants.add(0);
		constants.add(0);
		PersistenceXStream.setFilename("testconstants.xml");
		PersistenceXStream.saveToXMLwithXStream(constants);
	}

	@Test
	public void testConstantsPersistence() {
		assertEquals(true,constants.get(0) == 0);
		assertEquals(true,constants.get(1) == 0);
		constants.set(0, 1);
		constants.set(1, 2);
		PersistenceXStream.setFilename("testconstants.xml");
		PersistenceXStream.saveToXMLwithXStream(constants);
		constants = PersistenceXStream.initializeConstants("testconstants.xml");
		assertEquals(true,constants.get(0) == 1);
		assertEquals(true,constants.get(1) == 2);
	}

}
