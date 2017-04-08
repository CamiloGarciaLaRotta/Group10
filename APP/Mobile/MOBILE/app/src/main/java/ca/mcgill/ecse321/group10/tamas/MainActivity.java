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

    String rootPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void browseAppsClicked(View v){
        if (v.getId() == R.id.BrowseApps){
            Intent i = new Intent(MainActivity.this, BrowseApplications.class);
            startActivity(i);
        }
    }


}
