package widgets;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;


public class ThemedLabel extends JLabel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 38904693619812596L;

	public enum LabelType {Normal, Error, Success};
	
	private Color fgColor;
	
	private LabelType type;
	
	public ThemedLabel(String s) {
		super(s);
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
		fgColor = (constants.get(1) == 0) ? Constants.dark_normalFgColor : Constants.light_normalFgColor;
		this.setType(LabelType.Normal);
	}
	
	public ThemedLabel(String s, LabelType type) {
		super(s);
		this.setType(type);
	}
	
	public void setType(LabelType type) {
		this.type = type;
		if(type == LabelType.Normal) this.setForeground(fgColor);
		else if(type == LabelType.Error) this.setForeground(fgColor);
		else if(type == LabelType.Success) this.setForeground(fgColor);
	}
	
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants("output/constants.xml");
		fgColor = (constants.get(1) == 0) ? Constants.dark_normalFgColor : Constants.light_normalFgColor;
		setType(type);
	}

}
