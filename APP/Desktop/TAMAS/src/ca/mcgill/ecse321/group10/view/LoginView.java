package ca.mcgill.ecse321.group10.view;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.group10.TAMAS.model.ApplicationManager;
import ca.mcgill.ecse321.group10.TAMAS.model.CourseManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Profile;
import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import widgets.ThemedLabel;
import widgets.ThemedPanel;
import widgets.ThemedPasswordField;
import widgets.ThemedTextField;

public class LoginView extends JFrame{
	
	JButton loginButton;
	JButton registerButton;
	JTextField tfUsername;
	JTextField tfPassword;
	JLabel lUsername;
	JLabel lPassword;
	
	JLabel lError;
	
	ApplicationManager am;
	ProfileManager pm;
	CourseManager cm;
	
	public LoginView(ApplicationManager am, ProfileManager pm, CourseManager cm) {
		this.am = am;
		this.pm = pm;
		this.cm = cm;
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("TAMAS Login Page");
		loginButton = new JButton("Log In");
		registerButton = new JButton("Register");
		
		lError = new ThemedLabel("",ThemedLabel.LabelType.Error);
		
		loginButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String error = "";
				if(tfUsername.getText().trim().length() == 0) {
					error += "Username cannot be empty!";
				}
				if(tfPassword.getText().trim().length() == 0) {
					error += "Password cannot be empty!";
				}
				Profile p = null;
				for(int c = 0; c < pm.getAdmins().size(); c++) {
					if(pm.getAdmin(c).getUsername().equals(tfUsername.getText())) {
						p = pm.getAdmin(c);
						break;
					}
				}
				if(p == null) {
					for(int c = 0; c < pm.getInstructors().size(); c++) {
						if(pm.getInstructor(c).getUsername().equals(tfUsername.getText())) {
							p = pm.getInstructor(c);
							break;
						}
					}
					if(p == null) {
						for(int c = 0; c < pm.getStudents().size(); c++) {
							if(pm.getStudent(c).getUsername().equals(tfUsername.getText())) {
								p = pm.getStudent(c);
							}
						}
					}
				}
				if(p == null) error += "Profile not found! Try registering an account.";
				else if(!p.getPassword().equals(String.valueOf(tfPassword.getText()))) error += "Invalid password!";
				lError.setText(error);
				pack();
				tfPassword.setText("");
				if(error.length() == 0) {
					tfUsername.setText("");
					new MenuView(am,pm,cm,p).setVisible(true);
				}
			}
		});
		
		registerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new RegistrationView(pm).setVisible(true);
			}
		});
		
		tfUsername = new ThemedTextField(15);
		tfPassword = new ThemedPasswordField(15);
		
		lUsername = new ThemedLabel("Username: ");
		lPassword = new ThemedLabel("Password: ");
		
		JPanel mainPanel = new ThemedPanel();
		JPanel upanel = new ThemedPanel();
		JPanel ppanel = new ThemedPanel();
		
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		upanel.setLayout(new FlowLayout());
		ppanel.setLayout(new FlowLayout());
		upanel.add(lUsername);
		upanel.add(tfUsername);
		ppanel.add(lPassword);
		ppanel.add(tfPassword);
		mainPanel.add(lError);
		mainPanel.add(upanel);
		mainPanel.add(ppanel);
		mainPanel.add(loginButton);
		mainPanel.add(registerButton);
		this.add(mainPanel);
		
		pack();
	}
}
