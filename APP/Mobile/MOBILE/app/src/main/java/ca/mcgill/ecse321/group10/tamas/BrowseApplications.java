package ca.mcgill.ecse321.group10.tamas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.group10.TAMAS.model.Application;
import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.ProfileController;

public class BrowseApplications extends AppCompatActivity {
    private TextView jobOfferDescription;
    private Spinner applicationSpinner;
    private TextView errorView;

    private ProfileManager pm;
    private ApplicationManager am;

    private ProfileController pc;
    private ApplicationController ac;


    ArrayList<Job> jobs = null;
    Student student = null;
    List<Application> applications = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_applications);

        jobOfferDescription = (TextView) findViewById(R.id.Offers);
        applicationSpinner = (Spinner) findViewById(R.id.ApplicationSpinner);
        errorView = (TextView) findViewById(R.id.errorView);

        pm = ((TAMAS) getApplication()).getProfileManager();
        am = ((TAMAS) getApplication()).getApplicationManager();
        ac = ((TAMAS) getApplication()).getApplicationController();
        pc = ((TAMAS)getApplication()).getProfileController();

        jobs = new ArrayList<Job>();

        student = ((TAMAS) getApplication()).getStudent();
        applications = am.getApplications();

        for (Application application:applications){
            //type in the model - jobs is a single job
            //if the application has been offered and is the student's application, add it to jobs
            if(application.getJobs().isOfferSent() && application.getStudent().equals(student)){
                Job job = application.getJobs();
                job.addApplicationAt(application,0);
             jobs.add(job);
            }
        }

        //get string of job names
        String [] jobNames = new String[jobs.size()];

        for(int c = 0; c < jobNames.length; c++) {
            jobNames[c] = am.getJob(c).getCourse().getClassName() + ": " + am.getJob(c).getId() + " - " + am.getJob(c).getPositionFullName();
        }
        final ArrayAdapter<String> jobAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, jobNames);
        applicationSpinner.setAdapter(jobAdapter);

        applicationSpinner.setOnItemSelectedListener(
                //create item listener to allow user to acccept job offer by pressing enter
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
                });

    }

    private void setJobDescription(Job job){
        String description = "*" + job.getCourse().getClassName() + " " + job.getPositionFullName() + "*";
        description += "\n\nCourse taught by: ";
        description += "\nProf. " + job.getInstructor().getFirstName() + " " + job.getInstructor().getLastName();
        description += "\n\nSalary: $" + job.getSalary() + "/h";
        description += "\n\nHours: \n" + job.getHours() + "total hours. On " + job.getDay() + "s\n" ;
        jobOfferDescription.setText(description);
    }



    public void acceptOfferClicked(View v){
        if(v.getId() == R.id.AcceptOffer){
            String errors = "";
            Student student = ((TAMAS) getApplication()).getStudent();
            if(student == null) errors += "Must be logged in\n";
            if(applicationSpinner.getSelectedItemPosition() == -1) errors += "No job selected. \n";

            if(errors.length() == 0){
                Job job = am.getJob(applicationSpinner.getSelectedItemPosition());
                try{
                    pc.acceptJob(student,job);
                    ac.setJobOfferAccepted(job.getApplication(0),true);

                    String msg = "You have accepted the job offer for: " + job.getCourse().getClassName() +
                            ": " + job.getId() + " - " + job.getPositionFullName() + ".\nGood luck!";
                    Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
                    toast.setGravity(0,0,15);
                    toast.show();

                }catch (Exception e){
                    errors += "\n" + e.getMessage();
                    errors += "\nFailed to accept job Offer";
                }

            }


            errorView.setText(errors);
        }
    }

}
