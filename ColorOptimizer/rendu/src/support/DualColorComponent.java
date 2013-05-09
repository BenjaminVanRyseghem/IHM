package support;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.COModel;


/**
 * I am a class that contain two panel.
 * My first panel contain the color and the second one the grey scale of the color.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class DualColorComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The color panel.
	 * @uml.property  name="color"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JPanel color;
	
	/**
	 * The grey scale panel.
	 * @uml.property  name="gray"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JPanel gray;
	
	/**
	 * The index of this element in the list of DualColorComponent in the COModel
	 * @uml.property  name="index"
	 */
	int index;
	
	/**
	 * The model.
	 * 
	 * @uml.property  name="model"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	COModel model;
	
	/**
	 * The JScrollPane that contain the colors (column)
	 * @uml.property  name="scroller"
	 * @uml.associationEnd  
	 */
	JScrollPane scroller;
	
	/**
	 * Initialize the Panels and add them to itself.
	 * 
	 * @param m the model associated.
	 * @param col the color that will be putted on the JPanel color
	 */
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
	
	/**
	 * Sets the JScrollPane and the index of that element.
	 * Initialize the Panels and add them to itself.
	 * @param m
	 * @param idx
	 * @param col
	 * @param sc
	 */
	DualColorComponent(COModel m, int idx, Color col, JScrollPane sc){
		this(m, col);
		
		index = idx;
		scroller = sc;
	}
	
	

	/**
	 * Add a focus handling on this DualColorComponent in order to be able to display a blue rectangle.
	 */
	protected void addFocusHandling() {
		this.addFocusListener(new COFocusListener(this));
	    this.addMouseListener(new COMouseFocusListener(this));
	}
			

	/**
	 * Add the paste listener for this DualColorComponent
	 */
	protected void addPasteListener(){
		this.addKeyListener(new COPasteKeyListener(this));
		this.addMouseListener(new COPastePopupMenuListener(this));
	}
	
	/**
	 * Paste the previously copied color on the Panel color. 
	 */
	protected void paste(){
		Color col = this.model.paste();
		if (col == null) return;
		this.setBackground(col);
	}
	
	/**
	 * copy the color of the Panel color.
	 */
	protected void copy(){
		this.model.copy(this.color.getBackground());
	}
	
	/**
	 * Set the background of the color at the index place in the model.
	 */
	public void setBackground(Color col){
		model.setChosenColor(index, col);
	}
	
	/**
	 * Update the value of the background colors of the two JPanel.
	 * 
	 * @param col the new color.
	 */
	public void update(Color col){
		float grayScale = ((float)(col.getRed() * 0.30 / 255 + col.getGreen() * 0.59 / 255 + col.getBlue() * 0.11 / 255));	
		color.setBackground(col);
		gray.setBackground(new Color(grayScale, grayScale, grayScale));
	}
}
