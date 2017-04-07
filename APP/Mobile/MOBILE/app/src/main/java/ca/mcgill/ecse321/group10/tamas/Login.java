package ca.mcgill.ecse321.group10.tamas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.controller.ProfileController;

public class Login extends AppCompatActivity {

    ProfileController pc;
    ProfileManager pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    protected void backClicked(View v){
        if(v.getId() == R.id.button){
            Intent goToMainMenu = new Intent(Login.this, MainActivity.class);
            startActivity(goToMainMenu);
        }
    }

    protected void loginClicked(){

    }


}
