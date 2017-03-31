import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.controller.CourseController;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class CourseControllerTest {
	private CourseController cc;
	private CourseManager cm;
	private static String outputFile = "output"  + File.separator + "testCourseController";


	@Before
	public void setUp() throws Exception {
		PersistenceXStream.initializeCourseManager(outputFile);
		cm = new CourseManager();
		cc = new CourseController(cm, outputFile);
		
		assertEquals(0,cm.getCourses().size());
	}

	@After
	public void tearDown() throws Exception {
		cm.delete();
		File f = new File(outputFile);
		f.delete();
		assertEquals(0,cm.getCourses().size());
	}

	@Test
	public void testCreateCourseValid() throws InputException {
		cc.createCourse("history", 1, 2, 3);
		cm = (CourseManager) PersistenceXStream.loadFromXMLwithXStream();
		assertEquals(1,cm.getCourses().size());
	}
	
	@Test
	public void testCreateCourseInvalid(){
		try{
			cc.createCourse(null, 1, 2, 3);
		}catch(InputException e){
			cm = (CourseManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(0,cm.getCourses().size());
		}
		try{
			cc.createCourse("", 1, 2, 3);
		}catch(InputException e){
			cm = (CourseManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(0,cm.getCourses().size());
		}
		try{
			cc.createCourse("course", -1, 2, 3);
		}catch(InputException e){
			cm = (CourseManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(0,cm.getCourses().size());
		}
		try{
			cc.createCourse("course", 1, -2, 3);
		}catch(InputException e){
			cm = (CourseManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(0,cm.getCourses().size());
		}
		try{
			cc.createCourse("course", 1, 2, -3);
		}catch(InputException e){
			cm = (CourseManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(0,cm.getCourses().size());
		}
	}
	
	@Test
	public void testUniqueCDN(){
		try {
			cc.createCourse("course1", 1,2,3);
		} catch (InputException e) {
		}
		
		try {
			cc.createCourse("course2", 1,4,5);
		} catch (InputException e) {
			cm = (CourseManager) PersistenceXStream.loadFromXMLwithXStream();
			assertEquals(1,cm.getCourses().size());
		}
	}

}
