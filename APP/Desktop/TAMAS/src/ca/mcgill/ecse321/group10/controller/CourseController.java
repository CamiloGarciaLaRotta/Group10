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
	
	public void createCourse(String aClassName, int aCdn, float aGraderTimeBudget, float aTaTimeBudget, float labBudget) throws InputException {
		String error = "";
		if(aClassName == null || aClassName.trim().length() == 0) {
			error += "Course name cannot be empty! ";
		}
		if(aCdn < 0) error += "CDN must be positive! ";
		if(aGraderTimeBudget < 0) error +=  "Grader Budget must be positive! ";
		if(aTaTimeBudget < 0) error += "TA Budget must be positive! ";
		if(labBudget < 0) error += "Lab Budget must be positive! ";
		if (courseCdnAlreadyExists(aCdn)) error += "CDN must be unique! ";
		if(error.length() > 0) throw new InputException(error);
		else{
			Course c = new Course(aClassName,aCdn,aGraderTimeBudget,aTaTimeBudget,labBudget);
			cm.addCourse(c);
			PersistenceXStream.setFilename(filename);
			PersistenceXStream.saveToXMLwithXStream(cm);
		}
	}
	
	public void modifyTaBudget(Course course, float cost) throws InputException{
		for(int c = 0; c < cm.getCourses().size(); c++) {
			if(cm.getCourse(c).getClassName().equals(course.getClassName())) {
				if(cm.getCourse(c).getTaBudget() - cost < 0) {
					throw new InputException("Not enough budget for this job!");
				}
				cm.getCourse(c).setTaBudget(cm.getCourse(c).getTaBudget() - cost);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(cm);
				break;
			}
		}
	}
	
	public void modifyGraderBudget(Course course, float cost) throws InputException {
		for(int c = 0; c < cm.getCourses().size(); c++) {
			if(cm.getCourse(c).getClassName().equals(course.getClassName())) {
				if(cm.getCourse(c).getGraderBudget() - cost < 0) {
					throw new InputException("Not enough budget for this job!");
				}
				cm.getCourse(c).setGraderBudget(cm.getCourse(c).getGraderBudget() - cost);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(cm);
				break;
			}
		}
	}
	public void modifyLabBudget(Course course, float cost) throws InputException {
		for(int c = 0; c < cm.getCourses().size(); c++) {
			if(cm.getCourse(c).getClassName().equals(course.getClassName())) {
				if(cm.getCourse(c).getLabBudget() - cost < 0) {
					throw new InputException("Not enough budget for this job!");
				}
				cm.getCourse(c).setLabBudget(cm.getCourse(c).getLabBudget() - cost);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(cm);
				break;
			}
		}
	}
	// This will check to see if the course being saved has a unique CDN
	// ONLY USE IT BEFORE YOU ACTUALLY CREATE THE COURSE
	// USE IT NOWHERE BUT HERE
	private boolean courseCdnAlreadyExists(int aCdn) {

		for (int i = 0; i < cm.numberOfCourses(); i++) {
			if (aCdn == cm.getCourse(i).getCdn()) return true;
		}
		return false;
	}
}
