package widgets;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ThemedList extends JList{
	
	public ThemedList(String[] s) {
		super(s);
		this.setBackground(Constants.tfBgColor);
		this.setForeground(Constants.tfFgColor);
		this.setSelectionBackground(Constants.selectionBg);
		this.setSelectionForeground(Constants.tfFgColor);
	}
	
	public ThemedList(DefaultListModel d) {
		super(d);
		this.setBackground(Constants.tfBgColor);
		this.setForeground(Constants.tfFgColor);
		this.setSelectionBackground(Constants.selectionBg);
		this.setSelectionForeground(Constants.tfFgColor);
	}

}
