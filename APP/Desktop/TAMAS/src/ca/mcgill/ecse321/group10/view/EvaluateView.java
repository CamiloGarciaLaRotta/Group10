package ca.mcgill.ecse321.group10.view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.mcgill.ecse321.group10.TAMAS.model.Application;
import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Instructor;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import widgets.ThemedLabel;
import widgets.ThemedPanel;
import widgets.ThemedTextArea;

public class EvaluateView extends JFrame{
	
	private ApplicationManager am;
	private ProfileManager pm;
	
	private JLabel lJob;
	private JLabel lStudent;
	private ThemedLabel lMessage;
	private JTextArea tfEval;
	private JScrollPane evalScroller;
	private JButton submitButton;
	private JComboBox<String> instructorList;
	private JComboBox<String> applicationList;
	
	private ArrayList<Application> applications;
	private List<Instructor> instructors;
	
	private Instructor instructor;
	private Student student;
	
	public EvaluateView(ApplicationManager am, ProfileManager pm, Instructor instructor) {
		this.am = am;
		this.pm = pm;
		this.instructor = instructor;
		initComponents();
	}
	
	private void initComponents() {
		applications = new ArrayList<Application>();
		lJob = new ThemedLabel("Select job:");
		lStudent = new ThemedLabel("Student name: ");
		tfEval = new ThemedTextArea();
		evalScroller = new JScrollPane(tfEval);
		
		lMessage = new ThemedLabel("",ThemedLabel.LabelType.Success);
		
		student = null;
		
		instructors = pm.getInstructors();
		instructorList = new JComboBox<String>();
		applicationList = new JComboBox<String>();
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for(int c = 0; c < instructors.size(); c++) {
			model.addElement(instructors.get(c).getUsername());
		}
		instructorList.setModel(model);
		
		submitButton = new JButton("Submit Evaluation");
		
		instructorList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e ){ 
				refreshApplications();
			}
		});
		
		applicationList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				refreshStudentName();
			}
		});
		
		submitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Instructor curInstructor;
				Job curJob = applications.get(applicationList.getSelectedIndex()).getJobs();
				if(instructor == null) curInstructor = instructors.get(instructorList.getSelectedIndex());
				else curInstructor = instructor;
				String eval = "From " + curInstructor.getFirstName() + " " + curInstructor.getLastName() + "\n";
				eval += "Regarding performance as " + curJob.getPositionFullName() + " for " + curJob.getCourse().getClassName() + "(" + curJob.getDay() + "):\n\n";
				eval += tfEval.getText();
				eval += "\n**********************************\n\n";
				ProfileController pc = new ProfileController(pm, ProfileController.PROFILE_FILE_NAME);
				pc.addStudentEvaluation(student, eval);
				tfEval.setText("");
				lMessage.setType(ThemedLabel.LabelType.Success);
				lMessage.setText("Evaluation sent");
				pack();
			}
		});
		
		tfEval.setMinimumSize(new Dimension(150,200));
		tfEval.setRows(10);
		tfEval.setWrapStyleWord(true);
		tfEval.setLineWrap(true);
		
		refreshApplications();
		
		JPanel panel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(lMessage);
		if(instructor == null) panel.add(instructorList);
		panel.add(lJob);
		panel.add(applicationList);
		panel.add(lStudent);
		panel.add(evalScroller);
		panel.add(submitButton);
		
		lJob.setAlignmentX(Component.LEFT_ALIGNMENT);
		applicationList.setAlignmentX(Component.LEFT_ALIGNMENT);
		lMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
		instructorList.setAlignmentX(Component.LEFT_ALIGNMENT);
		lStudent.setAlignmentX(Component.LEFT_ALIGNMENT);
		evalScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
		submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		this.add(panel);
		pack();
	}
	
	private void refreshApplications() {
		Instructor curInstructor;
		if(instructor == null) curInstructor = instructors.get(instructorList.getSelectedIndex());
		else curInstructor = instructor;
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		
		applications.clear();
		
		for(int c = 0; c < am.getApplications().size(); c++) {
			if(am.getApplication(c).getJobs().getInstructor().getUsername().equals(curInstructor.getUsername())) {
				if(am.getApplication(c).getOfferAccepted()) {
					applications.add(am.getApplication(c));
					model.addElement(am.getApplication(c).getJobs().getCourse().getClassName() + " - " + am.getApplication(c).getJobs().getPositionFullName() + " " + am.getApplication(c).getJobs().getDay());
				}
			}
		}
		applicationList.setModel(model);
		refreshStudentName();
		pack();
	}
	
	private void refreshStudentName() {
		lMessage.setText("");
		if(applicationList.getSelectedIndex() == -1) {
			submitButton.setEnabled(false);
			lMessage.setType(ThemedLabel.LabelType.Error);
			lMessage.setText("A student must be selected.");
			pack();
			return;
		}
		submitButton.setEnabled(true);
		student = applications.get(applicationList.getSelectedIndex()).getStudent();
		String sname = student.getFirstName() + " " + student.getLastName();
		lStudent.setText("Student name: " + sname);
		pack();
	}

}
