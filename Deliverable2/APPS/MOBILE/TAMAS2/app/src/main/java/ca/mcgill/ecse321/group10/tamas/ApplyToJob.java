package ca.mcgill.ecse321.group10.tamas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ApplyToJob extends AppCompatActivity {



    private ApplicationManager am = null;
    private ProfileManager pm = null;
    private EditText usernameField = null;
    private Spinner jobSpinner = null;
    private TextView errorText = null;
    private String errors;


    List<Job> jobs = null;
    List<Student> students = null;
    public String APPLICATION_FILE_NAME, PROFILE_FILE_NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_to_job);


        jobSpinner = (Spinner) findViewById(R.id.spinner);
        usernameField = (EditText) findViewById(R.id.usernameField);
        errorText = (TextView) findViewById(R.id.errors);


        APPLICATION_FILE_NAME = getFilesDir().getAbsolutePath() + "/applications.xml";
        PROFILE_FILE_NAME = getFilesDir().getAbsolutePath() + "/profiles.xml";


        am = PersistenceXStream.initializeApplicationManager(APPLICATION_FILE_NAME,PROFILE_FILE_NAME);
        pm = PersistenceXStream.initializeProfileManager(PROFILE_FILE_NAME);

        jobs = am.getJobs();
        students = pm.getStudents();

        System.out.println(PROFILE_FILE_NAME);
        System.out.println(APPLICATION_FILE_NAME);


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



    }

    public void usernameClicked(View v){
        if(v.getId() == R.id.usernameField){
            if (usernameField.getText().toString().trim().equalsIgnoreCase("")) {
                usernameField.setError("This field can not be blank");
            }
        }
    }


    private static int getStudentIndex(List<Student> students, String name){
        for (int i = 0; i< students.size(); i++){
            if(students.get(i).getUsername() == name){
                return i;
            }

        }
        return -1;
    }


    public void ApplyToJobClicked(View v){
        if(v.getId() == R.id.applyButton){
            int index = getStudentIndex(students,usernameField.getText().toString());
            Student student = null;
            try{
                student = pm.getStudent(index);
            }catch(Exception e){
                errors += "Student username not Found ";
            }
            Job job = am.getJob(jobSpinner.getSelectedItemPosition());
            try{
                Application application = new Application(student,job);
                am.addApplication(application);
                String msg = student.getUsername() + " has applied to " + job.toString() + ". Good luck!";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                //success
                errorText.setText("");
            }catch (Exception e){
                errors += e.getMessage();
            } finally {
                errorText.setText(errors);
            }

        }
    }

}
