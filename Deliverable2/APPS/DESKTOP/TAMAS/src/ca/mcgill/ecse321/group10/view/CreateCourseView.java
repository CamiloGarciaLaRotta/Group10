package ca.mcgill.ecse321.group10.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.controller.CourseController;
import ca.mcgill.ecse321.group10.controller.ProfileController;

public class CreateCourseView extends JFrame{
	
	private CourseManager cm;
	private ProfileManager pm;
	
	private JList instructorList;
	private JScrollPane listScroller;
	private JLabel lName;
	private JLabel lCode;
	private JLabel lGraderBudget;
	private JLabel lTABudget;
	private JLabel errorLabel;
	private JTextField tfName;
	private JTextField tfCode;
	private JTextField tfGraderBudget;
	private JTextField tfTABudget;
	private JButton create;
	
	private String error;
	
	public CreateCourseView(CourseManager cm, ProfileManager pm) {
		this.cm = cm;
		this.pm = pm;
		error = "";
		initComponents();
	}
	
	private void initComponents() {
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Create Course");
		String [] instructorNames = new String[pm.getInstructors().size()];
		for(int c = 0; c < instructorNames.length; c++) {
			instructorNames[c] = pm.getInstructor(c).getFirstName() + " " + pm.getInstructor(c).getLastName();
		}
		instructorList = new JList(instructorNames);
		instructorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		instructorList.setLayoutOrientation(JList.VERTICAL);
		listScroller = new JScrollPane(instructorList);
		
		lName = new JLabel();
		lCode = new JLabel();
		lGraderBudget = new JLabel();
		lTABudget = new JLabel();
		tfName = new JTextField();
		tfCode = new JTextField();
		tfGraderBudget = new JTextField();
		tfTABudget = new JTextField();
		create = new JButton();
		
		create.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				createPressed();
			}
		});
		
		errorLabel = new JLabel();
		errorLabel.setText("");
		
		lName.setText("Course Name: ");
		lCode.setText("CDN: ");
		lGraderBudget.setText("Grader Time Budget (hrs): ");
		lTABudget.setText("TA Time Budget (hrs): ");
		create.setText("Create Course");
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	    layout.setHorizontalGroup(
	    		layout.createParallelGroup()
	    		.addComponent(errorLabel)
	    		.addComponent(listScroller)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lName)
	    				.addComponent(tfName, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lCode)
	    				.addComponent(tfCode, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lGraderBudget)
	    				.addComponent(tfGraderBudget, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lTABudget)
	    				.addComponent(tfTABudget, 50, 75, 100)
	    				)
	    		.addComponent(create)
	    		);
	    layout.linkSize(SwingConstants.HORIZONTAL,new java.awt.Component[] {lName,lCode,lTABudget,lGraderBudget});
	    layout.setVerticalGroup(
	    		layout.createSequentialGroup()
	    		.addComponent(errorLabel)
	    		.addComponent(listScroller)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lName)
	    				.addComponent(tfName, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lCode)
	    				.addComponent(tfCode, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lGraderBudget)
	    				.addComponent(tfGraderBudget, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lTABudget)
	    				.addComponent(tfTABudget, 50, 75, 100)
	    				)
	    		.addComponent(create)
	    		);
	    //layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {lName,tfName,lCode,tfCode,lGraderBudget,tfGraderBudget,lTABudget,tfTABudget});
	    pack();
	}
	
	private void refreshData() {
		if(error.length() != 0) {
			errorLabel.setText(error);
			pack();
			return;
		}
		errorLabel.setText("");
		tfName.setText("");
		tfCode.setText("");
		tfGraderBudget.setText("");
		tfTABudget.setText("");
	}
	
	private void createPressed() {
		error = "";
		if(instructorList.getSelectedIndex() == -1) {
			error += "Instructor must be selected!";
			return;
		}
		String name = tfName.getText();
		int code = Integer.parseInt(tfCode.getText());
		float graderBudget = Float.parseFloat(tfGraderBudget.getText());
		float taBudget = Float.parseFloat(tfTABudget.getText());
		CourseController cc = new CourseController(cm);
		cc.createCourse(name, code, graderBudget, taBudget);
		ProfileController pc = new ProfileController(pm);
		pc.addCourseToInstructor(instructorList.getSelectedIndex(), cm.getCourse(cm.getCourses().size()-1));
		refreshData();
	}

}
