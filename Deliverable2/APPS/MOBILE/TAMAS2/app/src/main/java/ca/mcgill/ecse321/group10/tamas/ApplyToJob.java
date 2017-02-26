package ca.mcgill.ecse321.group10.tamas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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


    private static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
    private ApplicationManager am;
    private ProfileManager pm;
    private EditText usernameField = null;
    private Spinner jobSpinner = null;
    private TextView errorText = null;
    private String errors = "";


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

//        String rootPath = "/data/XML";
        String rootPath = getFilesDir().getAbsolutePath();


        APPLICATION_FILE_NAME = rootPath + "/applications.xml";
        PROFILE_FILE_NAME = rootPath + "/profiles.xml";

        int permissionCheck = ContextCompat.checkSelfPermission(ApplyToJob.this,
                Manifest.permission.READ_EXTERNAL_STORAGE); // -1 = denied, 0 = granted


        if (permissionCheck != PackageManager.PERMISSION_GRANTED){

            //request permission
            ActivityCompat.requestPermissions(ApplyToJob.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);


        }



        pm = PersistenceXStream.initializeProfileManager(PROFILE_FILE_NAME);
        am = PersistenceXStream.initializeApplicationManager(APPLICATION_FILE_NAME,PROFILE_FILE_NAME);




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
            if(students.get(i).getUsername().equals(name)){
                return i;
            }

        }
        return -1;
    }


    public void ApplyToJobClicked(View v){
        if(v.getId() == R.id.applyButton){
            String username = usernameField.getText().toString();
            System.out.println(username);
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
                Application application = new Application(student,job);
                am.addApplication(application);
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

    //handles user response to requesting permissions
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//            if(requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    //permission granted
//
//
//                } else {
//                    Toast.makeText(ApplyToJob.this,"The Application will not work without permissions",Toast.LENGTH_LONG);
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }


}
