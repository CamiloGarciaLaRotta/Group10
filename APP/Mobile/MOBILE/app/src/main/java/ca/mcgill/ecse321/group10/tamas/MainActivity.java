package ca.mcgill.ecse321.group10.tamas;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    TextView userInfo;
    Student student;

    Button modProfile;
    Button applyToJob;
    Button browseJobOffers;
    Button viewEvals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInfo = (TextView) findViewById(R.id.currentUser);
        modProfile = (Button) findViewById(R.id.modifyProfile);
        applyToJob = (Button) findViewById(R.id.createJob);
        browseJobOffers = (Button) findViewById(R.id.BrowseApps);
        viewEvals = (Button) findViewById(R.id.BrowseEvals);



        student = ((TAMAS) this.getApplication()).getStudent();
        checkLogin();

    }

    private void checkLogin() {
        if (student == null){
            Context userInfoContext = findViewById(R.id.currentUser).getContext();
            userInfo.setTextColor(ContextCompat.getColor(userInfoContext,R.color.errorColor));
            userInfo.setText("Please login order to use Tamas\nOther buttons are currently disabled");
            userInfo.setTextSize(18);;
            this.setEnableButtons(false);
        }
        else{
            String username = ((TAMAS) this.getApplication()).getStudent().getUsername();
            String greeting = "Hello " + username;
            userInfo.setText(greeting);
            this.setEnableButtons(true);
        }
    }

    public void createJobAppClicked(View v){
        if(v.getId() == R.id.createJob){
            Intent i = new Intent(MainActivity.this, ApplyToJob.class);
            startActivity(i);
        }
    }

    public void loginClicked(View v){
        if (v.getId() == R.id.Login){
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
        }
    }


    public void modifyProfileClicked(View v){
        if (v.getId() == R.id.modifyProfile){
            Intent i = new Intent(MainActivity.this, ModifyProfile.class);
            startActivity(i);
        }
    }

    public void browseAppsClicked(View v){
        if (v.getId() == R.id.BrowseApps){
            Intent i = new Intent(MainActivity.this, BrowseApplications.class);
            startActivity(i);
        }
    }

    public void browseEvalsClicked(View v){
        if (v.getId() == R.id.BrowseEvals){
            Intent i = new Intent(MainActivity.this, BrowseEvals.class);
            startActivity(i);
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        student = ((TAMAS) this.getApplication()).getStudent();
        checkLogin();
    }



    private void setEnableButtons(boolean enabled){
        modProfile.setEnabled(enabled);
        applyToJob.setEnabled(enabled);
        browseJobOffers.setEnabled(enabled);
        viewEvals.setEnabled(enabled);

    }

}
