package ca.mcgill.ecse321.group10.controller;

import ca.mcgill.ecse321.group10.TAMAS.model.Course;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class CourseController {
	
	public static String COURSE_FILE_NAME = System.getProperty("user.home") + "/.tamas/output/courses.xml";

	private CourseManager cm;
	private String filename;
	
	/**
	 * Creates CourseController instance
	 * @param cm the CourseManager being controlled
	 * @param filename the filename to load/save data from/to
	 */
	public CourseController(CourseManager cm, String filename) {
		this.cm = cm;
		this.filename = filename;
	}
	
	/**
	 * Creates Course object and saves it to the persistence layer
	 * @param aClassName name for the proposed Course
	 * @param aCdn unique identifier number for the Course
	 * @param aGraderBudget monetary budget per semester for the Course allocated to graders
	 * @param aTaBudget monetary budget per semester for the Course allocated to tutorial TAs
	 * @param labBudget monetary budget per semester for the Course allocated to lab TAs
	 * @throws InputException
	 */
	public void createCourse(String aClassName, int aCdn, float aGraderBudget, float aTaBudget, float labBudget) throws InputException {
		String error = "";
		if(aClassName == null || aClassName.trim().length() == 0) {
			error += "Course name cannot be empty! ";
		}
		if(aCdn < 0) error += "CDN must be positive! ";
		if(aGraderBudget < 0) error +=  "Grader Budget must be positive! ";
		if(aTaBudget < 0) error += "TA Budget must be positive! ";
		if(labBudget < 0) error += "Lab Budget must be positive! ";
		if (courseCdnAlreadyExists(aCdn)) error += "CDN must be unique! ";
		if(error.length() > 0) throw new InputException(error);
		else{
			Course c = new Course(aClassName,aCdn,aGraderBudget,aTaBudget,labBudget);
			cm.addCourse(c);
			PersistenceXStream.setFilename(filename);
			PersistenceXStream.saveToXMLwithXStream(cm);
		}
	}
	
	/**
	 * Decrements tutorial budget as a consequence of a job being published. Should be used
	 * in conjunction with, and particular before the use of ApplicationController's addJobToSystem() function
	 * @param course
	 * @param cost
	 * @throws InputException corresponding to a budget error
	 */
	public void modifyTaBudget(Course course, float cost) throws InputException{
		for(int c = 0; c < cm.getCourses().size(); c++) {
			if(cm.getCourse(c).getCdn() == course.getCdn()) {
				if(cm.getCourse(c).getTutorialBudget() - cost < 0) {
					throw new InputException("Not enough budget for this job!");
				}
				cm.getCourse(c).setTutorialBudget(cm.getCourse(c).getTutorialBudget() - cost);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(cm);
				break;
			}
		}
	}
	
	/**
	 * Decrements tutorial budget as a consequence of a job being published. Should be used
	 * in conjunction with, and particular before the use of ApplicationController's addJobToSystem() function
	 * @param course
	 * @param cost
	 * @throws InputException corresponding to a budget error
	 */
	public void modifyGraderBudget(Course course, float cost) throws InputException {
		for(int c = 0; c < cm.getCourses().size(); c++) {
			if(cm.getCourse(c).getCdn() == course.getCdn()) {
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
	
	/**
	 * Decrements tutorial budget as a consequence of a job being published. Should be used
	 * in conjunction with, and particular before the use of ApplicationController's addJobToSystem() function
	 * @param course
	 * @param cost
	 * @throws InputException corresponding to a budget error
	 */
	public void modifyLabBudget(Course course, float cost) throws InputException {
		for(int c = 0; c < cm.getCourses().size(); c++) {
			if(cm.getCourse(c).getCdn() == course.getCdn()) {
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

	/**
	 * Determines if a proposed CDN is already associated to another course
	 * @param aCdn
	 * @return
	 */
	private boolean courseCdnAlreadyExists(int aCdn) {

		for (int i = 0; i < cm.numberOfCourses(); i++) {
			if (aCdn == cm.getCourse(i).getCdn()) return true;
		}
		return false;
	}
}
