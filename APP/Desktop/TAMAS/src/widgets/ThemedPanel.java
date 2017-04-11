package widgets;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

/**
 * JPanel conforming to the light/dark themes of the desktop application
 * @author harwiltz
 *
 */
public class ThemedPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6592889406148815983L;

	public ThemedPanel() {
		super();
		setColors();
	}
	
	/**
	 * Sets the background color of a new JPanel to an arbitrary color
	 * @param c
	 */
	public ThemedPanel(Color c) {
		super();
		setBackground(c);
	}
	
	/**
	 * Sets the relevant colors of the JPanel depending on the current state of the color theme
	 */
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		if(constants.get(1) == 0) setBackground(Constants.dark_bgColor);
		else setBackground(Constants.light_bgColor);
	}

}
