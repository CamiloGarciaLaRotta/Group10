package widgets;

import javax.swing.JTextField;

public class ThemedTextField extends JTextField{
	
	public ThemedTextField() {
		super();
		this.setBackground(Constants.tfBgColor);
		this.setForeground(Constants.tfFgColor);
		this.setCaretColor(Constants.cursorColor);
	}

}
