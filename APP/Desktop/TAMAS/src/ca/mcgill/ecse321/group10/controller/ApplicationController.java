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
	
	public ApplicationController(ApplicationManager am, String filename){
		this.am = am;
		this.filename = filename;
	}
	
	public void addJobToSystem(float hours, String day, double aSalary, String aRequirements, Course aCourse, Instructor aInstructor, Job.Position position) throws InputException {
		String error = "";
		if(aInstructor == null) error += "Instructor must be defined! ";
		if(aCourse == null) error += "Course must be defined! ";
		//if(aEndTime.getTime() - aStartTime.getTime() < 0) error += "Start time cannot be after end time! ";
		if(hours < 45.0f) error += "Job must offer at least 45 hours per semester!";
		else if(hours > 180.0f) error += "Job must offer at most 180 hours per semester!";
		if(aSalary < 0) error += "Salary must be positive! ";
		if(day == null) error += "Day must be specified! ";
		else if(day.equals("Saturday") || day.equals("Sunday")) error += "Day must be a work day! ";
		if(position == Job.Position.TUTORIAL) {
			//if(aCourse.getTaBudget() - hours * aSalary < 0) error += "Not enough budget remaining! ";
			//else aCourse.setTaBudget((float)(aCourse.getTaBudget() - hours * aSalary));
		} else if (position == Job.Position.GRADER) {
			//if(aCourse.getGraderBudget() - hours * aSalary < 0) error += "Not enough budget remaining! ";
			//else aCourse.setGraderBudget((float)(aCourse.getGraderBudget() - hours * aSalary));
		} else {
			//if(aCourse.getLabBudget() - hours * aSalary < 0) error += "Not enough budget remaining!";
		}
		if(error.length() > 0) throw new InputException(error);
		else{
			Job j = new Job(hours,day, aSalary,aRequirements,aCourse,aInstructor);
			j.setPosition(position);
			am.addJob(j);
			PersistenceXStream.setFilename(filename);
			PersistenceXStream.saveToXMLwithXStream(am);
		}
	}
	
	public void setJobOffered(Job j, boolean offered) {
		for(int c = 0; c < am.getJobs().size(); c++) {
			if(j.toString().equals(am.getJob(c).toString())) j.setOfferSent(offered);
		}
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}
	
	public void setJobOfferAccepted(Application a, boolean accepted) {
		for(int c = 0; c < am.getApplications().size(); c++) {
			if(a.getStudent().getUsername().equals(am.getApplication(c).getStudent().getUsername()) && a.getJobs().toString().equals(am.getApplication(c).getJobs().toString())) {
				am.getApplication(c).setOfferAccepted(accepted);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(am);
			}
		}
	}
	
	public void addStudentEvaluation(Application a, String eval) {
		for(int c = 0; c < am.getApplications().size(); c++) {
			if(a.getStudent().getUsername().equals(am.getApplication(c).getStudent().getUsername()) && a.getJobs().toString().equals(am.getApplication(c).getJobs().toString())) {
				a.setStudentEvaluation(eval);
				PersistenceXStream.setFilename(filename);
				PersistenceXStream.saveToXMLwithXStream(am);
				break;
			}
		}
	}
	
	public void modifyJobPosition(int index, Job.Position pos) {
		am.getJob(index).setPosition(pos);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}
	
	public void createApplication(Student s, Job j) throws InputException{
		if(s == null || j == null) throw new InputException("Student or job are null");
		Application a = new Application(s,j);
		am.addApplication(a);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}
	
	public void removeApplication(Application a) {
		am.removeApplication(a);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}
	
	public void persist() {
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}

}
