package ca.mcgill.ecse321.group10.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;

public class PublishJobView extends JFrame{

	private ApplicationManager am;
	private ProfileManager pm;
	private CourseManager cm;
	
	private JList instructorList;
	private JList courseList;
	private JScrollPane instructorScroller;
	private JScrollPane courseScroller;
	private JSpinner jStartTime;
	private JSpinner jEndTime;
	private JLabel lStart;
	private JLabel lEnd;
	private JLabel lSalary;
	private JLabel lReqs;
	private JLabel errorLabel;
	private JTextField tfSalary;
	private JTextField tfReqs;
	private JButton publish;
	
	private String error;
	
	public PublishJobView(ApplicationManager am, ProfileManager pm, CourseManager cm) {
		this.am = am;
		this.pm = pm;
		this.cm = cm;
		error = "";
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Publish Job Posting");
		lStart = new JLabel("Start Time: ");
		lEnd = new JLabel("End Time: ");
		lSalary = new JLabel("Salary: ");
		lReqs = new JLabel("Requirements: ");
		tfSalary = new JTextField();
		tfReqs = new JTextField();
		errorLabel = new JLabel();
		publish = new JButton("Publish Job");
		String [] instructorNames = new String[pm.getInstructors().size()];
		for(int c = 0; c < instructorNames.length; c++) {
			instructorNames[c] = pm.getInstructor(c).getFirstName() + " " + pm.getInstructor(c).getLastName();
		}
		instructorList = new JList(instructorNames);
		instructorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		instructorList.setLayoutOrientation(JList.VERTICAL);
		instructorScroller = new JScrollPane(instructorList);
		String [] courseNames = {};
		courseList = new JList(courseNames);
		courseScroller = new JScrollPane(courseList);
		
		jStartTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor startEditor = new JSpinner.DateEditor(jStartTime, "HH:mm");
		jStartTime.setEditor(startEditor);
		
		jEndTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor endEditor = new JSpinner.DateEditor(jEndTime, "HH:mm");
		jEndTime.setEditor(endEditor);
		
		GroupLayout layout = new GroupLayout(getContentPane());
	    getContentPane().setLayout(layout);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    layout.setHorizontalGroup(
	    		layout.createParallelGroup()
	    		.addComponent(errorLabel)
	    		.addComponent(instructorList)
	    		.addComponent(courseList)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lSalary)
	    				.addComponent(tfSalary,50,75,100)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lReqs)
	    				.addComponent(tfReqs, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lStart)
	    				.addComponent(jStartTime, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lEnd)
	    				.addComponent(jEndTime, 50, 75, 100)
	    				)
	    		.addComponent(publish)
	    		);
	    layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]{lStart,lEnd,lSalary,lReqs});
	    layout.setHorizontalGroup(
	    		layout.createParallelGroup()
	    		.addComponent(errorLabel)
	    		.addComponent(instructorList)
	    		.addComponent(courseList)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lSalary)
	    				.addComponent(tfSalary,50,75,100)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lReqs)
	    				.addComponent(tfReqs, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lStart)
	    				.addComponent(jStartTime, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createSequentialGroup()
	    				.addComponent(lEnd)
	    				.addComponent(jEndTime, 50, 75, 100)
	    				)
	    		.addComponent(publish)
	    		);
	}
	
}
