package ca.mcgill.ecse321.group10.view;

import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.group10.TAMAS.model.Admin;
import ca.mcgill.ecse321.group10.TAMAS.model.Instructor;
import ca.mcgill.ecse321.group10.TAMAS.model.Profile;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;
import widgets.Constants;
import widgets.ThemedLabel;
import widgets.ThemedPasswordField;
import widgets.ThemedRadioButton;
import widgets.ThemedTextArea;
import widgets.ThemedTextField;

public class RegistrationView extends JFrame implements java.awt.event.ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6576949847499887611L;

	private ProfileManager pm;
	
	ThemedLabel error;
	
	JRadioButton rbStudent, rbInstructor, rbAdmin;
	ButtonGroup radioGroup;
	JLabel lRole;
	
	JRadioButton rbUndergrad, rbGrad;
	ButtonGroup degGroup;
	
	JLabel lFirst, lLast, lUser, lPass, lDegree;
	JTextField tfFirst, tfLast, tfUser;
	JPasswordField tfPass;
	JButton submit;
	
	JTextArea taReqs;
	JLabel lReqs;
	String reqs;
	JScrollPane reqScroller;
	
	boolean problem;
	
	Profile profile;
	
	public RegistrationView(ProfileManager pm, Profile profile) {
		this.pm = pm;
		reqs = "";
		this.profile = profile;
		initComponents();
		problem = false;
	}
	
	private void initComponents() {
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Register a Profile");
		error = new ThemedLabel("",ThemedLabel.LabelType.Error);
		rbStudent = new ThemedRadioButton();
		rbInstructor = new ThemedRadioButton();
		rbAdmin = new ThemedRadioButton();
		radioGroup = new ButtonGroup();
		rbUndergrad = new ThemedRadioButton();
		rbGrad = new ThemedRadioButton();
		lDegree = new ThemedLabel("Degree:");
		degGroup = new ButtonGroup();
		lRole = new ThemedLabel("");
		lFirst = new ThemedLabel("");	
		lLast = new ThemedLabel("");
		lUser = new ThemedLabel("");
		lPass = new ThemedLabel("");
		tfFirst = new ThemedTextField();
		tfLast = new ThemedTextField();
		tfUser = new ThemedTextField();
		tfPass = new ThemedPasswordField();
		submit = new JButton();
		
		lReqs = new ThemedLabel("Skills: ");
		lReqs.setVisible(false);
		taReqs = new ThemedTextArea();
		taReqs.setRows(5);
		reqScroller = new JScrollPane(taReqs);
		taReqs.setText(reqs);
		reqScroller.setVisible(false);
		
		error.setText("");
		rbStudent.setText("Student");
		rbInstructor.setText("Instructor");
		rbAdmin.setText("Admin");
		rbStudent.addActionListener(this);
		rbInstructor.addActionListener(this);
		rbAdmin.addActionListener(this);
		radioGroup.add(rbStudent);
		radioGroup.add(rbInstructor);
		radioGroup.add(rbAdmin);
		lRole.setText("Indicate type of profile:");
		lFirst.setText("First Name: ");
		lLast.setText("Last Name: " );
		lUser.setText("Username: ");
		lPass.setText("Password: ");
		submit.setText("Submit");
		rbUndergrad.setText("Undergraduate");
		rbGrad.setText("Graduate");
		rbUndergrad.setSelected(true);
		degGroup.add(rbUndergrad);
		degGroup.add(rbGrad);
		
		submit.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				submitPressed();
			}
		});
		
		if(profile != null) {
			if(profile.getClass() == Admin.class) rbAdmin.setSelected(true);
			else if(profile.getClass() == Instructor.class) rbInstructor.setSelected(true);
			else {
				rbStudent.setSelected(true);
				Student s = (Student)profile;
				taReqs.setText(s.getExperience());
				if(s.getDegree() == Student.Degree.UNDERGRAD) rbUndergrad.setSelected(true);
				else rbGrad.setSelected(true);
			}
			tfFirst.setText(profile.getFirstName());
			tfLast.setText(profile.getLastName());
			tfUser.setText(profile.getUsername());
			tfUser.setEnabled(false);
			actionPerformed(null);
			rbStudent.setEnabled(false);
			rbInstructor.setEnabled(false);
			rbAdmin.setEnabled(false);
		}
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		if(constants.get(1) == 0) getContentPane().setBackground(Constants.dark_bgColor);
		else getContentPane().setBackground(Constants.light_bgColor);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	    layout.setHorizontalGroup(
	    		layout.createParallelGroup()
	    		.addComponent(error)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lRole)
	    				.addGroup(
	    						layout.createParallelGroup()
	    						.addComponent(rbAdmin)
	    						.addComponent(rbInstructor)
	    						.addComponent(rbStudent)
	    						)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lDegree)
	    				.addComponent(rbUndergrad)
	    				.addComponent(rbGrad)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lFirst)
	    				.addComponent(tfFirst,200,200,400)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lLast)
	    				.addComponent(tfLast,200,200,400)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lUser)
	    				.addComponent(tfUser,200,200,400)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lPass)
	    				.addComponent(tfPass,200,200,400)
	    				)
	    		.addComponent(lReqs)
	    		.addComponent(reqScroller)
	    		.addComponent(submit)
	    );
	    layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]{lFirst,lLast,lUser,lPass});
	    
	    layout.setVerticalGroup(
	    		layout.createSequentialGroup()
	    		.addComponent(error)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lRole)
	    				.addGroup(
	    						layout.createSequentialGroup()
	    	    				.addComponent(rbAdmin)
	    	    				.addComponent(rbInstructor)
	    	    				.addComponent(rbStudent)
	    						)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lDegree)
	    				.addComponent(rbUndergrad)
	    				.addComponent(rbGrad)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    	    		.addGroup(
	    	    				layout.createSequentialGroup()
	    	    				.addComponent(lFirst)
	    	    				.addComponent(lLast)
	    	    				.addComponent(lUser)
	    	    				.addComponent(lPass)
	    	    				)
	    	    		.addGroup(
	    	    				layout.createSequentialGroup()
	    	    				.addComponent(tfFirst)
	    	    				.addComponent(tfLast)
	    	    				.addComponent(tfUser)
	    	    				.addComponent(tfPass)
	    	    				)
	    				)
	    		.addComponent(lReqs)
	    		.addComponent(reqScroller)
	    		.addComponent(submit)
	    		);
	    layout.linkSize(SwingConstants.VERTICAL,new java.awt.Component[]{lFirst,tfFirst});
	    layout.linkSize(SwingConstants.VERTICAL,new java.awt.Component[]{lLast,tfLast});
	    layout.linkSize(SwingConstants.VERTICAL,new java.awt.Component[]{lUser,tfUser});
	    layout.linkSize(SwingConstants.VERTICAL,new java.awt.Component[]{lPass,tfPass});


	    pack();
	}
	
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		if(rbStudent.isSelected()) {
			lReqs.setVisible(true);
			reqScroller.setVisible(true);
			if(profile == null) taReqs.setText(reqs);
			lDegree.setVisible(true);
			rbUndergrad.setVisible(true);
			rbGrad.setVisible(true);
		}
		else {
			lReqs.setVisible(false);
			reqScroller.setVisible(false);
			reqs = taReqs.getText();
			lDegree.setVisible(false);
			rbUndergrad.setVisible(false);
			rbGrad.setVisible(false);
		}
		pack();
	}
	
	private void submitPressed() {
		error.setType(ThemedLabel.LabelType.Error);
		problem = false;
		String first = tfFirst.getText();
		String last = tfLast.getText();
		String user = tfUser.getText();
		String pass = String.valueOf(tfPass.getPassword());
		ProfileController pc = new ProfileController(pm, ProfileController.PROFILE_FILE_NAME);
		if(rbStudent.isSelected()) {
			try {
				reqs = taReqs.getText();
				Student.Degree deg = (rbUndergrad.isSelected()) ? Student.Degree.UNDERGRAD : Student.Degree.GRADUATE;
				if(profile == null) {
					pc.addStudentToSystem(user, pass, first, last, reqs,deg);
				}
				else {
					pc.modifyStudent(user, pass, first, last, reqs, deg);
					dispose();
				}
				error.setText("Student " + user + " created.");
				reqs = "";
				taReqs.setText("");
				error.setType(ThemedLabel.LabelType.Success);
			} catch (InputException e) {
				error.setText(e.getMessage());
				problem = true;
			}
			refreshData();
		}
		else if(rbInstructor.isSelected()) {
			try {
				if(profile == null) pc.addInstructorToSystem(user, pass, first, last);
				else {
					pc.modifyInstructor(user, pass, first, last);
					dispose();
				}
				error.setText("Instructor " + user + " created.");
				error.setType(ThemedLabel.LabelType.Success);
			} catch (InputException e) {
				error.setText(e.getMessage());
				problem = true;
			}
			refreshData();
		}
		else if(rbAdmin.isSelected()){
			try {
				if(profile == null) pc.addAdminToSystem(user, pass, first, last);
				else {
					pc.modifyAdmin(user, pass, first, last);
					dispose();
				}
				error.setText("Admin " + user + " created.");
				error.setType(ThemedLabel.LabelType.Success);
			} catch (InputException e) {
				error.setText(e.getMessage());
				problem = true;
			}
			refreshData();
		}
		else {
			error.setText("Please chose profile type");
			problem = true;
		}
	}
	
	private void refreshData() {
		if(!problem) {
			tfFirst.setText("");
			tfLast.setText("");
			tfUser.setText("");
			tfPass.setText("");
		}
		pack();
	}

}
