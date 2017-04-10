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
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ApplyToJob extends AppCompatActivity {


    private ApplicationManager am;
    private ProfileManager pm;
    private ProfileController pc;
    private String username = null;
    private Spinner jobSpinner = null;
    private TextView errorText = null;
    private TextView jobDescription = null;


    private String errors = "";

    private Student student;


    List<Job> jobs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_to_job);
        jobSpinner = (Spinner) findViewById(R.id.spinner);
        username = ((TAMAS) this.getApplication()).getUsername();
        errorText = (TextView) findViewById(R.id.errors);
        jobDescription = (TextView) findViewById(R.id.JobDescription);


        student = ((TAMAS) getApplication()).getStudent();

        if(student == null){
            errorText.setText("Please Login");
        }
        else{
            setupPage();
        }

//create item listener to allow user to acccept job offer by pressing enter
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
         String description = "*" + job.getCourse().getClassName() + "*";
         description += "\nPosition: " + job.getPositionFullName() + "*";
         description += "\n\nCourse taught by: ";
         description += "\nProf. " + job.getInstructor().getFirstName() + " " + job.getInstructor().getLastName();
         description += "\n\nSalary: $" + job.getSalary() + "/h";
         description += "\n\nHours: \n" + job.getHours() + " total hours. On " + job.getDay() + "s\n" ;
        jobDescription.setText(description);
    }



    public void ApplyToJobClicked(View v) {
        if(v.getId() == R.id.applyButton) {
            errors = "";
            if(student == null) errors += "Must be logged in\n";
            if(jobSpinner.getSelectedItemPosition() == -1) errors += "No job selected. \n";
            if(errors.length() == 0) {
                try {
                    Job job = am.getJob(jobSpinner.getSelectedItemPosition());
                    ApplicationController ac = ((TAMAS) this.getApplication()).getApplicationController();
                    ac.createApplication(student,job);
                    pc = ((TAMAS)this.getApplication()).getProfileController();
                    pc.offerJobToStudent(student,job);
                    String msg = student.getUsername() + " has applied to\n" + job.getCourse().getClassName() +
                            ": " + job.getId() + " - " + job.getPositionFullName() + ".\nGood luck!";
                    Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
                    toast.setGravity(0,0,15);
                    toast.show();
                    //success
                } catch(Exception e) {
                    errors += e.getMessage();
                    Log.d("errors", e.getStackTrace().toString());
                    errors += "\nFailed to create application.";
                }
            }
            errorText.setText(errors);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        student = ((TAMAS)this.getApplication()).getStudent();

        if(student == null){
            errorText.setText("Please Login");
        }
        else{
            this.setupPage();
            errorText.setText("");
        }
    }


    private void setupPage(){

        pm = ((TAMAS) getApplication()).getProfileManager();
        am = ((TAMAS) getApplication()).getApplicationManager();
        Log.v("load",pm.getStudent(0).toString());

        jobs = am.getJobs();

        //get string of job names
        String [] jobNames = new String[am.getJobs().size()];

        for(int c = 0; c < jobNames.length; c++) {
            jobNames[c] = am.getJob(c).getCourse().getClassName() + ": " + am.getJob(c).getPositionFullName();
        }

        final ArrayAdapter<String> jobAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, jobNames);
        jobSpinner.setAdapter(jobAdapter);
    }



}
