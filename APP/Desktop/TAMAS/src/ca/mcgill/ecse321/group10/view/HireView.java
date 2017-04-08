package ca.mcgill.ecse321.group10.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.mcgill.ecse321.group10.TAMAS.model.Application;
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
	private JLabel lOthers;
	private ThemedLabel message;
	private JComboBox cbInstructor;
	private JComboBox cbJob;
	private JList applicantList;
	private JScrollPane applicantScroller;
	private JList othersList;
	private JScrollPane othersScroller;
	private JButton hireButton;
	private JButton hireAllButton;
	
	private List<Instructor> instructors;
	private List<Job> jobs;
	private List<Student> applicants;
	private List<Application> others;
	
	private Instructor instructor;
	
	public HireView(ApplicationManager am, ProfileManager pm, Instructor instructor) {
		super("Choose Students to Hire");
		this.am = am;
		this.pm = pm;
		this.instructor = instructor;
		initComponents();
	}
	
	private void initComponents() {
		instructors = pm.getInstructors();
		jobs = new ArrayList<Job>();
		
		others = new ArrayList<Application>();
		
		lOthers = new ThemedLabel("Student has also applied for:");
		othersList = new ThemedList(new String[0]);
		othersList.setEnabled(false);
		othersScroller = new JScrollPane(othersList);
		
		for(int c = 0; c < am.getJobs().size(); c++) {
			String curUsername;
			if(instructor == null) curUsername = pm.getInstructor(0).getUsername();
			else curUsername = instructor.getUsername();
			if(am.getJob(c).getInstructor().getUsername().equals(curUsername)) {
				jobs.add(am.getJob(c));
			}
		}
		
		String[] jobNames = new String[jobs.size()];
		for(int c = 0; c < jobNames.length; c++) {
			jobNames[c] = jobs.get(c).getCourse().getClassName() + " " + 
							jobs.get(c).getPositionFullName() + " (" + jobs.get(c).getCourse().getCdn() + ")" + "[" + jobs.get(c).getId() + "]" + " " + jobs.get(c).getDay();
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
					public void actionPerformedBad(java.awt.event.ActionEvent e) {
						refreshData();
					}
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if(instructor == null) instructor = instructors.get(cbInstructor.getSelectedIndex());
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
					public void actionPerformedBad(java.awt.event.ActionEvent e) {
						refreshData();
					}
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
		
		othersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		othersList.setLayoutOrientation(JList.VERTICAL);
		
		applicantList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) return;
				refreshOthers();
			}
		});
		
		refreshOthers();
		
		hireButton = new JButton("Hire For This Job");
		hireAllButton = new JButton("Hire for All Listed");
		hireButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				hirePerson();
			}
		});
		hireAllButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				hirePersonAll();
			}
		});
		//hireAllButton.setPreferredSize(new Dimension(this.getWidth()/2,40));

		JPanel panel = new ThemedPanel();
		JPanel buttonPanel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		buttonPanel.setLayout(new FlowLayout());
		panel.add(message);
		if(instructor == null) {
			panel.add(lInstructor);
			panel.add(cbInstructor);
		}
		panel.add(lJob);
		panel.add(cbJob);
		panel.add(lApplicant);
		panel.add(applicantScroller);
		panel.add(lOthers);
		panel.add(othersScroller);
		buttonPanel.add(hireButton);
		buttonPanel.add(hireAllButton);
		panel.add(buttonPanel);
		panel.setMinimumSize(new Dimension(200,400));
		
		message.setAlignmentX(Component.LEFT_ALIGNMENT);
		lInstructor.setAlignmentX(Component.LEFT_ALIGNMENT);
		cbInstructor.setAlignmentX(Component.LEFT_ALIGNMENT);
		lJob.setAlignmentX(Component.LEFT_ALIGNMENT);
		cbJob.setAlignmentX(Component.LEFT_ALIGNMENT);
		lApplicant.setAlignmentX(Component.LEFT_ALIGNMENT);
		applicantScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
		hireButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		hireAllButton.setAlignmentX(Component.LEFT_ALIGNMENT);	
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(panel);
		//this.setVisible(true);
		this.setMinimumSize(new Dimension(200,400));
		pack();
	}
	
	private void hirePersonAll() {
		String error = "";
		if(cbInstructor == null && cbInstructor.getSelectedIndex() == -1) {
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
			Student s = applicants.get(applicantList.getSelectedIndex());
			ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
			ProfileController pc = new ProfileController(pm, ProfileController.PROFILE_FILE_NAME);
			for(int c = 0; c < others.size(); c++) {
				ac.setJobOffered(others.get(c).getJobs(), true);
				pc.offerJobToStudent(s, others.get(c).getJobs());
			}
			message.setType(ThemedLabel.LabelType.Success);
			message.setText("Job offers sent!");
		}
		refreshData();
	}
	
	private void hirePerson() {
		String error = "";
		if(cbInstructor == null && cbInstructor.getSelectedIndex() == -1) {
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
			//jobs.get(cbJob.getSelectedIndex()).setOfferSent(true);
			Student s = applicants.get(applicantList.getSelectedIndex());
			//new ApplicationController(am, ApplicationController.APPLICATION_FILE_NAME).persist();
			//for(int c = 0; c < pm.getStudents().size(); c++) {
		//		if(pm.getStudent(c).getUsername().equals(s.getUsername())) {
			//		System.out.println("FOUND!");
			//		pm.getStudent(c).addJob(jobs.get(cbJob.getSelectedIndex()));
			//		break;
			//	}
			//}
			//new ProfileController(pm, ProfileController.PROFILE_FILE_NAME).persist();
			
			ApplicationController ac = new ApplicationController(am,ApplicationController.APPLICATION_FILE_NAME);
			ProfileController pc = new ProfileController(pm, ProfileController.PROFILE_FILE_NAME);
			ac.setJobOffered(jobs.get(cbJob.getSelectedIndex()), true);
			pc.offerJobToStudent(s, jobs.get(cbJob.getSelectedIndex()));
			message.setType(ThemedLabel.LabelType.Success);
			message.setText("Job offer sent!");
		}
		refreshData();
	}
	
	private void refreshOthers() {
		if(applicantList.getSelectedIndex() == -1) {
			others.clear();
			othersList.setModel(new DefaultListModel());
			pack();
			return;
		}
		others.clear();
		System.out.println("Refreshing others...");
		Student s = applicants.get(applicantList.getSelectedIndex());
		int curCDN = jobs.get(cbJob.getSelectedIndex()).getCourse().getCdn();
		DefaultListModel<String> otherJobNames = new DefaultListModel<String>();
		for(int c = 0; c < am.getApplications().size(); c++) {
			Application a = am.getApplication(c);
			if(curCDN == a.getJobs().getCourse().getCdn() && !a.getJobs().isOfferSent() && s.getUsername().equals(a.getStudent().getUsername())) {
				others.add(a);
			}
		}
		for(int c = 0; c < others.size(); c++) {
			Job j = others.get(c).getJobs();
			otherJobNames.addElement(j.getCourse().getClassName() + " " + j.getPositionFullName() + " " + j.getDay());
		}
		othersList.setModel(otherJobNames);
		pack();
	}
	
	private void refreshData() {
		if(instructor == null) instructor = pm.getInstructor(cbInstructor.getSelectedIndex());
		jobs.clear();
		for(int c = 0; c < am.getJobs().size(); c++) {
			if(am.getJob(c).getInstructor().getUsername().equals(instructor.getUsername())) {
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
		hireButton.setMinimumSize(new Dimension(this.getWidth(),40));
		pack();
	}
}
