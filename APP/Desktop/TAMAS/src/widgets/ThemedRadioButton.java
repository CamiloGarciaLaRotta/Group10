package widgets;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JRadioButton;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ThemedRadioButton extends JRadioButton{
	
	private Color bgColor;
	private Color fgColor;
	
	public ThemedRadioButton() {
		super();
		setColors();
	}
	
	public ThemedRadioButton(String s) {
		super(s);
		setColors();
	}
	
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		if(constants.get(1) == 0) {
			bgColor = Constants.dark_bgColor;
			fgColor = Constants.dark_normalFgColor;
		}
		this.setBackground(bgColor);
		this.setForeground(fgColor);
	}

}
