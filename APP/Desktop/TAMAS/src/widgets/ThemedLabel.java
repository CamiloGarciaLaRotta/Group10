package widgets;

import java.awt.Color;

import javax.swing.JLabel;


public class ThemedLabel extends JLabel{
	
	public enum LabelType {Normal, Error, Success};
	
	public ThemedLabel(String s) {
		super(s);
		this.setForeground(new Color(0xbb,0xbb,0xff));
	}
	
	public ThemedLabel(String s, LabelType type) {
		super(s);
		if(type == LabelType.Normal) this.setForeground(new Color(0xbb,0xbb,0xff));
		else if(type == LabelType.Error) this.setForeground(new Color(0xff,0x88,0x88));
		else if(type == LabelType.Success) this.setForeground(new Color(0x88,0xff,0x88));
	}
	
	public void setType(LabelType type) {
		if(type == LabelType.Normal) this.setForeground(new Color(0xbb,0xbb,0xff));
		else if(type == LabelType.Error) this.setForeground(new Color(0xff,0x88,0x88));
		else if(type == LabelType.Success) this.setForeground(new Color(0x88,0xff,0x88));
	}

}
