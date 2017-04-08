package ca.mcgill.ecse321.group10.tamas;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.sql.Time;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.CourseController;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;


/**
 * Created by jshnaidman on 2017-04-07.
 */

public class TAMAS extends Application {
    private String username;
    private String password;
    private Student student;

    private ApplicationManager am;
    private ProfileManager pm;
    private ApplicationController ac;
    private ProfileController pc;
    private CourseController cc;
    private CourseManager cm;

    protected String APPLICATION_FILE_PATH, PROFILE_FILE_PATH, COURSE_FILE_PATH;
    protected String rootPath;

    @Override
    public void onCreate(){
        super.onCreate();
        rootPath = getApplicationInfo().dataDir;
        APPLICATION_FILE_PATH = rootPath + "/applications.xml";
        PROFILE_FILE_PATH = rootPath + "/profiles.xml";
        COURSE_FILE_PATH = rootPath + "/courses.xml";

        pm = PersistenceXStream.initializeProfileManager(PROFILE_FILE_PATH);
        am = PersistenceXStream.initializeApplicationManager(APPLICATION_FILE_PATH,PROFILE_FILE_PATH);
        cm = PersistenceXStream.initializeCourseManager(COURSE_FILE_PATH);

        ac = new ApplicationController(am,APPLICATION_FILE_PATH);
        pc = new ProfileController(pm,PROFILE_FILE_PATH);
        cc = new CourseController(cm,COURSE_FILE_PATH);

        createDummyData();
        //for testing
        student = pm.getStudent(0);

    }

    private void createDummyData() {
        if(pm.getStudents().size() != 0 && am.getJobs().size() != 0) {
            return;
        }

        //ONE OF THESE IS THROWING AN EXCEPTION
        try {
            pc.addStudentToSystem("dummy1", "ecse321", "dummy", "one", "not much experience");
            pc.addStudentToSystem("dummy2", "ecse321", "dummy", "two", "a lot of experience");
            pc.addStudentToSystem("dummy3", "ecse321", "dummy", "three", "high gpa");
            pc.addStudentToSystem("j","s","j","s","none");
            pc.addInstructorToSystem("donaldknuth","taocp","Donald","Knuth");
            pc.addInstructorToSystem("linus","linux","Linus","Torvalds");
            pc.addInstructorToSystem("vimcreator","sdfjdfs","Bill","Joy");
            cc.createCourse("The Art of Computer Programming",1,45,45,45);
            cc.createCourse("The Dragon Curve",2,45,45,45);
            cc.createCourse("Kernel Development",3,45,45,45);
            cc.createCourse("Linux",4,45,45,45);
            cc.createCourse("Becoming a Billionaire",5,45,45,45);
            cc.createCourse("Vim 101",6,45,45,45);
            cc.createCourse("Algorithm Analysis",7,45,45,45);
            pc.addCourseToInstructor(0,cm.getCourse(0));
            pc.addCourseToInstructor(0,cm.getCourse(1));
            pc.addCourseToInstructor(1,cm.getCourse(2));
            pc.addCourseToInstructor(1,cm.getCourse(3));
            pc.addCourseToInstructor(2,cm.getCourse(4));
            pc.addCourseToInstructor(2,cm.getCourse(5));
            pc.addCourseToInstructor(0,cm.getCourse(6));

            ac.addJobToSystem(45,"Tuesday",11.12,"None",cm.getCourse(0),pm.getInstructor(0), Job.Position.TUTORIAL);
            
            ac.addJobToSystem(45,"Wednesday",10.12,"None",cm.getCourse(0),pm.getInstructor(0),  Job.Position.TUTORIAL);
            
            ac.addJobToSystem(120,"Monday",11.12,"Lots",cm.getCourse(1),pm.getInstructor(0),  Job.Position.TUTORIAL);
            
            ac.addJobToSystem(150,"Friday",10.12,"None",cm.getCourse(1),pm.getInstructor(0),  Job.Position.TUTORIAL);
            
            ac.addJobToSystem(110,"Friday",11.12,"None",cm.getCourse(2),pm.getInstructor(1), Job.Position.GRADER);
            
            ac.addJobToSystem(145,"Wednesday",10.12,"None",cm.getCourse(2),pm.getInstructor(1), Job.Position.GRADER);
            
            ac.addJobToSystem(145,"Wednesday",11.12,"None",cm.getCourse(3),pm.getInstructor(1), Job.Position.GRADER);
            
            ac.addJobToSystem(145,"Thursday",10.12,"None",cm.getCourse(3),pm.getInstructor(1), Job.Position.GRADER);
            
            ac.addJobToSystem(145,"Friday",11.12,"None",cm.getCourse(4),pm.getInstructor(2), Job.Position.GRADER);
            
            ac.addJobToSystem(145,"Monday",10.12,"None",cm.getCourse(4),pm.getInstructor(2), Job.Position.GRADER);
            
            ac.addJobToSystem(145,"Tuesday",11.12,"None",cm.getCourse(5),pm.getInstructor(2), Job.Position.LABORATORY);
            
            ac.addJobToSystem(145,"Monday",10.12,"None",cm.getCourse(5),pm.getInstructor(2), Job.Position.LABORATORY);
            
            ac.addJobToSystem(145,"Tuesday",11.12,"None",cm.getCourse(6),pm.getInstructor(0), Job.Position.LABORATORY);
            
            ac.addJobToSystem(145,"Monday",10.12,"None",cm.getCourse(6),pm.getInstructor(0), Job.Position.LABORATORY);
            

            ac.setJobOffered(am.getJob(0), true);
            ac.setJobOffered(am.getJob(1), true);
            ac.setJobOffered(am.getJob(5), true);
            ac.setJobOffered(am.getJob(10), true);
        }
        catch (Exception e) {
            Log.d("ERROR", e.getStackTrace().toString());
        }
        pm = PersistenceXStream.initializeProfileManager(PROFILE_FILE_PATH);
        am = PersistenceXStream.initializeApplicationManager(APPLICATION_FILE_PATH,PROFILE_FILE_PATH);
        cm = PersistenceXStream.initializeCourseManager(COURSE_FILE_PATH);
        Log.d("dummy", String.valueOf(am.getJobs().size()));
    }


    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public Student getStudent(){
        return this.student;
    }
    public void setStudent(Student student){
        this.student = student;
    }


    public ProfileController getProfileController(){
        return this.pc;
    }
    public ApplicationController getApplicationController(){
        return this.ac;
    }
    public ProfileManager getProfileManager(){
        return this.pm;
    }
    public ApplicationManager getApplicationManager(){
        return this.am;
    }

}
