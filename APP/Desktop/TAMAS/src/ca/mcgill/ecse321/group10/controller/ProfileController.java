package ca.mcgill.ecse321.group10.controller;

import ca.mcgill.ecse321.group10.TAMAS.model.*;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ProfileController {
	
	public static String PROFILE_FILE_NAME = "output/profiles.xml";
	
	private ProfileManager pm;
	private String filename;
	
	public ProfileController(ProfileManager pm, String filename) {
		this.pm = pm;
		this.filename = filename;
	}
	
	private boolean isUsernameTaken(String username){
		boolean taken = true;
		
		for (Student student: pm.getStudents()){
			if (student.getUsername() == username){
				taken = false;
			}
		}
		for (Instructor instructor: pm.getInstructors()){
			if (instructor.getUsername() == username){
				taken = false;
			}
		}
		for(Admin admin: pm.getAdmins()){
			if(admin.getUsername() == username){
				taken = false;
			}
		}
		
		return taken;
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
		if( !this.isUsernameTaken(aUsername) ){
			error += ("Username is taken") + " ";
		}
		if(error.length() > 0) throw new InputException(error);
		else{
		Instructor instructor = new Instructor(aUsername,aPassword,aFirstName,aLastName);
		pm.addInstructor(instructor);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(pm);
		}
	}
	
	public void addCourseToInstructor(int instructor, Course course) {
		pm.getInstructor(instructor).addCourse(course);
		PersistenceXStream.setFilename(filename);
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
		if( !this.isUsernameTaken(aUsername) ){
			error += ("Username is taken") + " ";
		}
		if(error.length() > 0) throw new InputException(error);
		else{
		Admin admin = new Admin(aUsername,aPassword,aFirstName,aLastName);
		pm.addAdmin(admin);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(pm);
		}
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
		if( !this.isUsernameTaken(aUsername) ){
			error += ("Username is taken") + " ";
		}
		if(error.length() > 0) throw new InputException(error);
		else{
		Student student = new Student(aUsername,aPassword,aFirstName,aLastName,experience);
		pm.addStudent(student);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(pm);
		}
	}
	
	public void removeInstructorFromSystem(String username){
		for (Instructor teacher:pm.getInstructors()){
			if(teacher.getUsername() == username){
				pm.removeInstructor(teacher);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(pm);
			}
		}
	}
	
	public void removeStudentFromSystem(String username){
		for (Student student:pm.getStudents()){
			if(student.getUsername() == username){
				pm.removeStudent(student);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(pm);
			}
		}
	}
	
	public void removeAdminFromSystem(String username){
		for (Admin admin:pm.getAdmins()){
			if(admin.getUsername() == username){
				pm.removeAdmin(admin);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(pm);
			}
		}
	}
	
	//Followed remove implementation based off of the add implementation
	public void RemoveCourseFromInstructor(int instructor, Course course) {
		pm.getInstructor(instructor).addCourse(course);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
	
}
