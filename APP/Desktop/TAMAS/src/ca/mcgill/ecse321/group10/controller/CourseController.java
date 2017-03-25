package ca.mcgill.ecse321.group10.controller;

import ca.mcgill.ecse321.group10.TAMAS.model.Course;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class CourseController {
	
	public static String COURSE_FILE_NAME = "output/courses.xml";

	private CourseManager cm;
	private String filename;
	
	public CourseController(CourseManager cm, String filename) {
		this.cm = cm;
		this.filename = filename;
	}
	
	public void createCourse(String aClassName, int aCdn, float aGraderTimeBudget, float aTaTimeBudget) throws InputException {
		String error = "";
		if(aClassName == null || aClassName.trim().length() == 0) {
			error += "Course name cannot be empty! ";
		}
		if(aCdn < 0) error += "CDN must be positive! ";
		if(aGraderTimeBudget < 0) error +=  "Grader Budget must be positive! ";
		if(aTaTimeBudget < 0) error += "TA Budget must be positive! ";
		if (!(courseCdnIsUnique(aCdn))) error += "CDN must be unique! ";
		if(error.length() > 0) throw new InputException(error);
		Course c = new Course(aClassName,aCdn,aGraderTimeBudget,aTaTimeBudget);
		cm.addCourse(c);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(cm);
	}
	// This will check to see if the course being saved has a unique CDN
	public boolean courseCdnIsUnique(int aCdn) {
		
		for (int i = 0; i < cm.numberOfCourses(); i++) {
			if (aCdn == cm.getCourse(i).getCdn()) return false;
		}
		return true;
	}
}
