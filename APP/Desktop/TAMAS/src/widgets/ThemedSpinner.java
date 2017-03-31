package widgets;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

public class ThemedSpinner extends JSpinner{
	
	public ThemedSpinner(SpinnerModel s) {
		super(s);
		Component c = this.getEditor().getComponent(0);
		c.setBackground(Constants.tfBgColor);
		c.setForeground(Constants.tfFgColor);
	}
	
	public void setEditor(JComponent e) {
		super.setEditor(e);
		e.setBackground(Constants.tfBgColor);
		e.setForeground(Constants.tfFgColor);
	}

}
