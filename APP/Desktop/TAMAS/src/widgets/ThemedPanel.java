package widgets;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ThemedPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6592889406148815983L;

	public ThemedPanel() {
		super();
		setColors();
	}
	
	public ThemedPanel(Color c) {
		super();
		setBackground(c);
	}
	
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
		if(constants.get(1) == 0) setBackground(Constants.dark_bgColor);
		else setBackground(Constants.light_bgColor);
	}

}
