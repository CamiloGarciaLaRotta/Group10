package widgets;

import java.util.ArrayList;

import javax.swing.JCheckBox;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

/**
 * JCheckBox conforming to the light/dark themes of the desktop application
 * @author harwiltz
 *
 */
public class ThemedCheckBox extends JCheckBox{

	/**
	 * 
	 * @param s text for the checkbox's label
	 */
	public ThemedCheckBox(String s) {
		super(s);
		setColors();
	}
	
	/**
	 * Sets all relevant checkbox colors based on the current state of the color theme
	 */
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		if(constants.get(1) == 0) {
			this.setForeground(Constants.dark_normalFgColor);
			this.setBackground(Constants.dark_bgColor);
		}
		else {
			this.setForeground(Constants.light_normalFgColor);
			this.setBackground(Constants.light_bgColor);
		}
	}
	
}
