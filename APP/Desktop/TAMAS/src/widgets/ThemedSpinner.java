package widgets;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ThemedSpinner extends JSpinner{
	
	public ThemedSpinner(SpinnerModel s) {
		super(s);
		Component c = this.getEditor().getComponent(0);
		setColors();
	}
	
	public void setEditor(JComponent e) {
		super.setEditor(e);
		setColors();
	}
	
	public void setColors() {
		
		Color bgColor;
		Color fgColor;
		
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		if(constants.get(1) == 0) {
			bgColor = Constants.dark_tfBgColor;
			fgColor = Constants.dark_tfFgColor;
		} else {
			bgColor = Constants.light_tfBgColor;
			fgColor = Constants.light_tfFgColor;
		}
		
	}

}
