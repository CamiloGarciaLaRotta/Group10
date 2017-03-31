package widgets;

import javax.swing.JTextArea;

public class ThemedTextArea extends JTextArea{
	
	public ThemedTextArea() {
		super();
		this.setBackground(Constants.tfBgColor);
		this.setForeground(Constants.tfFgColor);
		this.setCaretColor(Constants.cursorColor);
	}

}
