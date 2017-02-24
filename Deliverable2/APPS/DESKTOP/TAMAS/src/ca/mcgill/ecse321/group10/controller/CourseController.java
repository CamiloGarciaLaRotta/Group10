package ca.mcgill.ecse321.group10.controller;

import ca.mcgill.ecse321.group10.TAMAS.model.Course;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class CourseController {
	
	public static String COURSE_FILE_NAME = "output/courses.xml";

	private CourseManager cm;
	
	public CourseController(CourseManager cm) {
		this.cm = cm;
	}
	
	public void createCourse(String aClassName, String aCdn, float aGraderTimeBudget, float aTaTimeBudget){
		Course c = new Course(aClassName,aCdn,aGraderTimeBudget,aTaTimeBudget);
		cm.addCourse(c);
		PersistenceXStream.setFilename(COURSE_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(cm);
	}
}
