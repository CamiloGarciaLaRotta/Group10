package ca.mcgill.ecse321.group10.controller;

import ca.mcgill.ecse321.group10.TAMAS.model.*;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ProfileController {
	
	public static String PROFILE_FILE_NAME = System.getProperty("user.home") + "/.tamas/output/profiles.xml";
	
	private ProfileManager pm;
	private String filename;
	
	/**
	 * Initializes a ProfileController instance
	 * @param pm the ProfileManager to control
	 * @param filename the filename to load/save data from/to
	 */
	public ProfileController(ProfileManager pm, String filename) {
		this.pm = pm;
		this.filename = filename;
	}
	
	/**
	 * Determines if a proposed username already belongs to another profile in the system
	 * @param username
	 * @return true when the username is not already being used
	 */
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
	
	/**
	 * Saves an Instructor object
	 * @param aUsername
	 * @param aPassword
	 * @param aFirstName
	 * @param aLastName
	 * @throws InputException
	 */
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
	
	/**
	 * Binds a Course object to an Instructor object and saves the association
	 * @param instructor 
	 * @param course
	 */
	public void addCourseToInstructor(int instructor, Course course) {
		pm.getInstructor(instructor).addCourse(course);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(pm);
	}
	
	/**
	 * Saves an Admin object
	 * @param aUsername
	 * @param aPassword
	 * @param aFirstName
	 * @param aLastName
	 * @throws InputException
	 */
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
	
	/**
	 * Saves a Student Object, defaulting the degree parameter to Undergrad
	 * @param aUsername
	 * @param aPassword
	 * @param aFirstName
	 * @param aLastName
	 * @param experience
	 * @throws InputException
	 */
	public void addStudentToSystem(String aUsername, String aPassword, String aFirstName, String aLastName, String experience)  throws InputException{
		addStudentToSystem(aUsername,aPassword,aFirstName,aLastName,experience,Student.Degree.UNDERGRAD);
	}
	
	/**
	 * Saves a Student Object
	 * @param aUsername
	 * @param aPassword
	 * @param aFirstName
	 * @param aLastName
	 * @param experience
	 * @param degree
	 * @throws InputException
	 */
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
	
	/**
	 * Replaces Student profile parameters for a student with a given username
	 * @param aUsername username of the student being modified
	 * @param aPassword new password to save in the student's profile
	 * @param aFirstName new first name to save in the student's profile
	 * @param aLastName new last name to save in the student's profile
	 * @param experience modified experience text to save
	 * @param degree modified degree status
	 * @throws InputException
	 */
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
	
	/**
	 * Replaces Admin profile parameters for an admin with a given username
	 * @param aUsername username of admin to modify
	 * @param aPassword new password
	 * @param aFirstName new first name
	 * @param aLastName new last name
	 * @throws InputException
	 */
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
	
	/**
	 * Replaces Instructor profile parameters for an instructor with a given username
	 * @param aUsername username of instructor to modify
	 * @param aPassword new password
	 * @param aFirstName new first name
	 * @param aLastName new last name
	 * @throws InputException
	 */
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
	
	/**
	 * Judges the validity of proposed profile data
	 * @param aUsername
	 * @param aPassword
	 * @param aFirstName
	 * @param aLastName
	 * @param newProfile
	 * @return true if profile data corresponds to a valid Profile
	 */
	private String validateProfile(String aUsername, String aPassword, String aFirstName, String aLastName, boolean newProfile) {
		String error = "";
		if(aUsername == null || aUsername.trim().length() == 0) {
			error += ("Username cannot be empty!") + " ";
		}
		if(aPassword == null || aPassword.trim().length() == 0) {
			error += ("Password cannot be empty!") + " ";
		}
		if((aFirstName == null || aFirstName.trim().length() == 0) || (aLastName == null || aLastName.trim().length() == 0)) {
			error += ("First and last names cannot be empty!") + " ";
		}
		if( newProfile && !this.isUsernameFree(aUsername) ){
			error += ("Username is taken!") + " ";
		}
		return error;
	}
	
	/**
	 * Effectively offers job to student by adding the job to the student's jobs list.
	 * Note: should be used in conjunction with ApplicationController's setJobOffered() function
	 * @param s student receiving the job offer
	 * @param j job being offered
	 */
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
	
	/**
	 * Effectively removes job offer from student; used when student accepts/rejects job offer or when
	 * admin rejects the job offer. Should be used in conjunction with ApplicationController's setJobOffered() function
	 * @param s
	 * @param j
	 */
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
	
	/**
	 * Effectively deduces the amount of available hours left for a given student accepting a given
	 * job offer. Note: should be used in conjunction with ApplicationController's setJobOfferAccepted() function
	 * @param student student profile accepting the job offer
	 * @param job job offer being accepted
	 * @throws InputException
	 */
	public void acceptJob(Student student, Job job) throws InputException{
		for(int c = 0; c < pm.getStudents().size(); c++) {
			if(pm.getStudent(c).getId() == student.getId()) {
				if(pm.getStudent(c).getHoursLeft() - job.getHours() >= 0) {
					pm.getStudent(c).setHoursLeft(pm.getStudent(c).getHoursLeft() - job.getHours());
					PersistenceXStream.setFilename(filename);
					PersistenceXStream.saveToXMLwithXStream(pm);
				}
				else throw new InputException("Student cannot take on so many hours!");
			}
		}
	}
}
