package ca.mcgill.ecse321.group10.tamas_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import ca.mcgill.ecse321.group10.*;
import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;



public class MainMenu extends AppCompatActivity {

    private ApplicationManager am = null;
    private CourseManager cm = null;
    private ProfileManager pm = null;
    public String APPLICATION_FILE_NAME, COURSE_FILE_NAME, PROFILE_FILE_NAME;
    String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(PROFILE_FILE_NAME);
        System.out.println(APPLICATION_FILE_NAME);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //initialize persistence

        //String names for xml files
        APPLICATION_FILE_NAME = getFilesDir().getAbsolutePath() + "/applications.xml";
        COURSE_FILE_NAME = getFilesDir().getAbsolutePath() + "/courses.xml";
        PROFILE_FILE_NAME = getFilesDir().getAbsolutePath() + "/profiles.xml";


        //initializes managers with associated xml files
        am = PersistenceXStream.initializeApplicationManager(APPLICATION_FILE_NAME,PROFILE_FILE_NAME);
        cm = PersistenceXStream.initializeCourseManager(COURSE_FILE_NAME);
        pm = PersistenceXStream.initializeProfileManager(PROFILE_FILE_NAME);
    }

    public void ApplyToJobClicked(View v){
        if(v.getId() == R.id){
            Intent i = new Intent(MainMenu.this, ApplyToJobPosting.class);
            startActivity(i);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
