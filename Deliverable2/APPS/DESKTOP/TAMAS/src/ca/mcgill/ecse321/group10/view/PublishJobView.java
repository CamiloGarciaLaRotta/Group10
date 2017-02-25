package ca.mcgill.ecse321.group10.view;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.swing.DefaultListModel;
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
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Course;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Instructor;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.controller.ApplicationController;

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
	private JSpinner jDay;
	private JLabel lStart;
	private JLabel lEnd;
	private JLabel lSalary;
	private JLabel lReqs;
	private JLabel errorLabel;
	private JTextField tfSalary;
	private JTextField tfReqs;
	private JButton publish;
	
	private DefaultListModel courseListModel;
	
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
		
		publish.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				publishPressed();
			}
		});
		String [] instructorNames = new String[pm.getInstructors().size()];
		for(int c = 0; c < instructorNames.length; c++) {
			instructorNames[c] = pm.getInstructor(c).getFirstName() + " " + pm.getInstructor(c).getLastName();
		}
		instructorList = new JList(instructorNames);
		instructorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		instructorList.setLayoutOrientation(JList.VERTICAL);
		instructorScroller = new JScrollPane(instructorList);
		String [] courseNames = {};
		courseListModel = new DefaultListModel();
		courseList = new JList(courseListModel);
		courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		courseList.setLayoutOrientation(JList.VERTICAL);
		courseScroller = new JScrollPane(courseList);
		
		instructorList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) return;
				String[] courseNames;
				if(instructorList.getSelectedIndex() == -1) courseNames = new String[0];
				else {
					courseListModel.clear();
					List<Course> courses = pm.getInstructor(instructorList.getSelectedIndex()).getCourses();
					for(int c = 0;c < courses.size(); c++) {
						courseListModel.addElement(courses.get(c).getClassName());
					}
				}
			}
		});
		
		jStartTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor startEditor = new JSpinner.DateEditor(jStartTime, "HH:mm");
		jStartTime.setEditor(startEditor);
		
		jEndTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor endEditor = new JSpinner.DateEditor(jEndTime, "HH:mm");
		jEndTime.setEditor(endEditor);
		
		String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		jDay = new JSpinner(new SpinnerListModel(days));
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
	    getContentPane().setLayout(layout);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    layout.setHorizontalGroup(
	    		layout.createParallelGroup()
	    		.addComponent(errorLabel)
	    		.addComponent(instructorScroller)
	    		.addComponent(courseScroller)
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
	    		.addComponent(jDay)
	    		.addComponent(publish)
	    		);
	    layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]{lStart,lEnd,lSalary,lReqs});
	    layout.setVerticalGroup(
	    		layout.createSequentialGroup()
	    		.addComponent(errorLabel)
	    		.addComponent(instructorScroller)
	    		.addComponent(courseScroller)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lSalary)
	    				.addComponent(tfSalary,50,75,100)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lReqs)
	    				.addComponent(tfReqs, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lStart)
	    				.addComponent(jStartTime, 50, 75, 100)
	    				)
	    		.addGroup(
	    				layout.createParallelGroup()
	    				.addComponent(lEnd)
	    				.addComponent(jEndTime, 50, 75, 100)
	    				)
	    		.addComponent(jDay)
	    		.addComponent(publish)
	    		);
	    layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[]{lSalary,lReqs,tfSalary,tfReqs});
	    layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[]{lStart,lEnd,jStartTime,jEndTime});

	    pack();
	}
	
	private void refreshData() {
		errorLabel.setText(error);
		if(error.length() != 0) return;
		tfSalary.setText("");
		tfReqs.setText("");
		jDay.setValue("Monday");
		jStartTime.setValue(new java.util.Date());
		jEndTime.setValue(new java.util.Date());
	}
	
	private void publishPressed() {
		error = "";
		if(instructorList.getSelectedIndex() == -1) error += "Instructor must be specified!\n";
		if(courseList.getSelectedIndex() == -1) error += "Course must be specified!\n";
		double salary = Double.parseDouble(tfSalary.getText());
		String requirements = tfReqs.getText();
		String day = jDay.getName();
		Instructor instructor = pm.getInstructor(instructorList.getSelectedIndex());
		Course course = instructor.getCourse(courseList.getSelectedIndex());
		Time startTime = new Time(((java.util.Date)jStartTime.getValue()).getTime());
		Time endTime = new Time(((java.util.Date)jEndTime.getValue()).getTime());
		
		if(error.length() == 0) {
			ApplicationController ac = new ApplicationController(am);
			ac.addJobToSystem(startTime, endTime, day, salary, requirements, course, instructor);
		}
		refreshData();
	}
	
}
