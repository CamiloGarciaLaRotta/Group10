package ca.mcgill.ecse321.group10.controller;

import java.sql.Date;

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
	
	public ApplicationController(ApplicationManager am){
		this.am = am;
	}
	
	public void addJobToSystem(Date aStartTime, Date aEndTime, double aSalary, String aRequirements, Course aCourse, Instructor aInstructor) {
		Job j = new Job(aStartTime,aEndTime,aSalary,aRequirements,aCourse,aInstructor);
		am.addJob(j);
		PersistenceXStream.setFilename(APPLICATION_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(am);
	}
	
	public void createApplication(Student s, Job j) {
		Application a = new Application(s,j);
		am.addApplication(a);
		PersistenceXStream.setFilename(APPLICATION_FILE_NAME);
		PersistenceXStream.saveToXMLwithXStream(am);
	}

}
