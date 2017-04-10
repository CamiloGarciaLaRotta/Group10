package ca.mcgill.ecse321.group10.application;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.CourseController;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;
import ca.mcgill.ecse321.group10.view.LoginView;

public class TAMAS {
	
	public static void main(String[] args) {
		
		final ApplicationManager am = PersistenceXStream.initializeApplicationManager(ApplicationController.APPLICATION_FILE_NAME);
		final ProfileManager pm = PersistenceXStream.initializeProfileManager(ProfileController.PROFILE_FILE_NAME);
		final CourseManager cm = PersistenceXStream.initializeCourseManager(CourseController.COURSE_FILE_NAME);
		
		java.awt.EventQueue.invokeLater(
			new Runnable(){
				public void run() {
					new LoginView(am,pm,cm).setVisible(true);
				}
			});
	}

}
