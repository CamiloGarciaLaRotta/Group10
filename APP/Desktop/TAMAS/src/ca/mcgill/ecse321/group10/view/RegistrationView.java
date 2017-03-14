package ca.mcgill.ecse321.group10.view;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.controller.ProfileController;

public class RegistrationView extends JFrame implements java.awt.event.ActionListener{
	
	private ProfileManager pm;
	
	JLabel error;
	
	JRadioButton rbStudent, rbInstructor, rbAdmin;
	ButtonGroup radioGroup;
	JLabel lRole;
	
	JLabel lFirst, lLast, lUser, lPass;
	JTextField tfFirst, tfLast, tfUser, tfPass;
	JButton submit;
	
	JTextArea taReqs;
	JLabel lReqs;
	String reqs;
	JScrollPane reqScroller;
	
	boolean problem;
	
	public RegistrationView(ProfileManager pm) {
		this.pm = pm;
		reqs = "";
		initComponents();
		problem = false;
	}
	
	private void initComponents() {
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Register a Profile");
		error = new JLabel();
		rbStudent = new JRadioButton();
		rbInstructor = new JRadioButton();
		rbAdmin = new JRadioButton();
		radioGroup = new ButtonGroup();
		lRole = new JLabel();
		lFirst = new JLabel();
		lLast = new JLabel();
		lUser = new JLabel();
		lPass = new JLabel();
		tfFirst = new JTextField();
		tfLast = new JTextField();
		tfUser = new JTextField();
		tfPass = new JTextField();
		submit = new JButton();
		
		lReqs = new JLabel("Skills: ");
		lReqs.setVisible(false);
		taReqs = new JTextArea();
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
		
		submit.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				submitPressed();
			}
		});
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
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
	    /*
	    layout.setVerticalGroup(
	    		layout.createSequentialGroup()
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
	    				.addComponent(lFirst)
	    				.addComponent(tfFirst,50,75,100)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lLast)
	    				.addComponent(tfLast,200,200,400)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lUser)
	    				.addComponent(tfUser,200,200,400)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lPass)
	    				.addComponent(tfPass,200,200,400)
	    				)
	    		.addComponent(submit)
	    		);
	    pack();
	    */
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
			taReqs.setText(reqs);
		}
		else {
			lReqs.setVisible(false);
			reqScroller.setVisible(false);
			reqs = taReqs.getText();
		}
		pack();
	}
	
	private void submitPressed() {
		problem = false;
		String first = tfFirst.getText();
		String last = tfLast.getText();
		String user = tfUser.getText();
		String pass = tfPass.getText();
		ProfileController pc = new ProfileController(pm, ProfileController.PROFILE_FILE_NAME);
		if(rbStudent.isSelected()) {
			try {
				reqs = taReqs.getText();
				pc.addStudentToSystem(user, pass, first, last, reqs);
				error.setText("Student " + user + " created.");
				reqs = "";
				taReqs.setText("");
			} catch (InputException e) {
				error.setText(e.getMessage());
				problem = true;
			}
			refreshData();
		}
		else if(rbInstructor.isSelected()) {
			try {
				pc.addInstructorToSystem(user, pass, first, last);
				error.setText("Instructor " + user + " created.");
			} catch (InputException e) {
				error.setText(e.getMessage());
				problem = true;
			}
			refreshData();
		}
		else if(rbAdmin.isSelected()){
			try {
				pc.addAdminToSystem(user, pass, first, last);
				error.setText("Admin " + user + " created.");
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
