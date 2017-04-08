package widgets;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

public class ThemedList extends JList{
	
	private Color bgColor;
	private Color fgColor;
	private Color selBgColor;
	private Color selFgColor;
	
	public ThemedList(String[] s) {
		super(s);
		setColors();
		this.setBackground(bgColor);
		this.setForeground(fgColor);
		this.setSelectionBackground(selBgColor);
		this.setSelectionForeground(selFgColor);
	}
	
	public ThemedList(DefaultListModel d) {
		super(d);
		setColors();
		this.setBackground(bgColor);
		this.setForeground(fgColor);
		this.setSelectionBackground(selBgColor);
		this.setSelectionForeground(selFgColor);
	}
	
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
		if(constants.get(1) == 0) {
			bgColor = Constants.dark_tfBgColor;
			fgColor = Constants.dark_tfFgColor;
			selBgColor = Constants.dark_selectionBg;
			selFgColor = Constants.dark_tfFgColor;
		} else {
			bgColor = Constants.light_tfBgColor;
			fgColor = Constants.light_tfFgColor;
			selBgColor = Constants.light_selectionBg;
			selFgColor = Constants.light_tfFgColor;
		}
	}

}
