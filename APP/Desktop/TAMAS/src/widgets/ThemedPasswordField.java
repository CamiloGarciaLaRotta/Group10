package widgets;

import javax.swing.JPasswordField;

public class ThemedPasswordField extends JPasswordField{
	public ThemedPasswordField() {
		super();
		this.setBackground(Constants.tfBgColor);
		this.setForeground(Constants.tfFgColor);
		this.setCaretColor(Constants.cursorColor);
	}
	
	public ThemedPasswordField(int width) {
		super(width);
		this.setBackground(Constants.tfBgColor);
		this.setForeground(Constants.tfFgColor);
		this.setCaretColor(Constants.cursorColor);
	}
}
