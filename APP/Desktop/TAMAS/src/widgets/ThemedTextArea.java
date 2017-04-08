package widgets;

import java.util.ArrayList;

import javax.swing.JTextArea;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ThemedTextArea extends JTextArea{
	
	public ThemedTextArea() {
		super();
		setColors();
		this.setCaretColor(Constants.cursorColor);
	}
	
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
		if(constants.get(1) == 0) {
			this.setBackground(Constants.dark_tfBgColor);
			this.setForeground(Constants.dark_tfFgColor);
			this.setSelectedTextColor(Constants.dark_selectionBg);
		} else {
			this.setBackground(Constants.light_tfBgColor);
			this.setForeground(Constants.light_tfFgColor);
			this.setSelectedTextColor(Constants.light_selectionBg);
		}
	}

}
