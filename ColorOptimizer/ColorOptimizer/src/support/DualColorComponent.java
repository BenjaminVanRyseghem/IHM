package support;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import core.COModel;

public class DualColorComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	JPanel color;
	JPanel gray;
	int index;
	COModel model;
	
	DualColorComponent(COModel m, int idx, Color col){
		model = m;
		index = idx;
		color = new JPanel();
		gray = new JPanel();
		
		this.update(col);
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(color);
		this.add(gray);
	}
	
	public void setBackground(Color col){
		
		model.setChosenColor(index, col);
	}
	
	public void update(Color col){
		float grayScale = ((float)(col.getRed() * 0.30 / 255 + col.getGreen() * 0.59 / 255 + col.getBlue() * 0.11 / 255));	
		color.setBackground(col);
		gray.setBackground(new Color(grayScale, grayScale, grayScale));
	}
	
}
