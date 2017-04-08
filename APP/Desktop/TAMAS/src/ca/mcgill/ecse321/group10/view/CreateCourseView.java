package ca.mcgill.ecse321.group10.view;

import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.controller.CourseController;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;
import widgets.Constants;
import widgets.ThemedLabel;
import widgets.ThemedList;
import widgets.ThemedTextField;

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
		instructorList = new ThemedList(instructorNames);
		instructorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		instructorList.setLayoutOrientation(JList.VERTICAL);
		listScroller = new JScrollPane(instructorList);
		
		lName = new ThemedLabel("");
		lCode = new ThemedLabel("");
		lGraderBudget = new ThemedLabel("");
		lTABudget = new ThemedLabel("");
		tfName = new ThemedTextField();
		tfCode = new ThemedTextField();
		tfGraderBudget = new ThemedTextField();
		tfTABudget = new ThemedTextField();
		create = new JButton();
		
		create.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				createPressed();
			}
		});
		
		errorLabel = new ThemedLabel("",ThemedLabel.LabelType.Error);
		
		lName.setText("Course Name: ");
		lCode.setText("CDN: ");
		lGraderBudget.setText("Grader Time Budget (hrs): ");
		lTABudget.setText("TA Time Budget (hrs): ");
		create.setText("Create Course");
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
		if(constants.get(1) == 0) getContentPane().setBackground(Constants.dark_bgColor);
		else getContentPane().setBackground(Constants.light_bgColor);
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
		pack();
	}
	
	private void createPressed() {
		error = "";
		if(instructorList.getSelectedIndex() == -1) {
			error += "Instructor must be selected!";
			refreshData();
			return;
		}
		String name = tfName.getText();
		try {
			int code = Integer.parseInt(tfCode.getText());
			float graderBudget = Float.parseFloat(tfGraderBudget.getText());
			float taBudget = Float.parseFloat(tfTABudget.getText());
			CourseController cc = new CourseController(cm, CourseController.COURSE_FILE_NAME);
			try {
				cc.createCourse(name, code, graderBudget, taBudget);
				ProfileController pc = new ProfileController(pm, ProfileController.PROFILE_FILE_NAME);
				pc.addCourseToInstructor(instructorList.getSelectedIndex(), cm.getCourse(cm.getCourses().size()-1));
			} catch (InputException e) {
				error += e.getMessage() + "\n";
			}
		} catch(Exception e) {
			error += "Invalid course code, TA budget, or grader budget";
		}
		
		refreshData();
	}

}
