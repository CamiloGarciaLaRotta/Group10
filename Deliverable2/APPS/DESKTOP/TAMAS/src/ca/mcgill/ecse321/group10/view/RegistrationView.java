package ca.mcgill.ecse321.group10.view;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.controller.ProfileController;

public class RegistrationView extends JFrame{
	
	private ProfileManager pm;
	
	JLabel error;
	
	JRadioButton rbStudent, rbInstructor, rbAdmin;
	ButtonGroup radioGroup;
	JLabel lRole;
	
	JLabel lFirst, lLast, lUser, lPass;
	JTextField tfFirst, tfLast, tfUser, tfPass;
	JButton submit;
	
	public RegistrationView(ProfileManager pm) {
		this.pm = pm;
		initComponents();
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
		
		error.setText("");
		rbStudent.setText("Student");
		rbInstructor.setText("Instructor");
		rbAdmin.setText("Admin");
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
	    		.addComponent(submit)
	    		);

	    pack();
	}
	
	private void submitPressed() {
		String first = tfFirst.getText();
		String last = tfLast.getText();
		String user = tfUser.getText();
		String pass = tfPass.getText();
		ProfileController pc = new ProfileController(pm);
		if(rbStudent.isSelected()) {
			pc.addStudentToSystem(user, pass, first, last, "");
			error.setText("Student " + user + " created.");
			refreshData();
		}
		else if(rbInstructor.isSelected()) {
			pc.addInstructorToSystem(user, pass, first, last);
			error.setText("Instructor " + user + " created.");
			refreshData();
		}
		else if(rbAdmin.isSelected()){
			pc.addAdminToSystem(user, pass, first, last);
			error.setText("Admin " + user + " created.");
			refreshData();
		}
		else {
			error.setText("Please chose profile type");
		}
	}
	
	private void refreshData() {
		tfFirst.setText("");
		tfLast.setText("");
		tfUser.setText("");
		tfPass.setText("");
	}

}
