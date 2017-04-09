package widgets;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPasswordField;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ThemedPasswordField extends JPasswordField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2323392112428221259L;
	private Color bgColor;
	private Color fgColor;
	
	public ThemedPasswordField() {
		super();
		setColors();
		this.setCaretColor(Constants.cursorColor);
	}
	
	public ThemedPasswordField(int width) {
		super(width);
		setColors();
		this.setCaretColor(Constants.cursorColor);
	}
	
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		if(constants.get(1) == 0) {
			bgColor = Constants.dark_tfBgColor;
			fgColor = Constants.dark_tfFgColor;
		} else {
			bgColor = Constants.light_tfBgColor;
			fgColor = Constants.light_tfFgColor;
		}
		this.setBackground(bgColor);
		this.setForeground(fgColor);
	}
}
