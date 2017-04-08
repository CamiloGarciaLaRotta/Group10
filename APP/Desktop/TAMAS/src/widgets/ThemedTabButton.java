package widgets;

import java.util.ArrayList;

import javax.swing.JButton;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ThemedTabButton extends JButton{
	
	String s;
	
	public ThemedTabButton() {
		super();
		setColors();
		s = "";
	}
	
	public ThemedTabButton(String s) {
		super(s);
		setColors();
		this.s = s;
	}
	
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
		if(constants.get(1) == 0) {
			this.setBackground(Constants.darkBlue);
			this.setForeground(Constants.dark_tfFgColor);
		} else {
			this.setBackground(Constants.lightRed);
			this.setForeground(Constants.light_tfFgColor);
		}
	}
	
	public void setText(String s) {
		super.setText(s);
		this.s = s;
	}
	
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
