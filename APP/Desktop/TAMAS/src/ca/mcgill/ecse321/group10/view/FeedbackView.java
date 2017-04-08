package ca.mcgill.ecse321.group10.view;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import widgets.ThemedLabel;
import widgets.ThemedPanel;
import widgets.ThemedTextArea;

public class FeedbackView extends JFrame {
	
	private Student student;
	private ProfileManager pm;
	
	private JTextArea tfEval;
	private JScrollPane evalScroller;
	private JButton closeButton;
	
	private JLabel label;
	
	private JComboBox<String> cbStudents;
	
	public FeedbackView(ProfileManager pm, Student student) {
		this.student = student;
		this.pm = pm;
		initComponents();
	}
	
	private void initComponents() {
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
		panel.add(label);
		panel.add(evalScroller);
		panel.add(closeButton);
		
		this.add(panel);
		pack();
	}
	
	private void refreshEvaluation() {
		Student curStudent;
		if(student == null) curStudent = pm.getStudent(cbStudents.getSelectedIndex());
		else curStudent = student;
		tfEval.setText(student.getEvaluations());
		pack();
	}
}
