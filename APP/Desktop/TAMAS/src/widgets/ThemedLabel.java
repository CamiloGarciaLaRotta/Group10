package widgets;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

import ca.mcgill.ecse321.group10.persistence.PersistenceXStream;

/**
 * JLabel conforming to the light/dark themes of the desktop application
 * @author harwiltz
 *
 */
public class ThemedLabel extends JLabel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 38904693619812596L;
	
	/**
	 * Enum describing the various label types corresponding to different colors
	 * @author harwiltz
	 *
	 */
	public enum LabelType {Normal, Error, Success};
	
	private Color fgColor;
	
	private LabelType type;
	
	/**
	 * 
	 * @param s text to set label to
	 */
	public ThemedLabel(String s) {
		super(s);
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		fgColor = (constants.get(1) == 0) ? Constants.dark_normalFgColor : Constants.light_normalFgColor;
		this.setType(LabelType.Normal);
	}
	
	/**
	 * 
	 * @param s text to set label to
	 * @param type initial LabelType of the label
	 */
	public ThemedLabel(String s, LabelType type) {
		super(s);
		this.setType(type);
	}
	
	/**
	 * Sets the type property to a given LabelType
	 * @param type
	 */
	public void setType(LabelType type) {
		this.type = type;
		if(type == LabelType.Normal) this.setForeground(fgColor);
		else if(type == LabelType.Error) this.setForeground(Constants.errorFgColor);
		else if(type == LabelType.Success) this.setForeground(Constants.successFgColor);
	}
	
	/**
	 * Sets the relevant colors of the JLabel based on the state of the color theme
	 */
	public void setColors() {
		ArrayList<Integer> constants = PersistenceXStream.initializeConstants(System.getProperty("user.home") + "/.tamas/output/constants.xml");
		fgColor = (constants.get(1) == 0) ? Constants.dark_normalFgColor : Constants.light_normalFgColor;
		setType(type);
	}

}
