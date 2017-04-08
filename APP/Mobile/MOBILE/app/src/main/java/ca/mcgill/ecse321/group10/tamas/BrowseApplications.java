package ca.mcgill.ecse321.group10.tamas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;

public class BrowseApplications extends AppCompatActivity {
    private TextView jobOfferDescription;
    private Button acceptOffer;
    private Spinner applicationSpinner;

    private ProfileManager pm;
    private ApplicationManager am;

    List<Job> jobs = null;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_applications);
        jobOfferDescription = (TextView) findViewById(R.id.JobDescription);
        acceptOffer = (Button) findViewById(R.id.AcceptOffer);
        applicationSpinner = (Spinner) findViewById(R.id.ApplicationSpinner);

        pm = ((TAMAS) getApplication()).getProfileManager();
        am = ((TAMAS) getApplication()).getApplicationManager();

        student = ((TAMAS) getApplication()).getStudent();
        jobs = student.getJobs();

        for (Job job:jobs){
            if(!job.isOfferSent() ){

            }
        }

    }

    private void setJobDescription(Job job){
        String description = "*" + job.getCourse().getClassName() + " " + job.getPositionFullName() + "*";
        description += "\n\nCourse taught by: ";
        description += "\nProf. " + job.getInstructor().getFirstName() + " " + job.getInstructor().getLastName();
        description += "\n\nSalary: $" + job.getSalary() + "/h";
        description += "\n\nHours: \n" + job.getDay() + "s from " + job.getStartTime().toString() + " to " + job.getEndTime().toString();
        jobOfferDescription.setText(description);
    }

}
