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
	
	public static String APPLICATION_FILE_NAME = "output/applications.xml";
	
	private ApplicationManager am;
	private String filename;
	
	public ApplicationController(ApplicationManager am, String filename){
		this.am = am;
		this.filename = filename;
	}
	
	public void addJobToSystem(Time aStartTime, Time aEndTime, String day, double aSalary, String aRequirements, Course aCourse, Instructor aInstructor) throws InputException {
		String error = "";
		if(aInstructor == null) error += "Instructor must be defined! ";
		if(aCourse == null) error += "Course must be defined! ";
		//if(aEndTime.getTime() - aStartTime.getTime() < 0) error += "Start time cannot be after end time! ";
		if(aStartTime == null || aEndTime == null) error += "Start or End Time(s) cannot be empty! ";
		else if (aStartTime.after(aEndTime)) error += "Start time cannot be after end time! ";
		if(aSalary < 0) error += "Salary must be positive! ";
		if(day == null) error += "Day must be specified! ";
		else if(day.equals("Saturday") || day.equals("Sunday")) error += "Day must be a work day! ";
		if(error.length() > 0) throw new InputException(error);
		else{
		Job j = new Job(aStartTime,aEndTime,day, aSalary,aRequirements,aCourse,aInstructor);
		am.addJob(j);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
		}
	}
	
	public void modifyJobPosition(int index, Job.Position pos) {
		am.getJob(index).setPosition(pos);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}
	
	public void createApplication(Student s, Job j) {
		Application a = new Application(s,j);
		am.addApplication(a);
		PersistenceXStream.setFilename(filename);
		PersistenceXStream.saveToXMLwithXStream(am);
	}

}
