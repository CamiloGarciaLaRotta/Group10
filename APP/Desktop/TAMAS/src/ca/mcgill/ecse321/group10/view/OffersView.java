package ca.mcgill.ecse321.group10.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
import ca.mcgill.ecse321.group10.controller.ProfileController;
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
	
	private ApplicationManager am;
	private ProfileManager pm;
	
	public OffersView(ApplicationManager am, ProfileManager pm) {
		super("Accept or Reject Job Offers");
		this.am = am;
		this.pm = pm;
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
		panel.add(lStudent);
		panel.add(cbStudent);
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
		cbStudent.setAlignmentX(Component.LEFT_ALIGNMENT);
		offerScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(panel);
		//this.setVisible(true);
		this.setMinimumSize(new Dimension(350,400));
		pack();
	}
	
	private void refreshOfferList() {
		listModel = new DefaultListModel<String>();
		int jobOffers = pm.getStudent(cbStudent.getSelectedIndex()).getJobs().size();
		if(jobOffers == 0) {
			listModel.addElement("You have no pending job offers");
			offerList.setEnabled(false);
		}
		else {
			offerList.setEnabled(true);
			for(int c = 0; c < jobOffers; c++) {
				Job currentJob = pm.getStudent(cbStudent.getSelectedIndex()).getJob(c);
				String offerName = currentJob.getCourse().getClassName() + 
									" " + currentJob.getPositionFullName() + " - $" + currentJob.getSalary() + "/h";
				listModel.addElement(offerName);
			}
		}
		offerList.setModel(listModel);
		pack();
	}
	
	private void verdict(boolean accept) {
		if(offerList.getSelectedIndex() == -1) {
			message.setType(ThemedLabel.LabelType.Error);
			message.setText("No offer selected!");
			pack();
			return;
		}
		message.setType(ThemedLabel.LabelType.Success);

		Student currentStudent = pm.getStudent(cbStudent.getSelectedIndex());
		String currentUsername = currentStudent.getUsername();
		Job currentJob = currentStudent.getJob(offerList.getSelectedIndex());
		String currentJobName = getJobString(currentJob); 
		for(int c = 0; c < am.getApplications().size(); c++) {
			if(am.getApplication(c).getStudent().getUsername().equals(currentUsername) && getJobString(am.getApplication(c).getJobs()).equals(currentJobName)) {
				am.getApplication(c).setOfferAccepted(accept);
				new ApplicationController(am, ApplicationController.APPLICATION_FILE_NAME).persist();
				if(accept)
					message.setText("Offer for " + currentJob.getCourse().getClassName() + " " + currentJob.getPositionFullName() + " was accepted!");
				else
					message.setText("Offer for " + currentJob.getCourse().getClassName() + " " + currentJob.getPositionFullName() + " was rejected.");
				break;
			}
		}
		currentStudent.removeJob(currentJob);
		new ProfileController(pm,ProfileController.PROFILE_FILE_NAME).persist();
		refreshOfferList();
	}
	
	private String getJobString(Job currentJob) {
		return currentJob.getCourse().getClassName() + currentJob.getPositionFullName() + currentJob.getSalary() +
				currentJob.getStartTime().toString() + currentJob.getEndTime().toString();
	}

}
