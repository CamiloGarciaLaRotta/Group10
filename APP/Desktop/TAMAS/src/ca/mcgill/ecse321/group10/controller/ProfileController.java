package ca.mcgill.ecse321.group10.controller;

import ca.mcgill.ecse321.group10.TAMAS.model.*;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ProfileController {
	
	public static String PROFILE_FILE_NAME = System.getProperty("user.home") + "/.tamas/output/profiles.xml";
	
	private ProfileManager pm;
	private String filename;
	
	public ProfileController(ProfileManager pm, String filename) {
		this.pm = pm;
		this.filename = filename;
	}
	
	private boolean isUsernameFree(String username){
		
		for (Student student: pm.getStudents()){
			if (student.getUsername().equals(username)){
				return false;
			}
		}
		for (Instructor instructor: pm.getInstructors()){
			if (instructor.getUsername().equals(username)){
				return false;
			}
		}
		for(Admin admin: pm.getAdmins()){
			if(admin.getUsername().equals(username)){
				return false;
			}
		}
		
		return true;
	}
	
	public void addInstructorToSystem(String aUsername, String aPassword, String aFirstName, String aLastName) throws InputException{
		String error = "";
		error = validateProfile(aUsername,aPassword,aFirstName,aLastName,true);
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
		error = validateProfile(aUsername,aPassword,aFirstName,aLastName,true);
		if(error.length() > 0) throw new InputException(error);
		else{
		Admin admin = new Admin(aUsername,aPassword,aFirstName,aLastName);
		pm.addAdmin(admin);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(pm);
		}
	}
	
	public void addStudentToSystem(String aUsername, String aPassword, String aFirstName, String aLastName, String experience)  throws InputException{
		addStudentToSystem(aUsername,aPassword,aFirstName,aLastName,experience,Student.Degree.UNDERGRAD);
	}
	
	public void addStudentToSystem(String aUsername, String aPassword, String aFirstName, String aLastName, String experience, Student.Degree degree)  throws InputException{
		String error = "";
		error = validateProfile(aUsername,aPassword,aFirstName,aLastName,true);
		if(error.length() > 0) throw new InputException(error);
		else{
			Student student = new Student(aUsername,aPassword,aFirstName,aLastName,experience);
			student.setDegree(degree);
			student.setHoursLeft(180.0f);
			pm.addStudent(student);
			PersistenceXStream.setFilename(filename);
			PersistenceXStream.saveToXMLwithXStream(pm);
		}
	}
	
	public void modifyStudent(String aUsername, String aPassword, String aFirstName, String aLastName, String experience, Student.Degree degree) throws InputException {
		String error = validateProfile(aUsername, aPassword, aFirstName, aLastName,false);
		if(error.length() > 0) throw new InputException(error);
			else {
			for(int c = 0; c < pm.getStudents().size(); c++) {
				if(aUsername.equals(pm.getStudent(c).getUsername())) {
					Student s = pm.getStudent(c);
					s.setFirstName(aFirstName);
					s.setLastName(aLastName);
					s.setPassword(aPassword);
					s.setExperience(experience);
					s.setDegree(degree);
					PersistenceXStream.setFilename(filename);
					PersistenceXStream.saveToXMLwithXStream(pm);
					return;
				}
			}
		}
	}
	
	public void modifyAdmin(String aUsername, String aPassword, String aFirstName, String aLastName) throws InputException{
		String error = validateProfile(aUsername, aPassword, aFirstName, aLastName,false);
		if(error.length() > 0) throw new InputException(error);
		else {
			for(int c = 0; c < pm.getAdmins().size(); c++) {
				if(aUsername.equals(pm.getAdmin(c).getUsername())) {
					Admin a = pm.getAdmin(c);
					a.setFirstName(aFirstName);
					a.setLastName(aLastName);
					a.setPassword(aPassword);
					PersistenceXStream.setFilename(filename);
					PersistenceXStream.saveToXMLwithXStream(pm);
					return;
				}
			}
		}
	}
	
	public void modifyInstructor(String aUsername, String aPassword, String aFirstName, String aLastName) throws InputException{
		String error = validateProfile(aUsername, aPassword, aFirstName, aLastName,false);
		if(error.length() > 0) throw new InputException(error);
		else {
			for(int c = 0; c < pm.getInstructors().size(); c++) {
				if(aUsername.equals(pm.getInstructor(c).getUsername())) {
					Instructor a = pm.getInstructor(c);
					a.setFirstName(aFirstName);
					a.setLastName(aLastName);
					a.setPassword(aPassword);
					PersistenceXStream.setFilename(filename);
					PersistenceXStream.saveToXMLwithXStream(pm);
					return;
				}
			}
		}
	}
	
	private String validateProfile(String aUsername, String aPassword, String aFirstName, String aLastName, boolean newProfile) {
		String error = "";
		if(aUsername == null || aUsername.trim().length() == 0) {
			error += ("Username cannot be empty!") + " ";
		}
		if(aPassword == null || aPassword.trim().length() == 0) {
			error += ("Password cannot be empty!") + " ";
		}
		if(aFirstName == null || aFirstName.trim().length() == 0 || aLastName == null || aLastName.trim().length() == 0) {
			error += ("First and last names cannot be empty!") + " ";
		}
		if( newProfile && !this.isUsernameFree(aUsername) ){
			error += ("Username is taken!") + " ";
		}
		return error;
	}
	
	public void offerJobToStudent(Student s, Job j) {
		for(int c = 0; c < pm.getStudents().size(); c++) {
			if(pm.getStudent(c).getUsername().equals(s.getUsername())) {
				pm.getStudent(c).addJob(j);
				break;
			}
		}
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
	
	public void removeJobOfferFromStudent(Student s, Job j) {
		for(int c = 0; c < pm.getStudents().size(); c++) {
			if(pm.getStudent(c).getUsername().equals(s.getUsername())) {
				pm.getStudent(c).removeJob(j);
				break;
			}
		}
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
	
	public void acceptJob(Student student, Job job) throws InputException{
		for(int c = 0; c < pm.getStudents().size(); c++) {
			if(pm.getStudent(c).getId() == student.getId()) {
				if(pm.getStudent(c).getHoursLeft() - job.getHours() > 0) {
					pm.getStudent(c).setHoursLeft(pm.getStudent(c).getHoursLeft() - job.getHours());
					PersistenceXStream.setFilename(filename);
					PersistenceXStream.saveToXMLwithXStream(pm);
				}
				else throw new InputException("Student cannot take on so many hours!");
			}
		}
	}
	
	public void removeJobFromStudent(Student s, Job j) {
		for(int c = 0; c < pm.getStudents().size(); c++) {
			if(pm.getStudent(c).getUsername().equals(s.getUsername())) {
				pm.getStudent(c).removeJob(j);
				break;
			}
		}
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
	
	
	public void persist() {
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
//	
//	public void removeInstructorFromSystem(String username){
//		for (Instructor teacher:pm.getInstructors()){
//			if(teacher.getUsername() == username){
//				pm.removeInstructor(teacher);
//				PersistenceXStream.setFilename(filename);
//				PersistenceXStream.saveToXMLwithXStream(pm);
//			}
//		}
//	}
//	
//	public void removeStudentFromSystem(String username){
//		for (Student student:pm.getStudents()){
//			if(student.getUsername() == username){
//				pm.removeStudent(student);
//				PersistenceXStream.setFilename(filename);
//				PersistenceXStream.saveToXMLwithXStream(pm);
//			}
//		}
//	}
//	
//	public void removeAdminFromSystem(String username){
//		for (Admin admin:pm.getAdmins()){
//			if(admin.getUsername() == username){
//				pm.removeAdmin(admin);
//				PersistenceXStream.setFilename(filename);
//				PersistenceXStream.saveToXMLwithXStream(pm);
//			}
//		}
//	}
//	
//	//Followed remove implementation based off of the add implementation
//	public void RemoveCourseFromInstructor(int instructor, Course course) {
//		pm.getInstructor(instructor).addCourse(course);
//		PersistenceXStream.setFilename(filename);
//		PersistenceXStream.saveToXMLwithXStream(pm);
//	}
//	
}
