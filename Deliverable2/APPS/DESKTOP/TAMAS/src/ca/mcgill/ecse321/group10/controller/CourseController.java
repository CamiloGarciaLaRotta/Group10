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
	
	public void createCourse(String aClassName, int aCdn, float aGraderTimeBudget, float aTaTimeBudget) throws InputException {
		String error = "";
		if(aClassName == null || aClassName.trim().length() == 0) {
			error += "Course name cannot be empty! ";
		}
		if(error.length() > 0) throw new InputException(error);
		Course c = new Course(aClassName,aCdn,aGraderTimeBudget,aTaTimeBudget);
		cm.addCourse(c);
		PersistenceXStream.setFilename(COURSE_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(cm);
	}
}
