package ca.mcgill.ecse321.group10.controller;

import ca.mcgill.ecse321.group10.TAMAS.model.Admin;
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
	
	public void addInstructorToSystem(String aId, String aUsername, String aPassword, String aFirstName, String aLastName){
		Instructor instructor = new Instructor(aId,aUsername,aPassword,aFirstName,aLastName);
		pm.addInstructor(instructor);
		PersistenceXStream.setFilename(PROFILE_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
	
	public void addAdminToSystem(String aId, String aUsername, String aPassword, String aFirstName, String aLastName) {
		Admin admin = new Admin(aId,aUsername,aPassword,aFirstName,aLastName);
		pm.addAdmin(admin);
		PersistenceXStream.setFilename(PROFILE_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
	
	public void addStudentToSystem(String aId, String aUsername, String aPassword, String aFirstName, String aLastName, String experience) {
		Student student = new Student(aId,aUsername,aPassword,aFirstName,aLastName,experience);
		pm.addStudent(student);
		PersistenceXStream.setFilename(PROFILE_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
}
