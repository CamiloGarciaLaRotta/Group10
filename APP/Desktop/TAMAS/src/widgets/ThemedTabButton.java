package widgets;

import java.util.ArrayList;

import javax.swing.JButton;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

/**
 * Special themed buttons used for the tabs in the MenuView as seen by an Admin
 * Conforms to the light/dark themes of the desktop application
 * @author harwiltz
 *
 */
public class ThemedTabButton extends JButton{
	
	String s;
	
	public ThemedTabButton() {
		super();
		setColors();
		s = "";
	}
	
	/**
	 * 
	 * @param s instantiates button text to s
	 */
	public ThemedTabButton(String s) {
		super(s);
		setColors();
		this.s = s;
	}
	
	/**
	 * Sets relevant colors of the JButton depending on the current state of the color scheme
	 */
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		if(constants.get(1) == 0) {
			this.setBackground(Constants.darkBlue);
			this.setForeground(Constants.dark_tfFgColor);
		} else {
			this.setBackground(Constants.lightRed);
			this.setForeground(Constants.light_tfFgColor);
		}
	}
	
	/**
	 * Sets the text of the JButton
	 */
	public void setText(String s) {
		super.setText(s);
		this.s = s;
	}
	
	/**
	 * Enables/disables the JButton, preserving and modifying the appropriate colors
	 * @param enabled 
	 */
	public void setEnabled(boolean enabled) {
		
		if(enabled) {
			setColors();
		}
		else {
			this.setBackground(null);
			this.setForeground(null);
		}
		super.setEnabled(enabled);
	}

}
