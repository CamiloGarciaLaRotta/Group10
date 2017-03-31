package ca.mcgill.ecse321.group10.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
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
		message = new ThemedLabel("", ThemedLabel.LabelType.Error);
		String [] instructorNames = new String[instructors.size()];
		for(int c = 0; c < instructorNames.length; c++) 
			instructorNames[c] = instructors.get(c).getFirstName() + " " + instructors.get(c).getLastName();
		lInstructor = new ThemedLabel("Select Instructor:");
		lJob = new ThemedLabel("Select Job:");
		lApplicant = new ThemedLabel("Choose Applicant:");
		cbInstructor = new JComboBox(instructorNames);
		cbJob = new JComboBox(new String[0]);
		
		applicantList = new ThemedList(new String[0]);
		applicantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		applicantList.setLayoutOrientation(JList.VERTICAL);
		applicantScroller = new JScrollPane(applicantList);
		
		hireButton = new JButton("Hire");
		hireButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
			}
		});
		
		JPanel panel = new ThemedPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(lInstructor);
		panel.add(cbInstructor);
		panel.add(lJob);
		panel.add(cbJob);
		panel.add(lApplicant);
		panel.add(applicantScroller);
		panel.add(hireButton);
		this.add(panel);
		this.setVisible(true);
		pack();
	}

}
