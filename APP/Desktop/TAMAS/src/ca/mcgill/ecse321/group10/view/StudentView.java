package ca.mcgill.ecse321.group10.view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import widgets.ThemedLabel;
import widgets.ThemedPanel;

public class StudentView extends JFrame{
	private static final int X_SIZE = 300;
	private static final int Y_SIZE = 300;

	private JLabel greeting;
	private JLabel error;
	private JButton applicationButton;
	private JButton offerButton;
	
	private ApplicationManager am;
	private ProfileManager pm;
	private CourseManager cm;
	
	public StudentView(ApplicationManager am, ProfileManager pm, CourseManager cm) {
		this.am = am;
		this.pm = pm;
		this.cm = cm;
		initComponents();
	}
	
	private void initComponents() {
		greeting = new ThemedLabel("");
		error = new ThemedLabel("",ThemedLabel.LabelType.Error);
		applicationButton = new JButton();
		offerButton = new JButton();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Main Control Center");
		
		greeting.setText("Welcome to TAMAS. Power is in your hands.");
		applicationButton.setText("Create Job Application");
		offerButton.setText("View Job Offers");
		
		applicationButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(am.getJobs().size() > 0) {
					new ApplicationView(am,pm,null).setVisible(true);
					error.setText("");
				}
				else error.setText("No jobs available.");
				pack();
			}
		});
		
		offerButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(am.getApplications().size() != 0) {
					new OffersView(am,pm,null).setVisible(true);
					error.setText("");
				}
				else error.setText("No applications have been made yet.");
				pack();
			}
		});
		
		JPanel panel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		panel.add(greeting);
		panel.add(error);
		panel.add(applicationButton);
		panel.add(offerButton);
		
		this.add(panel);
		this.setSize(new Dimension(X_SIZE,Y_SIZE));
		pack();
	}
	
	private void profilePressed() {
		new RegistrationView(pm).setVisible(true);
		error.setText("");
		pack();
	}
	
	private void coursePressed() {
		if(pm.getInstructors().size() > 0) {
			new CreateCourseView(cm,pm).setVisible(true);
			error.setText("");
		}
		else error.setText("No instructors available. Please register an instructor profile.");
		pack();
	}
}
