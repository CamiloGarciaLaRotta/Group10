package ca.mcgill.ecse321.group10.tamas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Time;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.CourseController;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class MainActivity extends AppCompatActivity {

    Button button = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void createJobAppClicked(View v){
        if(v.getId() == R.id.button){
            createDummyData();
            Intent i = new Intent(MainActivity.this, ApplyToJob.class);
            startActivity(i);
        }
    }

    private void createDummyData() {
        String applicationPath = getObbDir().getAbsolutePath() + "/applications.xml";
        String profilePath = getObbDir().getAbsolutePath() + "/profiles.xml";
        String coursePath = getObbDir().getAbsolutePath() + "/courses.xml";



        System.out.println(getObbDir().getAbsolutePath());
        ProfileManager pm = PersistenceXStream.initializeProfileManager(profilePath);
        ApplicationManager am = PersistenceXStream.initializeApplicationManager(applicationPath,profilePath);
        CourseManager cm = PersistenceXStream.initializeCourseManager(coursePath);

        if(pm.getStudents().size() != 0 && am.getJobs().size() != 0) {
            return;
        }

        ProfileController pc = new ProfileController(pm,profilePath);
        ApplicationController ac = new ApplicationController(am,applicationPath);
        CourseController cc = new CourseController(cm,coursePath);

        try {
            pc.addStudentToSystem("dummy1", "ecse321", "dummy", "one", "not much experience");
            pc.addStudentToSystem("dummy2", "ecse321", "dummy", "two", "a lot of experience");
            pc.addStudentToSystem("dummy3", "ecse321", "dummy", "three", "high gpa");
            pc.addInstructorToSystem("donaldknuth","taocp","Donald","Knuth");
            pc.addInstructorToSystem("linus","linux","Linus","Torvalds");
            pc.addInstructorToSystem("vimcreator","sdfjdfs","Bill","Joy");
            cc.createCourse("The Art of Computer Programming",0,32,46);
            cc.createCourse("The Dragon Curve",1,234,653);
            cc.createCourse("Kernel Development",2,234,66);
            cc.createCourse("Linux",3,33,11);
            cc.createCourse("Becoming a Billionaire",4,322,10000);
            cc.createCourse("Vim 101", 5, 22, 553.2f);
            cc.createCourse("Algorithm Analysis",6,33.2f,33.5f);
            pc.addCourseToInstructor(0,cm.getCourse(0));
            pc.addCourseToInstructor(0,cm.getCourse(1));
            pc.addCourseToInstructor(1,cm.getCourse(2));
            pc.addCourseToInstructor(1,cm.getCourse(3));
            pc.addCourseToInstructor(2,cm.getCourse(4));
            pc.addCourseToInstructor(2,cm.getCourse(5));
            pc.addCourseToInstructor(0,cm.getCourse(6));
            ac.addJobToSystem(new Time(0),new Time(1000),"Tuesday",11.12,"None",cm.getCourse(0),pm.getInstructor(0));
            ac.modifyJobPosition(0, Job.Position.TA);
            ac.addJobToSystem(new Time(0),new Time(1000),"Wednesday",10.12,"None",cm.getCourse(0),pm.getInstructor(0));
            ac.modifyJobPosition(1, Job.Position.GRADER);
            ac.addJobToSystem(new Time(0),new Time(4000),"Monday",11.12,"Lots",cm.getCourse(1),pm.getInstructor(0));
            ac.modifyJobPosition(2, Job.Position.TA);
            ac.addJobToSystem(new Time(0),new Time(2000),"Friday",10.12,"None",cm.getCourse(1),pm.getInstructor(0));
            ac.modifyJobPosition(3, Job.Position.GRADER);
            ac.addJobToSystem(new Time(0),new Time(1000),"Friday",11.12,"None",cm.getCourse(2),pm.getInstructor(1));
            ac.modifyJobPosition(4, Job.Position.TA);
            ac.addJobToSystem(new Time(0),new Time(1000),"Wednesday",10.12,"None",cm.getCourse(2),pm.getInstructor(1));
            ac.modifyJobPosition(5, Job.Position.GRADER);
            ac.addJobToSystem(new Time(0),new Time(1000),"Wednesday",11.12,"None",cm.getCourse(3),pm.getInstructor(1));
            ac.modifyJobPosition(6, Job.Position.TA);
            ac.addJobToSystem(new Time(0),new Time(1000),"Thursday",10.12,"None",cm.getCourse(3),pm.getInstructor(1));
            ac.modifyJobPosition(7, Job.Position.GRADER);
            ac.addJobToSystem(new Time(0),new Time(1000),"Friday",11.12,"None",cm.getCourse(4),pm.getInstructor(2));
            ac.modifyJobPosition(8, Job.Position.TA);
            ac.addJobToSystem(new Time(0),new Time(1000),"Monday",10.12,"None",cm.getCourse(4),pm.getInstructor(2));
            ac.modifyJobPosition(9, Job.Position.GRADER);
            ac.addJobToSystem(new Time(0),new Time(1000),"Tuesday",11.12,"None",cm.getCourse(5),pm.getInstructor(2));
            ac.modifyJobPosition(10, Job.Position.TA);
            ac.addJobToSystem(new Time(0),new Time(1000),"Monday",10.12,"None",cm.getCourse(5),pm.getInstructor(2));
            ac.modifyJobPosition(11, Job.Position.GRADER);
            ac.addJobToSystem(new Time(0),new Time(1000),"Tuesday",11.12,"None",cm.getCourse(6),pm.getInstructor(0));
            ac.modifyJobPosition(12, Job.Position.TA);
            ac.addJobToSystem(new Time(0),new Time(1000),"Monday",10.12,"None",cm.getCourse(6),pm.getInstructor(0));
            ac.modifyJobPosition(13, Job.Position.GRADER);
        }catch (InputException e) {
            System.out.println(e.getMessage());
        }
    }

}
