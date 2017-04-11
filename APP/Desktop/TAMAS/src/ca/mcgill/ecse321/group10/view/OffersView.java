package ca.mcgill.ecse321.group10.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ApplicationController;
import ca.mcgill.ecse321.group10.controller.InputException;
import ca.mcgill.ecse321.group10.controller.ProfileController;
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;
import widgets.ThemedLabel;
import widgets.ThemedList;
import widgets.ThemedPanel;

public class OffersView extends JFrame{
	
	private JComboBox<String> cbStudent;
	private JLabel lStudent;
	private JList<String> offerList;
	private DefaultListModel<String> listModel;
	private JScrollPane offerScroller;
	private JLabel lOffers;
	private JButton acceptButton;
	private JButton rejectButton;
	private ThemedLabel message;
	private ThemedLabel stats;
	
	private ApplicationManager am;
	private ProfileManager pm;
	
	private Student student;
	
	public OffersView(ApplicationManager am, ProfileManager pm, Student student) {
		super("Accept or Reject Job Offers");
		this.am = am;
		this.pm = pm;
		this.student = student;
		initComponents();
	}
	
	private void initComponents() {
		String [] studentNames = new String[pm.getStudents().size()];
		for(int c = 0; c < studentNames.length; c++) {
			studentNames[c] = pm.getStudent(c).getUsername();
		}
		cbStudent = new JComboBox<String>(studentNames);
		lStudent = new ThemedLabel("Select username:");
		
		cbStudent.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				refreshOfferList();
			}
		});
		
		offerList = new ThemedList(new String[0]);
		stats = new ThemedLabel("");
		refreshOfferList();
		offerScroller = new JScrollPane(offerList);
		lOffers = new ThemedLabel("Select job offer:");
		
		acceptButton = new JButton("Accept Offer");
		rejectButton = new JButton("Reject Offer");
		
		acceptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				verdict(true);
			}
		});
		
		rejectButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				verdict(false);
			}
		});
		
		message = new ThemedLabel("",ThemedLabel.LabelType.Success);
		
		cbStudent.setMaximumSize(new Dimension(300,50));
		offerScroller.setMinimumSize(new Dimension(300,200));
		
		JPanel panel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(message);
		panel.add(stats);
		if(student == null) {
			panel.add(lStudent);
			panel.add(cbStudent);
		}

		panel.add(lOffers);
		panel.add(offerScroller);
		JPanel buttonPanel = new ThemedPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(acceptButton);
		buttonPanel.add(rejectButton);
		panel.add(buttonPanel);
		lStudent.setAlignmentX(Component.LEFT_ALIGNMENT);
		lOffers.setAlignmentX(Component.LEFT_ALIGNMENT);
		message.setAlignmentX(Component.LEFT_ALIGNMENT);
		stats.setAlignmentX(Component.LEFT_ALIGNMENT);
		cbStudent.setAlignmentX(Component.LEFT_ALIGNMENT);
		offerScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(panel);
		this.setMinimumSize(new Dimension(350,400));
		pack();
	}
	
	//Refreshes list of job offers
	private void refreshOfferList() {
		listModel = new DefaultListModel<String>();
		int jobOffers;
		if(student == null) jobOffers = pm.getStudent(cbStudent.getSelectedIndex()).getJobs().size();
		else jobOffers = student.getJobs().size();
		if(jobOffers == 0) {
			listModel.addElement("You have no pending job offers");
			offerList.setEnabled(false);
		}
		else {
			offerList.setEnabled(true);
			for(int c = 0; c < jobOffers; c++) {
				Job currentJob;
				if(student == null) currentJob = pm.getStudent(cbStudent.getSelectedIndex()).getJob(c);
				else currentJob = student.getJob(c);
				String offerName = currentJob.getCourse().getClassName() + 
									" " + currentJob.getPositionFullName() + " - $" + currentJob.getSalary() + "/h";
				listModel.addElement(offerName);
			}
		}
		offerList.setModel(listModel);
		refreshStats();
		pack();
	}
	
	//Accepts/rejects job offers
	private void verdict(boolean accept) {
		if(offerList.getSelectedIndex() == -1) {
			message.setType(ThemedLabel.LabelType.Error);
			message.setText("No offer selected!");
			pack();
			return;
		}
		message.setType(ThemedLabel.LabelType.Success);
		
		Student currentStudent;
		Job currentJob;

		if(student == null) {
			currentStudent = pm.getStudent(cbStudent.getSelectedIndex());	
		}
		else currentStudent = student;

		String currentUsername = currentStudent.getUsername();
		currentJob = currentStudent.getJob(offerList.getSelectedIndex());
		String currentJobName = getJobString(currentJob); 
		//Cycle through applications to find the application corresponding to the current job offer
		for(int c = 0; c < am.getApplications().size(); c++) {
			if(am.getApplication(c).getStudent().getUsername().equals(currentUsername) && getJobString(am.getApplication(c).getJobs()).equals(currentJobName)) {
				ApplicationController ac = new ApplicationController(am, ApplicationController.APPLICATION_FILE_NAME);
				ProfileController pc = new ProfileController(pm, ProfileController.PROFILE_FILE_NAME);
				if(accept) {
					try {
						pc.acceptJob(am.getApplication(c).getStudent(), am.getApplication(c).getJobs()); //Deduct available hours from student
						ac.setJobOfferAccepted(am.getApplication(c), true); //Set offerAccepted flag in the application
						message.setType(ThemedLabel.LabelType.Success);
						message.setText("Offer for " + currentJob.getCourse().getClassName() + " " + currentJob.getPositionFullName() + " was accepted!");
						pc.removeJobOfferFromStudent(currentStudent,currentJob); //Remove job offer from student since it's already been accepted
					} catch(InputException ex) {
						message.setType(ThemedLabel.LabelType.Error);
						message.setText(ex.getMessage());
					}
				}
				else {
					message.setText("Offer for " + currentJob.getCourse().getClassName() + " " + currentJob.getPositionFullName() + " was rejected.");
					ac.setJobOfferAccepted(am.getApplication(c), false); //Set offerAccepted flag to false
					ac.setJobOffered(am.getApplication(c).getJobs(), false); //Unset the offerSent flag for the current job, so other students may be hired
					ac.removeApplication(am.getApplication(c)); //Get rid of the rejected job application
					pc.removeJobOfferFromStudent(currentStudent,currentJob); //Remove rejected job offer from student's list

					//Unset the admin approved data, allowing instructors to hire more students
					ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home")+"/.tamas/output/constants.xml");
					constants.set(0, 0);
					PersistenceXStream.setFilename(System.getProperty("user.home")+"/.tamas/output/constants.xml");
					PersistenceXStream.saveToXMLwithXStream(constants);
				}
				break;
			}
		}
		refreshOfferList();
	}
	
	//Refresh label indicating how many available hours are left for the given student
	private void refreshStats() {
		float hoursLeft;
		if(student == null) hoursLeft = pm.getStudent(cbStudent.getSelectedIndex()).getHoursLeft();
		else hoursLeft = student.getHoursLeft();
		stats.setText("You can currently accept up to " + hoursLeft + " hours of work");
	}
	
	private String getJobString(Job currentJob) {
		return currentJob.getCourse().getClassName() + currentJob.getPositionFullName() + currentJob.getSalary();
	}

}
