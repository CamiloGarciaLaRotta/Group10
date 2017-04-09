package ca.mcgill.ecse321.group10.view;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;
import widgets.Constants;
import widgets.ThemedLabel;
import widgets.ThemedList;
import widgets.ThemedPanel;

public class ApproveOffersView extends JFrame{
	
	private ApplicationManager am;
	private ProfileManager pm;
	
	private JLabel lCurrent;
	private JList<String> offersList;
	private JScrollPane offerScroller;
	private JButton approveButton;
	private JButton removeButton;
	private JButton exitButton;
	
	private ArrayList<Job> jobs;
	private ArrayList<Student> students;
	private DefaultListModel<String> listModel;
	
	public ApproveOffersView(ApplicationManager am, ProfileManager pm) {
		this.am = am;
		this.pm = pm;
		initComponents();
	}
	
	private void initComponents() {
		jobs = new ArrayList<Job>();
		students = new ArrayList<Student>();
		lCurrent = new ThemedLabel("Current Job Offers:");
		offersList = new ThemedList(new String[0]);
		offerScroller = new JScrollPane(offersList);
		approveButton = new JButton("Approve All Offers");
		removeButton = new JButton("Remove Selected Offer");
		exitButton = new JButton("Exit");
		
		refreshList();
		
		exitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		removeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(offersList.getSelectedIndex() == -1) return;
				//jobs.get(offersList.getSelectedIndex()).setOfferSent(false);
				ApplicationController ac = new ApplicationController(am, ApplicationController.APPLICATION_FILE_NAME);
				ProfileController pc = new ProfileController(pm, ProfileController.PROFILE_FILE_NAME);
				ac.setJobOffered(jobs.get(offersList.getSelectedIndex()), false);
				pc.removeJobFromStudent(students.get(offersList.getSelectedIndex()), jobs.get(offersList.getSelectedIndex()));
				//students.get(offersList.getSelectedIndex()).removeJob(jobs.get(offersList.getSelectedIndex()));
				jobs.remove(offersList.getSelectedIndex());
				students.remove(offersList.getSelectedIndex());
				refreshList();
			}
		});
		
		approveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
				constants.set(0, 1);
				PersistenceXStream.setFilename(System.getProperty("user.home") + "/.tamas/output/constants.xml");
				PersistenceXStream.saveToXMLwithXStream(constants);
				refreshList();
			}
		});
		
		JPanel panel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(lCurrent);
		panel.add(offerScroller);
		JPanel buttonPanel = new ThemedPanel(Constants.grey);
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(approveButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(exitButton);
		panel.add(buttonPanel);
		
		this.add(panel);
		pack();
	}
	
	private void refreshList() {
		listModel = new DefaultListModel<String>();
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		if(constants.get(0) == 1) {
			approveButton.setEnabled(false);
			removeButton.setEnabled(false);
			listModel.addElement("Offers already sent");
			offersList.setEnabled(false);
			offersList.setModel(listModel);
			pack();
		}
		for(int c = 0; c < pm.getStudents().size(); c++) {
			Student curStudent = pm.getStudent(c);
			for(int j = 0; j < curStudent.getJobs().size(); j++) {
				Job curJob = curStudent.getJob(j);
				jobs.add(curJob);
				students.add(curStudent);
				listModel.addElement(curJob.getCourse().getClassName() + " " + curJob.getPositionFullName() + " -> " + 
									curStudent.getFirstName() + " " + curStudent.getLastName());
			}
		}
		offersList.setModel(listModel);
		pack();
	}

}
