package ca.mcgill.ecse321.group10.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;
import widgets.Constants;
import widgets.ThemedLabel;
import widgets.ThemedPanel;
import widgets.ThemedTabButton;

public class MenuView extends JFrame{
	
	private static final int X_SIZE = 300;
	private static final int Y_SIZE = 300;

	private JLabel greeting;
	private JLabel error;
	private JButton applicationButton;
	private JButton publishButton;
	private JButton profileButton;
	private JButton courseButton;
	private JButton hireButton;
	private JButton offerButton;
	private JButton approveButton;
	
	private JButton studentButton;
	private JButton instructorButton;
	private JButton adminButton;
	
	private enum Tab {Admin, Instructor, Student};
	
	private Tab state;
	
	private ApplicationManager am;
	private ProfileManager pm;
	private CourseManager cm;
	
	private JPanel pAdmin;
	private JPanel pInstructor;
	private JPanel pStudent;
	private JPanel mainPanel;
	
	private static final Dimension BUTTON_SIZE = new Dimension(350,75);
	
	public MenuView(ApplicationManager am, ProfileManager pm, CourseManager cm) {
		this.am = am;
		this.pm = pm;
		this.cm = cm;
		initComponents();
	}
	
	private void initComponents() {
		state = Tab.Admin;
		greeting = new ThemedLabel("");
		error = new ThemedLabel("",ThemedLabel.LabelType.Error);
		applicationButton = new JButton();
		publishButton = new JButton();
		profileButton = new JButton();
		courseButton = new JButton();
		hireButton = new JButton();
		offerButton = new JButton();
		approveButton = new JButton();
		
		adminButton = new ThemedTabButton();
		instructorButton = new ThemedTabButton();
		studentButton = new ThemedTabButton();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Main Control Center");
		
		greeting.setText("Welcome to TAMAS. Power is in your hands.");
		applicationButton.setText("Create Job Application");
		publishButton.setText("Publish Job Posting");
		profileButton.setText("Register Profile");
		courseButton.setText("Create Course");
		hireButton.setText("Hire Applicants");
		offerButton.setText("View Job Offers");
		approveButton.setText("Manage Job Offers");
		
		adminButton.setText("Admin");
		instructorButton.setText("Instructor");
		studentButton.setText("Student");
		
		//applicationButton.setPreferredSize(BUTTON_SIZE);
		//publishButton.setPreferredSize(BUTTON_SIZE);
		//offerButton.setPreferredSize(BUTTON_SIZE);
		//hireButton.setPreferredSize(BUTTON_SIZE);
		//courseButton.setPreferredSize(BUTTON_SIZE);
		//profileButton.setPreferredSize(BUTTON_SIZE);
		
		profileButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				profilePressed();
			}
		});
		
		courseButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				coursePressed();
			}
		});
		
		publishButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(cm.getCourses().size() > 0) {
					new PublishJobView(am,pm,cm).setVisible(true);
					error.setText("");
				}
				else error.setText("No courses are available. Please create a course.");
				pack();
			}
		});
		
		applicationButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(am.getJobs().size() > 0) {
					new ApplicationView(am,pm).setVisible(true);
					error.setText("");
				}
				else error.setText("No jobs available.");
				pack();
			}
		});
		
		hireButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(am.getApplications().size() != 0) {
					new HireView(am,pm).setVisible(true);
					error.setText("");
				}
				else error.setText("No applications have been made yet.");
				pack();
			}
		});
		
		offerButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
				if(constants.get(0) != 0) {
					new OffersView(am,pm).setVisible(true);
					error.setText("");
				}
				else error.setText("Admins have not approved of job offers yet.");
				pack();
			}
		});
		
		approveButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(am.getApplications().size() != 0) {
					new ApproveOffersView(am,pm).setVisible(true);
					error.setText("");
				}
				else error.setText("No applications have been made yet.");
				pack();
			}
		});
		
		adminButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				state = Tab.Admin;
				refresh();
				pack();
			}
		});
		
		studentButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				state = Tab.Student;
				refresh();
				pack();
			}
		});
		
		instructorButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				state = Tab.Instructor;
				refresh();
				pack();
			}
		});
		
		pAdmin = new ThemedPanel(Constants.grey);
		pAdmin.setLayout(new BoxLayout(pAdmin,BoxLayout.Y_AXIS));
		pInstructor = new ThemedPanel(Constants.grey);
		pInstructor.setLayout(new BoxLayout(pInstructor,BoxLayout.Y_AXIS));
		pStudent = new ThemedPanel(Constants.grey);
		pStudent.setLayout(new BoxLayout(pStudent,BoxLayout.Y_AXIS));
		mainPanel = new ThemedPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		JPanel panel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JPanel tabs = new ThemedPanel();
		tabs.setLayout(new FlowLayout());
		tabs.add(adminButton);
		tabs.add(instructorButton);
		tabs.add(studentButton);
		
		panel.add(greeting);
		panel.add(error);
		panel.add(tabs);
		pAdmin.add(profileButton);
		pAdmin.add(courseButton);
		pAdmin.add(approveButton);
		pInstructor.add(publishButton);
		pStudent.add(applicationButton);
		pInstructor.add(hireButton);
		pStudent.add(offerButton);
		
		mainPanel.add(pAdmin);
		panel.add(mainPanel);
		
		this.add(panel);
		this.setSize(new Dimension(X_SIZE,Y_SIZE));
		
		greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
		error.setAlignmentX(Component.CENTER_ALIGNMENT);
		profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		hireButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		offerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		applicationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		publishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		courseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		approveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		refresh();
		pack();
	}
	
	private void refresh() {
		mainPanel.removeAll();
		switch(state) {
		case Admin:
			mainPanel.add(pAdmin);
			adminButton.setEnabled(false);
			instructorButton.setEnabled(true);
			studentButton.setEnabled(true);
			break;
		case Instructor:
			mainPanel.add(pInstructor);
			adminButton.setEnabled(true);
			instructorButton.setEnabled(false);
			studentButton.setEnabled(true);
			break;
		case Student:
			mainPanel.add(pStudent);
			adminButton.setEnabled(true);
			instructorButton.setEnabled(true);
			studentButton.setEnabled(false);
			break;
		}
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
