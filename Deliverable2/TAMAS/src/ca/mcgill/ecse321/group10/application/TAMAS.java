package ca.mcgill.ecse321.group10.application;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;
import ca.mcgill.ecse321.group10.view.MenuView;

public class TAMAS {
	
	private static String applicationFileName = "output/applications.xml";
	private static String staffFileName = "output/staff.xml";
	
	public static void main(String[] args) {
		
		final ApplicationManager am = PersistenceXStream.initializeApplicationManager(applicationFileName);
		final ProfileManager pm = PersistenceXStream.initializeProfileManager(staffFileName);
		
		java.awt.EventQueue.invokeLater(
			new Runnable(){
				public void run() {
					new MenuView().setVisible(true);
				}
			});
	}

}
