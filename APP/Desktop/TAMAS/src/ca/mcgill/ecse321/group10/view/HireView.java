package ca.mcgill.ecse321.group10.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Instructor;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import widgets.ThemedLabel;
import widgets.ThemedList;
import widgets.ThemedPanel;

public class HireView extends JFrame{
	
	private ApplicationManager am;
	private ProfileManager pm;
	
	private JLabel lInstructor;
	private JLabel lJob;
	private JLabel lApplicant;
	private ThemedLabel message;
	private JComboBox cbInstructor;
	private JComboBox cbJob;
	private JList applicantList;
	private JScrollPane applicantScroller;
	private JButton hireButton;
	
	private List<Instructor> instructors;
	private List<Job> jobs;
	private List<Student> applicants;
	
	public HireView(ApplicationManager am, ProfileManager pm) {
		super("Choose Students to Hire");
		this.am = am;
		this.pm = pm;
		initComponents();
	}
	
	private void initComponents() {
		instructors = pm.getInstructors();
		jobs = new ArrayList<Job>();
		
		for(int c = 0; c < am.getJobs().size(); c++) {
			if(am.getJob(c).getInstructor().getUsername().equals(pm.getInstructor(0).getUsername())) {
				jobs.add(am.getJob(c));
			}
		}
		
		String[] jobNames = new String[jobs.size()];
		for(int c = 0; c < jobNames.length; c++) {
			jobNames[c] = jobs.get(c).getCourse().getClassName() + " " + 
							jobs.get(c).getPositionFullName() + " (" + jobs.get(c).getCourse().getCdn() + ")";
		}

		message = new ThemedLabel("", ThemedLabel.LabelType.Error);
		String [] instructorNames = new String[instructors.size()];
		for(int c = 0; c < instructorNames.length; c++) 
			instructorNames[c] = instructors.get(c).getFirstName() + " " + instructors.get(c).getLastName();
		lInstructor = new ThemedLabel("Select Instructor:");
		lJob = new ThemedLabel("Select Job:");
		lApplicant = new ThemedLabel("Choose Applicant:");
		
		cbInstructor = new JComboBox(instructorNames);
		cbJob = new JComboBox(jobNames);
		
		cbInstructor.addActionListener(
				new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						Instructor i = instructors.get(cbInstructor.getSelectedIndex());
						jobs.clear();
						for(int c = 0; c < am.getJobs().size(); c++) {
							if(am.getJob(c).getInstructor().getUsername().equals(instructors.get(cbInstructor.getSelectedIndex()).getUsername())) {
								jobs.add(am.getJob(c));
							}
						}
						String[] jobNames = new String[jobs.size()];
						for(int c = 0; c < jobNames.length; c++) {
							jobNames[c] = jobs.get(c).getCourse().getClassName() + " " + 
							jobs.get(c).getPositionFullName() + " (" + jobs.get(c).getCourse().getCdn() + ")";
						}
						cbJob.setModel(new DefaultComboBoxModel(jobNames));
						
						applicants.clear();
						DefaultListModel model = new DefaultListModel();
						if(jobs.get(cbJob.getSelectedIndex()).getOfferSent()){
							model.addElement("Offer already sent.");
							applicantList.setModel(model);
							applicantList.setEnabled(false);
						}
						else {
							for(int c = 0; c < am.getApplications().size(); c++) {
								//System.out.println(am.getApplication(c).getJobs().toString() + "====" + am.getJob(cbJob.getSelectedIndex()).toString());
								if(am.getApplication(c).getJobs().toString().equals(jobs.get(cbJob.getSelectedIndex()).toString())) {
									applicants.add(am.getApplication(c).getStudent());
									System.out.println("added");
								}
							}
							System.out.println("===");
							for(int c = 0; c < applicants.size(); c++) {
								model.addElement(applicants.get(c).getFirstName() + " " + applicants.get(c).getLastName());
							}
							applicantList.setModel(model);
							applicantList.setEnabled(true);
						}
					}
				}
		);
		
		cbJob.addActionListener(
				new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						applicants.clear();
						DefaultListModel model = new DefaultListModel();
						if(jobs.get(cbJob.getSelectedIndex()).getOfferSent()){
							model.addElement("Offer already sent.");
							applicantList.setModel(model);
							applicantList.setEnabled(false);
						}
						else {
							for(int c = 0; c < am.getApplications().size(); c++) {
								//System.out.println(am.getApplication(c).getJobs().toString() + "====" + am.getJob(cbJob.getSelectedIndex()).toString());
								if(am.getApplication(c).getJobs().toString().equals(jobs.get(cbJob.getSelectedIndex()).toString())) {
									applicants.add(am.getApplication(c).getStudent());
									System.out.println("added");
								}
							}
							System.out.println("===");
							for(int c = 0; c < applicants.size(); c++) {
								model.addElement(applicants.get(c).getFirstName() + " " + applicants.get(c).getLastName());
							}
							applicantList.setModel(model);
							applicantList.setEnabled(true);
						}
					}
				}
		);

		
		applicants = new ArrayList<Student>();
		boolean initSent = false;
		DefaultListModel model = new DefaultListModel();
		if(jobs.get(cbJob.getSelectedIndex()).getOfferSent()){
			model.addElement("Offer already sent.");
			initSent = true;
		}
		else {
			for(int c = 0; c < am.getApplications().size(); c++) {
				//System.out.println(am.getApplication(c).getJobs().toString() + "====" + am.getJob(cbJob.getSelectedIndex()).toString());
				if(am.getApplication(c).getJobs().toString().equals(am.getJob(cbJob.getSelectedIndex()).toString())) {
					applicants.add(am.getApplication(c).getStudent());
					System.out.println("added");
				}
			}
			System.out.println("===");
			for(int c = 0; c < applicants.size(); c++) {
				model.addElement(applicants.get(c).getFirstName() + " " + applicants.get(c).getLastName());
			}
		}
		
		applicantList = new ThemedList(model);
		if(initSent) applicantList.setEnabled(false);
		applicantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		applicantList.setLayoutOrientation(JList.VERTICAL);
		applicantScroller = new JScrollPane(applicantList);
		
		hireButton = new JButton("Hire");
		hireButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				hirePerson();
			}
		});
		
		JPanel panel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(message);
		panel.add(lInstructor);
		panel.add(cbInstructor);
		panel.add(lJob);
		panel.add(cbJob);
		panel.add(lApplicant);
		panel.add(applicantScroller);
		panel.add(hireButton);
		panel.setMinimumSize(new Dimension(200,400));
		this.add(panel);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(200,400));
		pack();
	}
	
	private void hirePerson() {
		String error = "";
		if(cbInstructor.getSelectedIndex() == -1) {
			error += "Instructor must be selected!";
		}
		if(cbJob.getSelectedIndex() == -1) {
			error += "Job must be selected!";
		}
		if(applicantList.getSelectedIndex() == -1) {
			error += "Student must be selected!";
		}
		if(error.length() != 0) {
			message.setType(ThemedLabel.LabelType.Error);
			message.setText(error);
		}
		else {
			jobs.get(cbJob.getSelectedIndex()).setOfferSent(true);
			Student s = applicants.get(applicantList.getSelectedIndex());
			new ApplicationController(am, ApplicationController.APPLICATION_FILE_NAME).persist();
			for(int c = 0; c < pm.getStudents().size(); c++) {
				if(pm.getStudent(c).getUsername().equals(s.getUsername())) {
					System.out.println("FOUND!");
					pm.getStudent(c).addJob(jobs.get(cbJob.getSelectedIndex()));
					break;
				}
			}
			new ProfileController(pm, ProfileController.PROFILE_FILE_NAME).persist();
			message.setType(ThemedLabel.LabelType.Success);
			message.setText("Job offer sent!");
		}
		refreshData();
	}
	
	private void refreshData() {
		Instructor i = pm.getInstructor(cbInstructor.getSelectedIndex());
		jobs.clear();
		for(int c = 0; c < am.getJobs().size(); c++) {
			if(am.getJob(c).getInstructor().getUsername().equals(pm.getInstructor(cbInstructor.getSelectedIndex()).getUsername())) {
				jobs.add(am.getJob(c));
			}
		}
		String[] jobNames = new String[jobs.size()];
		for(int c = 0; c < jobNames.length; c++) {
			jobNames[c] = jobs.get(c).getCourse().getClassName() + " " + 
			jobs.get(c).getPositionFullName() + " (" + jobs.get(c).getCourse().getCdn() + ")";
		}
		cbJob.setModel(new DefaultComboBoxModel(jobNames));
		
		applicants.clear();
		DefaultListModel model = new DefaultListModel();
		if(jobs.get(cbJob.getSelectedIndex()).getOfferSent()){
			model.addElement("Offer already sent.");
			applicantList.setModel(model);
			applicantList.setEnabled(false);
		}
		else {
			for(int c = 0; c < am.getApplications().size(); c++) {
				//System.out.println(am.getApplication(c).getJobs().toString() + "====" + am.getJob(cbJob.getSelectedIndex()).toString());
				if(am.getApplication(c).getJobs().toString().equals(jobs.get(cbJob.getSelectedIndex()).toString())) {
					applicants.add(am.getApplication(c).getStudent());
					System.out.println("added");
				}
			}
			System.out.println("===");
			for(int c = 0; c < applicants.size(); c++) {
				model.addElement(applicants.get(c).getFirstName() + " " + applicants.get(c).getLastName());
			}
			applicantList.setModel(model);
			applicantList.setEnabled(true);
		}
		pack();
	}
}
