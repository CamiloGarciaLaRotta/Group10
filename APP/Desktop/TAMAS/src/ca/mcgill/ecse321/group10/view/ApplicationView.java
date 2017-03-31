package ca.mcgill.ecse321.group10.view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import widgets.ThemedLabel;
import widgets.ThemedList;
import widgets.ThemedPanel;

public class ApplicationView extends JFrame{
	
	private ApplicationManager am;
	private ProfileManager pm;
	
	private JList studentList;
	private JList jobList;
	private JScrollPane studentScroller;
	private JScrollPane jobScroller;
	private JLabel lStudent;
	private JLabel lJob;
	private JButton apply;
	private ThemedLabel message;
	
	public ApplicationView(ApplicationManager am, ProfileManager pm) {
		this.am = am;
		this.pm = pm;
		initComponents();
	}
	
	private void initComponents() {
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Apply for a Job");
		
		String [] studentNames = new String[pm.getStudents().size()];
		for(int c = 0; c < studentNames.length; c++) {
			studentNames[c] = pm.getStudent(c).getUsername();
		}
		studentList = new ThemedList(studentNames);
		studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		studentList.setLayoutOrientation(JList.VERTICAL);
		studentScroller = new JScrollPane(studentList);
		
		String [] jobNames = new String[am.getJobs().size()];
		for(int c = 0; c < jobNames.length; c++) {
			jobNames[c] = am.getJob(c).getCourse().getClassName() + ": " + am.getJob(c).getId() + " - " + am.getJob(c).getPositionFullName();
		}
		jobList = new ThemedList(jobNames);
		jobList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jobList.setLayoutOrientation(JList.VERTICAL);
		jobScroller = new JScrollPane(jobList);
		
		lStudent = new ThemedLabel("Find your username below:");
		lJob = new ThemedLabel("Choose job:");
		
		message = new ThemedLabel("",ThemedLabel.LabelType.Success);
		
		apply = new JButton("Apply");
		
		apply.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				applyPressed();
			}
		});
		
		JPanel panel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(message);
		panel.add(lStudent);
		panel.add(studentScroller);
		panel.add(lJob);
		panel.add(jobScroller, null);
		panel.add(apply);
		add(panel);
		pack();
	}
	
	private void applyPressed() {
		String error = "";
		if(studentList.getSelectedIndex() == -1) error += "Student must be selected!";
		if(jobList.getSelectedIndex() == -1) error += "Job must be selected!";
		if(error.length() != 0) {
			message.setType(ThemedLabel.LabelType.Error);
			message.setText(error);
		}
		else {
			message.setType(ThemedLabel.LabelType.Success);
			Student student = pm.getStudent(studentList.getSelectedIndex());
			Job job = am.getJob(jobList.getSelectedIndex());
			ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
			ac.createApplication(student, job);
			String msg = student.getUsername() + " has applied to " + jobList.getSelectedValue().toString() + ". Good luck!";
			message.setText(msg);
			pack();
		}
	}

}
