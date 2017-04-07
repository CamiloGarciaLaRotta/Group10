package ca.mcgill.ecse321.group10.tamas;

import android.app.Application;

import java.io.File;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

/**
 * Created by jshnaidman on 2017-04-07.
 */

public class TAMAS extends Application {
    private String username;
    private String password;

    private ApplicationManager am;
    private ProfileManager pm;
    private ApplicationController ac;
    private ProfileController pc;

    protected String APPLICATION_FILE_NAME, PROFILE_FILE_NAME;
    protected String rootPath;

    @Override
    public void onCreate(){
        super.onCreate();
        APPLICATION_FILE_NAME = "./applications.xml";
        PROFILE_FILE_NAME = "./profiles.xml";
        pm = PersistenceXStream.initializeProfileManager(PROFILE_FILE_NAME);
        am = PersistenceXStream.initializeApplicationManager(APPLICATION_FILE_NAME,PROFILE_FILE_NAME);
        ac = new ApplicationController(am,APPLICATION_FILE_NAME);
        pc = new ProfileController(pm,PROFILE_FILE_NAME);

    }


    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }



    public ProfileController getProfileController(){
        return this.pc;
    }
    public ApplicationController getApplicationController(){
        return this.ac;
    }
    public ProfileManager getProfileManager(){
        return this.pm;
    }
    public ApplicationManager getApplicationManager(){
        return this.am;
    }

}
