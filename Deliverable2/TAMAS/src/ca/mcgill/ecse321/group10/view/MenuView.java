package ca.mcgill.ecse321.group10.view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;

public class MenuView extends JFrame{
	
	private static final int X_SIZE = 300;
	private static final int Y_SIZE = 300;

	private JLabel greeting;
	private JButton applicationButton;
	private JButton publishButton;
	
	private ApplicationManager am;
	private ProfileManager pm;
	
	public MenuView(ApplicationManager am, ProfileManager pm) {
		this.am = am;
		this.pm = pm;
		initComponents();
	}
	
	private void initComponents() {
		greeting = new JLabel();
		applicationButton = new JButton();
		publishButton = new JButton();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Main Control Center");
		
		greeting.setText("Welcome to TAMAS. Power is in your hands.");
		applicationButton.setText("Create Job Application");
		publishButton.setText("Publish Job Posting");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		panel.add(greeting);
		panel.add(applicationButton);
		panel.add(publishButton);
		
		this.add(panel);
		this.setSize(new Dimension(X_SIZE,Y_SIZE));
		pack();
	}
	
}
