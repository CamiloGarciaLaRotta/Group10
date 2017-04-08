package ca.mcgill.ecse321.group10.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import ca.mcgill.ecse321.group10.TAMAS.model.Admin;
import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Instructor;
import ca.mcgill.ecse321.group10.TAMAS.model.Profile;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;
import widgets.Constants;
import widgets.ThemedLabel;
import widgets.ThemedPanel;
import widgets.ThemedTabButton;

public class MenuView extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1163663035678723691L;
	private static final int X_SIZE = 300;
	private static final int Y_SIZE = 300;

	private ThemedLabel greeting;
	private ThemedLabel lTAMAS;
	private ThemedLabel error;
	private JButton applicationButton;
	private JButton publishButton;
	private JButton profileButton;
	private JButton courseButton;
	private JButton hireButton;
	private JButton offerButton;
	private JButton approveButton;
	private JButton logoutButton;
	private JButton evaluateButton;
	private JButton feedbackButton;
	private JButton colorButton;
	private JButton updateButton;
	
	private JButton studentButton;
	private JButton instructorButton;
	private JButton adminButton;
	
	private enum Tab {Admin, Instructor, Student};
	
	private Tab state;
	
	private ApplicationManager am;
	private ProfileManager pm;
	private CourseManager cm;
	
	private ThemedPanel pAdmin;
	private ThemedPanel pInstructor;
	private ThemedPanel pStudent;
	private ThemedPanel mainPanel;
	private ThemedPanel panel;
	private ThemedPanel tabs;
	
	private Profile user;
	
	private String[] colorToggle;
	private int theme;
	
	@Deprecated
	public MenuView(ApplicationManager am, ProfileManager pm, CourseManager cm) {
		this.am = am;
		this.pm = pm;
		this.cm = cm;
		this.user = null;
		initComponents();
	}
	
	public MenuView(ApplicationManager am, ProfileManager pm, CourseManager cm, Profile user) {
		this.am = am;
		this.pm = pm;
		this.cm = cm;
		this.user = user;
		initComponents();
	}
	
	private void refreshWidgets() {
		greeting.setColors();
		lTAMAS.setColors();
		error.setColors();
		pAdmin.setColors();
		pInstructor.setColors();
		pStudent.setColors();
		mainPanel.setColors();
		panel.setColors();
		tabs.setColors();
		colorButton.setText(colorToggle[theme]);
	}
	
	private void initComponents() {
		colorToggle = new String[2];
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
		theme = constants.get(1);
		colorToggle[0] = "Switch to light theme";
		colorToggle[1] = "Switch to dark theme";
		if(user.getClass() == Admin.class) state = Tab.Admin;
		else if(user.getClass() == Instructor.class) state = Tab.Instructor;
		else state = Tab.Student;
		
		greeting = new ThemedLabel("");
		lTAMAS = new ThemedLabel("");
		error = new ThemedLabel("",ThemedLabel.LabelType.Error);
		applicationButton = new JButton();
		publishButton = new JButton();
		profileButton = new JButton();
		courseButton = new JButton();
		hireButton = new JButton();
		offerButton = new JButton();
		approveButton = new JButton();
		logoutButton = new JButton();
		evaluateButton = new JButton();
		feedbackButton = new JButton();
		colorButton = new JButton();
		updateButton = new JButton();
		
		adminButton = new ThemedTabButton();
		instructorButton = new ThemedTabButton();
		studentButton = new ThemedTabButton();
		
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Main Control Center");
		
		lTAMAS.setText("Welcome to TAMAS. Power is in your hands.");
		if(user != null) greeting.setText("Hello, " + user.getFirstName() + ".");
		applicationButton.setText("Create Job Application");
		publishButton.setText("Publish Job Posting");
		profileButton.setText("Register Profile");
		courseButton.setText("Create Course");
		hireButton.setText("Hire Applicants");
		offerButton.setText("View Job Offers");
		approveButton.setText("Manage Job Offers");
		logoutButton.setText("Log out");
		evaluateButton.setText("Evaluate Your TAs");
		feedbackButton.setText("View Your Evaluations");
		colorButton.setText(colorToggle[theme]);
		updateButton.setText("Edit Your Profile");
		
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
				refresh();
			}
		});
		
		courseButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				coursePressed();
				refresh();
			}
		});
		
		publishButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(cm.getCourses().size() > 0) {
					if(user.getClass() == Admin.class) new PublishJobView(am,pm,cm,null).setVisible(true);
					else if(user.getClass() == Instructor.class) new PublishJobView(am,pm,cm,(Instructor)user).setVisible(true);
					error.setText("");
				}
				else error.setText("No courses are available. Please create a course.");
				refresh();
				pack();
			}
		});
		
		applicationButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(am.getJobs().size() > 0) {
					if(user.getClass() == Admin.class) new ApplicationView(am,pm,null).setVisible(true);
					else if(user.getClass() == Student.class) new ApplicationView(am,pm,(Student)user).setVisible(true);
					error.setText("");
				}
				else error.setText("No jobs available.");
				refresh();
				pack();
			}
		});
		
		hireButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(am.getApplications().size() != 0) {
					if(user.getClass() == Admin.class) new HireView(am,pm,null).setVisible(true);
					else if(user.getClass() == Instructor.class) new HireView(am,pm,(Instructor)user).setVisible(true);
					error.setText("");
				}
				else error.setText("No applications have been made yet.");
				refresh();
				pack();
			}
		});
		
		offerButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
				if(constants.get(0) != 0) {
					if(user.getClass() == Admin.class) new OffersView(am,pm,null).setVisible(true);
					else if(user.getClass() == Student.class) new OffersView(am,pm,(Student)user).setVisible(true);
					error.setText("");
				}
				else error.setText("Admins have not approved of job offers yet.");
				refresh();
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
				refresh();
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
		
		logoutButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
		
		evaluateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(user.getClass() == Admin.class) new EvaluateView(am,pm,null).setVisible(true);
				else if(user.getClass() == Instructor.class) new EvaluateView(am,pm,(Instructor)user).setVisible(true);
				error.setText("");
			}
		});
		
		feedbackButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(user.getClass() == Admin.class) new FeedbackView(pm,null).setVisible(true);
				else if(user.getClass() == Student.class) new FeedbackView(pm,(Student)user).setVisible(true);
				error.setText("");
			}
		});
		
		colorButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				theme ^= 1; //The epitome of ECSE323
				ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
				constants.set(1, theme);
				PersistenceXStream.setFilename("output/constants.xml");
				PersistenceXStream.saveToXMLwithXStream(constants);
				refreshWidgets();
			}
		});
		
		updateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new RegistrationView(pm,user).setVisible(true);
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
		
		panel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		tabs = new ThemedPanel();
		tabs.setLayout(new FlowLayout());
		tabs.add(adminButton);
		tabs.add(instructorButton);
		tabs.add(studentButton);
		
		panel.add(greeting);
		panel.add(lTAMAS);
		panel.add(error);
		if(user.getClass() == Admin.class) panel.add(tabs);
		pAdmin.add(profileButton);
		pAdmin.add(courseButton);
		pAdmin.add(approveButton);
		pInstructor.add(publishButton);
		pStudent.add(applicationButton);
		pInstructor.add(hireButton);
		pInstructor.add(evaluateButton);
		pStudent.add(offerButton);
		pStudent.add(feedbackButton);
		
		mainPanel.add(pAdmin);
		panel.add(mainPanel);
		panel.add(updateButton);
		panel.add(colorButton);
		panel.add(logoutButton);
		
		this.add(panel);
		this.setSize(new Dimension(X_SIZE,Y_SIZE));
		
		greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
		lTAMAS.setAlignmentX(Component.CENTER_ALIGNMENT);
		error.setAlignmentX(Component.CENTER_ALIGNMENT);
		profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		hireButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		offerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		applicationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		publishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		courseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		approveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		evaluateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		feedbackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		colorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
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
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
		if(constants.get(0) == 1) {
			approveButton.setText("Job offers already sent...");
			approveButton.setEnabled(false);
		}
	}
	
	private void profilePressed() {
		new RegistrationView(pm,null).setVisible(true);
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
