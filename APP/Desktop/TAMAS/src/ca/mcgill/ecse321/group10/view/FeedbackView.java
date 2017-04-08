package ca.mcgill.ecse321.group10.view;

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
import ca.mcgill.ecse321.group10.TAMAS.model.Job;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import widgets.ThemedLabel;
import widgets.ThemedPanel;
import widgets.ThemedTextArea;

public class FeedbackView extends JFrame {
	
	private Student student;
	private ProfileManager pm;
	private ApplicationManager am;
	
	private JTextArea tfEval;
	private JScrollPane evalScroller;
	private JButton closeButton;
	
	private JLabel label;
	
	private JComboBox<String> cbStudents;
	private JComboBox<String> cbApplications;
	
	private List<Application> applications;
	
	public FeedbackView(ApplicationManager am, ProfileManager pm, Student student) {
		this.student = student;
		this.am = am;
		this.pm = pm;
		initComponents();
	}
	
	private void initComponents() {
		applications = new ArrayList<Application>();
		label = new ThemedLabel("Your evaluations are listed below:");
		tfEval = new ThemedTextArea();
		tfEval.setWrapStyleWord(true);
		tfEval.setLineWrap(true);
		tfEval.setRows(10);
		tfEval.setColumns(40);
		tfEval.setEditable(false);
		evalScroller = new JScrollPane(tfEval);
		
		setTitle("Feedback From Profs");
		
		cbStudents = new JComboBox<String>();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for(int c = 0; c < pm.getStudents().size(); c++) {
			model.addElement(pm.getStudent(c).getUsername());
		}
		cbStudents.setModel(model);
		
		cbStudents.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				refreshApplications();
				refreshEvaluation();
			}
		});
		
		cbApplications = new JComboBox<String>();
		refreshApplications();
		
		cbApplications.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				refreshEvaluation();
			}
		});
		
		closeButton = new JButton("Close");
		
		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
		
		refreshEvaluation();
		
		JPanel panel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		if(student == null) panel.add(cbStudents);
		panel.add(cbApplications);
		panel.add(label);
		panel.add(evalScroller);
		panel.add(closeButton);
		
		this.add(panel);
		pack();
	}
	
	private void refreshApplications() {
		Student curStudent;
		if(student == null) curStudent = pm.getStudent(cbStudents.getSelectedIndex());
		else curStudent = student;
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		applications.clear();
		for(int c = 0; c < am.getApplications().size(); c++) {
			if(am.getApplication(c).getOfferAccepted() && am.getApplication(c).getStudent().getUsername().equals(curStudent.getUsername())) {
				applications.add(am.getApplication(c));
				Job j = am.getApplication(c).getJobs();
				model.addElement(j.getCourse().getClassName() + " " + j.getPositionFullName() + " " + j.getDay());
			}
		}
		cbApplications.setModel(model);
	}
	
	private void refreshEvaluation() {
		Student curStudent;
		if(student == null) curStudent = pm.getStudent(cbStudents.getSelectedIndex());
		else curStudent = student;
		tfEval.setText(applications.get(cbApplications.getSelectedIndex()).getStudentEvaluation());
		pack();
	}
}
