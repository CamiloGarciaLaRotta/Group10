package ca.mcgill.ecse321.group10.tamas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class MainActivity extends AppCompatActivity {

    Button button = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void createJobAppClicked(View v){
        if(v.getId() == R.id.button){
            Intent i = new Intent(MainActivity.this, ApplyToJob.class);
            startActivity(i);
        }
    }

}
