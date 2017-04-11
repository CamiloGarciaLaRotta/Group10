package widgets;

import java.util.ArrayList;

import javax.swing.JTextField;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

/**
 * JTextField conforming to the light/dark themes of the desktop application
 * @author harwiltz
 *
 */
public class ThemedTextField extends JTextField{
	
	public ThemedTextField() {
		super();
		setColors();
		this.setCaretColor(Constants.cursorColor);
	}
	
	/**
	 * 
	 * @param width desired width of the textfield
	 */
	public ThemedTextField(int width) {
		super(width);
		setColors();
		this.setCaretColor(Constants.cursorColor);
	}
	
	/**
	 * Sets the relevant colors of the JTextField depending on the current state of the color theme
	 */
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		if(constants.get(1) == 0) {
			this.setBackground(Constants.dark_tfBgColor);
			this.setForeground(Constants.dark_tfFgColor);
		} else {
			this.setBackground(Constants.light_tfBgColor);
			this.setForeground(Constants.light_tfFgColor);
		}
	}

}
