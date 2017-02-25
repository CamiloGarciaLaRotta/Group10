package ca.mcgill.ecse321.group10.controller;

import ca.mcgill.ecse321.group10.TAMAS.model.Admin;
import ca.mcgill.ecse321.group10.TAMAS.model.Course;
import ca.mcgill.ecse321.group10.TAMAS.model.Instructor;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ProfileController {
	
	public static String PROFILE_FILE_NAME = "output/profiles.xml";
	
	private ProfileManager pm;
	
	public ProfileController(ProfileManager pm) {
		this.pm = pm;
	}
	
	public void addInstructorToSystem(String aUsername, String aPassword, String aFirstName, String aLastName) throws InputException{
		String error = "";
		if(aUsername == null || aUsername.trim().length() == 0) {
			error += ("Instructor username cannot be empty "); 
		}
		if(aPassword == null || aPassword.trim().length() == 0) {
			error += ("Instructor password cannot be empty") + " ";
		}
		if(aFirstName == null || aFirstName.trim().length() == 0 || aLastName == null || aLastName.trim().length() == 0) {
			error += ("Instructor first and last names cannot be empty") + " ";
		}
		if(error.length() > 0) throw new InputException(error);
		Instructor instructor = new Instructor(aUsername,aPassword,aFirstName,aLastName);
		pm.addInstructor(instructor);
		PersistenceXStream.setFilename(PROFILE_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
	
	public void addCourseToInstructor(int instructor, Course course) {
		pm.getInstructor(instructor).addCourse(course);
		PersistenceXStream.setFilename(PROFILE_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
	
	public void addAdminToSystem(String aUsername, String aPassword, String aFirstName, String aLastName) throws InputException{
		String error = "";
		if(aUsername == null || aUsername.trim().length() == 0) {
			error += ("Admin username cannot be empty") + " ";
		}
		if(aPassword == null || aPassword.trim().length() == 0) {
			error += ("Admin password cannot be empty") + " ";
		}
		if(aFirstName == null || aFirstName.trim().length() == 0 || aLastName == null || aLastName.trim().length() == 0) {
			error += ("Admin first and last names cannot be empty") + " ";
		}
		if(error.length() > 0) throw new InputException(error);
		Admin admin = new Admin(aUsername,aPassword,aFirstName,aLastName);
		pm.addAdmin(admin);
		PersistenceXStream.setFilename(PROFILE_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
	
	public void addStudentToSystem(String aUsername, String aPassword, String aFirstName, String aLastName, String experience)  throws InputException{
		String error = "";
		if(aUsername == null || aUsername.trim().length() == 0) {
			error += ("Student username cannot be empty") + " ";
		}
		if(aPassword == null || aPassword.trim().length() == 0) {
			error += ("Student password cannot be empty") + " ";
		}
		if(aFirstName == null || aFirstName.trim().length() == 0 || aLastName == null || aLastName.trim().length() == 0) {
			error += ("Student first and last names cannot be empty") + " ";
		}
		if(error.length() > 0) throw new InputException(error);
		Student student = new Student(aUsername,aPassword,aFirstName,aLastName,experience);
		pm.addStudent(student);
		PersistenceXStream.setFilename(PROFILE_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
}
