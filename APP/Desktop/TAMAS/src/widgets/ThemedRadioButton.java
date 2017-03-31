package widgets;

import javax.swing.JRadioButton;

public class ThemedRadioButton extends JRadioButton{
	
	public ThemedRadioButton() {
		super();
		this.setBackground(Constants.bgColor);
		this.setForeground(Constants.normalFgColor);
	}
	
	public ThemedRadioButton(String s) {
		super(s);
		this.setBackground(Constants.bgColor);
		this.setForeground(Constants.normalFgColor);
	}

}
