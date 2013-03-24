package support;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.COModel;

public class DualColorComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	JPanel color;
	JPanel gray;
	int index;
	COModel model;
	JScrollPane scroller;
	
	
	DualColorComponent(COModel m, Color col){
		model = m;
		color = new JPanel();
		gray = new JPanel();
		this.setFocusable(true);
		
	    this.addFocusHandling();
		
	    this.addPasteListener();
	    
		this.update(col);
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(color);
		this.add(gray);
	}

	protected void addFocusHandling() {
		this.addFocusListener(new COFocusListener(this));
	    this.addMouseListener(new COMouseFocusListener(this));
	}
			
	DualColorComponent(COModel m, int idx, Color col, JScrollPane sc){
		this(m, col);
		
		index = idx;
		scroller = sc;
	}
	
	protected void addPasteListener(){
		this.addKeyListener(new COPasteKeyListener(this));
	}
	
	protected void paste(){
		Color col = this.model.paste();
		if (col == null) return;
		this.setBackground(col);
	}
	
	protected void copy(){
		this.model.copy(this.color.getBackground());
	}
	
	public void setBackground(Color col){
		model.setChosenColor(index, col);
	}
	
	public void update(Color col){
		float grayScale = ((float)(col.getRed() * 0.30 / 255 + col.getGreen() * 0.59 / 255 + col.getBlue() * 0.11 / 255));	
		color.setBackground(col);
		gray.setBackground(new Color(grayScale, grayScale, grayScale));
	}

	public JScrollPane getScroller() {
		return scroller;
	}
	
}
