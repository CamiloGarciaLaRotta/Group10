package ca.mcgill.ecse321.group10.tamas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.mcgill.ecse321.group10.TAMAS.model.Application;
import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ApplyToJob extends AppCompatActivity {


    private ApplicationManager am;
    private ProfileManager pm;
    private String username = null;
    private Spinner jobSpinner = null;
    private TextView errorText = null;

    private TextView jobDescription1, jobDescription2, jobDescription3, jobDescription4, jobDescription5 = null;


    private String errors = "";


    List<Job> jobs = null;
    List<Student> students = null;
    public String APPLICATION_FILE_NAME, PROFILE_FILE_NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_to_job);
        jobSpinner = (Spinner) findViewById(R.id.spinner);
        username = ((TAMAS) this.getApplication()).getUsername();
        errorText = (TextView) findViewById(R.id.errors);
        jobDescription1 = (TextView) findViewById(R.id.JobDescription1);
        jobDescription2 = (TextView) findViewById(R.id.JobDescription2);
        jobDescription3 = (TextView) findViewById(R.id.JobDescription3);
        jobDescription4 = (TextView) findViewById(R.id.JobDescription4);
        jobDescription5 = (TextView) findViewById(R.id.JobDescription5);

        pm = ((TAMAS) getApplication()).getProfileManager();
        am = ((TAMAS) getApplication()).getApplicationManager();
        Log.v("load",pm.getStudent(0).toString());

        jobs = am.getJobs();
        students = pm.getStudents();

        //get string of job names
        String [] jobNames = new String[am.getJobs().size()];

        Log.v("size", String.valueOf(am.getJobs().size()));
        for(int c = 0; c < jobNames.length; c++) {
            jobNames[c] = am.getJob(c).getCourse().getClassName() + ": " + am.getJob(c).getId() + " - " + am.getJob(c).getPositionFullName();
        }

        final ArrayAdapter<String> jobAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, jobNames);
        jobSpinner.setAdapter(jobAdapter);

        jobSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Job selectedJob = jobs.get(position); //job index within jobs should be at the same index as jobNames from the adapter
                        setJobDescription(selectedJob);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //do nothing
                    }
                }
        );
    }


    private void setJobDescription(Job job){
        String description1 = "*" + job.getCourse().getClassName() + " " + job.getPositionFullName() + "*\n";
        String description2 = "Course taught by: ";
        String description5 = "Prof. " + job.getInstructor().getFirstName() + " " + job.getInstructor().getLastName();
        String description3 = "Salary: $" + job.getSalary() + "/h";
        String description4 = "Hours: " + job.getDay() + "s from " + job.getStartTime().toString() + " to " + job.getEndTime().toString();
        jobDescription1.setText(description1);
        jobDescription2.setText(description2);
        jobDescription3.setText(description3);
        jobDescription4.setText(description4);
        jobDescription5.setText(description5);
    }


    private static int getStudentIndex(List<Student> students, String name){
        for (int i = 0; i< students.size(); i++){
            if(students.get(i).getUsername().equals(name)){
                return i;
            }
        }
        return -1;
    }


    public void ApplyToJobClicked(View v) {
        if(v.getId() == R.id.applyButton) {
            errors = "";
            Student student = ((TAMAS) getApplication()).getStudent();
            if(student == null) errors += "Must be logged in\n";
            if(jobSpinner.getSelectedItemPosition() == -1) errors += "No job selected. \n";
            if(errors.length() == 0) {
                try {
                    Job job = am.getJob(jobSpinner.getSelectedItemPosition());
                    ApplicationController ac = ((TAMAS) this.getApplication()).getApplicationController();
                    ac.createApplication(student,job);
                    String msg = student.getUsername() + " has applied to\n" + job.getCourse().getClassName() +
                            ": " + job.getId() + " - " + job.getPositionFullName() + ".\nGood luck!";
                    Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
                    toast.setGravity(0,0,15);
                    toast.show();
                    //success
                } catch(Exception e) {
                    errors += "Failed to create application.\n";
                }
            }
            errorText.setText(errors);
        }
    }

    @Deprecated
    public void applyToJobClicked(View v){
        if(v.getId() == R.id.applyButton){
            int index = getStudentIndex(students,username);
            Student student = null;
            Job job = null;
            try{
                student = pm.getStudent(index);
            }catch(Exception e){
                errors += "Student username not found. \n";
            }
            try {
                job = am.getJob(jobSpinner.getSelectedItemPosition());
            }catch(Exception e){
                errors += "No job selected. ";
            }
            try{
                //Application application = new Application(student,job);
                //am.addApplication(application);
                ApplicationController ac = new ApplicationController(am,APPLICATION_FILE_NAME);
                ac.createApplication(student,job);
                String msg = student.getUsername() + " has applied to\n" + job.getCourse().getClassName() +
                            ": " + job.getId() + " - " + job.getPositionFullName() + ".\nGood luck!";
                Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
                toast.setGravity(0,0,15);
                toast.show();
                //success
                errorText.setText("");
            }catch (Exception e){
                //do nothing, already handles inputs in previous try catch blocks
            } finally {
                errorText.setText(errors);
            }

        }
    }
}
