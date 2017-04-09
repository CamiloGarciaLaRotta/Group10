package ca.mcgill.ecse321.group10.tamas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.group10.TAMAS.model.Application;
import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.ProfileController;

public class BrowseEvals extends AppCompatActivity {


    private TextView evaluations;
    private Spinner evalSpinner;

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
        setContentView(R.layout.activity_browse_evals);

        evalSpinner = (Spinner) findViewById(R.id.EvalSpinner);
        evaluations = (TextView) findViewById(R.id.Evaluations);

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
            if(application.isOfferAccepted() && application.getStudent().equals(student)){
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
        evalSpinner.setAdapter(jobAdapter);

        evalSpinner.setOnItemSelectedListener(
                //create item listener to allow user to acccept job offer by pressing enter
                new AdapterView.OnItemSelectedListener(){

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Job selectedJob = jobs.get(position); //job index within jobs should be at the same index as jobNames from the adapter
                        setEvaluationDescription(selectedJob);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //do nothing
                    }
                });
    }

    private void setEvaluationDescription(Job job){
        String eval = job.getApplication(0).getStudentEvaluation();
        if(eval.length() == 0 || eval == null){
            return;
        }
        else{
            String description = "Evaluation:\n" + eval;
            evaluations.setText(description);
        }


    }
}
