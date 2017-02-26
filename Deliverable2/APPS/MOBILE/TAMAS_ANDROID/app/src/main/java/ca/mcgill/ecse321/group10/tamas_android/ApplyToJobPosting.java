package ca.mcgill.ecse321.group10.tamas_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.mcgill.ecse321.group10.TAMAS.model.Application;
import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

import static ca.mcgill.ecse321.group10.controller.ApplicationController.APPLICATION_FILE_NAME;

public class ApplyToJobPosting extends AppCompatActivity {

    private ApplicationManager am = null;
    private ProfileManager pm = null;

    private Spinner usernameSpinner = null;
    private Spinner jobSpinner = null;

    List<Job> jobs = null;
    List<Student> students = null;
    public String APPLICATION_FILE_NAME, PROFILE_FILE_NAME;

    private int selectedStudentIndex, selectedJobIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //<< this
        setContentView(R.layout.apply_to_job);


        usernameSpinner = (Spinner) findViewById(R.id.UsernameSpinner);

        APPLICATION_FILE_NAME = getFilesDir().getAbsolutePath() + "/applications.xml";
        PROFILE_FILE_NAME = getFilesDir().getAbsolutePath() + "/profiles.xml";

        am = PersistenceXStream.initializeApplicationManager(APPLICATION_FILE_NAME,PROFILE_FILE_NAME);
        pm = PersistenceXStream.initializeProfileManager(PROFILE_FILE_NAME);

        jobs = am.getJobs();
        students = pm.getStudents();

        //get string of student and job names
        String [] studentNames = new String[pm.getStudents().size()];
        String [] jobNames = new String[am.getJobs().size()];

        for(int c = 0; c < studentNames.length; c++) {
            studentNames[c] = pm.getStudent(c).getUsername();
        }

        for(int c = 0; c < jobNames.length; c++) {
            jobNames[c] = am.getJob(c).getCourse().getClassName() + ": " + am.getJob(c).getId() + " - " + am.getJob(c).getPositionFullName();
        }



        final ArrayAdapter<String> jobAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, jobNames);
        jobSpinner.setAdapter(jobAdapter);

        final ArrayAdapter<String> studentAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, studentNames);
        usernameSpinner.setAdapter(studentAdapter);

        //        jobSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                TextView textView = (TextView) selectedItemView;
//                selectedJobIndex = jobAdapter.getPosition(textView.toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });
//
//        usernameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                TextView textView = (TextView) selectedItemView;
//                selectedStudentIndex = studentAdapter.getPosition(textView.toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });



    }



    public void ApplyClicked(){
        Student student = pm.getStudent(usernameSpinner.getSelectedItemPosition());
        Job job = am.getJob(jobSpinner.getSelectedItemPosition());
        Application application = new Application(student,job);
        am.addApplication(application);
        String msg = student.getUsername() + " has applied to " + job.toString() + ". Good luck!";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}