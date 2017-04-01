package widgets;

import javax.swing.JButton;

public class ThemedTabButton extends JButton{
	
	String s;
	
	public ThemedTabButton() {
		super();
		initColors();
		s = "";
	}
	
	public ThemedTabButton(String s) {
		super(s);
		initColors();
		this.s = s;
	}
	
	private void initColors() {
		this.setBackground(Constants.darkBlue);
		this.setForeground(Constants.tfFgColor);
	}
	
	public void setText(String s) {
		super.setText(s);
		this.s = s;
	}
	
	public void setEnabled(boolean enabled) {
		
		if(enabled) {
			initColors();
		}
		else {
			this.setBackground(null);
			this.setForeground(null);
		}
		super.setEnabled(enabled);
	}

}
