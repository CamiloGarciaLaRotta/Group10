package ca.mcgill.ecse321.group10.controller;

import java.sql.Time;

import ca.mcgill.ecse321.group10.TAMAS.model.Application;
import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Course;
import ca.mcgill.ecse321.group10.TAMAS.model.Instructor;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ApplicationController {
	
	public static String APPLICATION_FILE_NAME = System.getProperty("user.home") + "/.tamas/output/applications.xml";
	
	private ApplicationManager am;
	private String filename;
	
	/**
	 * Creates instance of an ApplicationController
	 * @param am the ApplicationManager to control
	 * @param filename the filename to load/save data from/to
	 */
	public ApplicationController(ApplicationManager am, String filename){
		this.am = am;
		this.filename = filename;
	}
	
	/**
	 * Adds Job to memory. Should be used in conjunction with, and particularly after the use of CourseController's
	 * modifyTaBudget(), modifyGraderBudget(), or modifyLabBudget() functions depending on the type of Job being
	 * added
	 * @param hours amount of hours per semester that job entails
	 * @param day day of the week, as a string
	 * @param aSalary hourly salary
	 * @param aRequirements any requisite skills the applicants should have
	 * @param aCourse Course for which the job is posted
	 * @param aInstructor Instructor that is teaching the Course
	 * @param position whether the job is for a Tutorial, Grader, or Lab
	 * @throws InputException
	 */
	public void addJobToSystem(float hours, String day, double aSalary, String aRequirements, Course aCourse, Instructor aInstructor, Job.Position position) throws InputException {
		String error = "";
		if(aInstructor == null) error += "Instructor must be defined! ";
		if(aCourse == null) error += "Course must be defined! ";
		if(hours < 45.0f) error += "Job must offer at least 45 hours per semester!"; //Students cannot work less than 45 hours - enforce 45 hour job time minimum
		else if(hours > 180.0f) error += "Job must offer at most 180 hours per semester!"; //Students cannot work more than 180 hours - enforce 180 hour maximum
		if(aSalary < 0) error += "Salary must be positive! ";
		if(day == null) error += "Day must be specified! ";
		else if(day.equals("Saturday") || day.equals("Sunday")) error += "Day must be a work day! ";

		if(error.length() > 0) throw new InputException(error);
		else{
			Job j = new Job(hours,day, aSalary,aRequirements,aCourse,aInstructor);
			j.setPosition(position);
			am.addJob(j);
			PersistenceXStream.setFilename(filename);
			PersistenceXStream.saveToXMLwithXStream(am);
		}
	}
	
	/**
	 * Flags the offerSent variable of a Job. This will allow the Admins to ponder over the possibility
	 * of accepting this job offer. Note: this must be done in conjunction with ProfileController's 
	 * offerJobToStudent() function.
	 * @param j 
	 * @param offered
	 */
	public void setJobOffered(Job j, boolean offered) {
		for(int c = 0; c < am.getJobs().size(); c++) {
			if(j.toString().equals(am.getJob(c).toString())) {
				j.setOfferSent(offered);
				break;
			}
		}
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}
	
	/**
	 * Flag the offerAccepted variable of an Application. This will store the decision of a student to accept
	 * or reject a job offer corresponding to an application he's made. Note: should be used in conjunction
	 * with ProfileController's acceptJob() function
	 * @param a application to the job that the student is accepting
	 * @param accepted
	 */
	public void setJobOfferAccepted(Application a, boolean accepted) {
		for(int c = 0; c < am.getApplications().size(); c++) {
			//if(a.getStudent().getUsername().equals(am.getApplication(c).getStudent().getUsername()) && a.getJobs().toString().equals(am.getApplication(c).getJobs().toString())) {
			if(a.toString().equals(am.getApplication(c).toString())) {
				am.getApplication(c).setOfferAccepted(accepted);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(am);
			}
		}
	}
	
	/**
	 * Sets the evaluation field of an application corresponding to a job that a student has completed. 
	 * @param a
	 * @param eval
	 */
	public void addStudentEvaluation(Application a, String eval) {
		for(int c = 0; c < am.getApplications().size(); c++) {
			if(a.toString().equals(am.getApplication(c).toString())) {
			//if(a.getStudent().getUsername().equals(am.getApplication(c).getStudent().getUsername()) && a.getJobs().toString().equals(am.getApplication(c).getJobs().toString())) {
				a.setStudentEvaluation(eval);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(am);
				break;
			}
		}
	}
	
	/**
	 * Modifies the Position field of a given Job.
	 * @param index index in the system of the job whose position is to be modified.
	 * @param pos
	 */
	public void modifyJobPosition(int index, Job.Position pos) {
		am.getJob(index).setPosition(pos);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}
	
	/**
	 * Saves a job application
	 * @param s student applying for the job
	 * @param j job that's being applied to
	 * @throws InputException
	 */
	public void createApplication(Student s, Job j) throws InputException{
		if(s == null || j == null) throw new InputException("Student or job are null");
		Application a = new Application(s,j);
		am.addApplication(a);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}
	
	/**
	 * Deletes an application from persistence
	 * @param a
	 */
	public void removeApplication(Application a) {
		am.removeApplication(a);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}
}
