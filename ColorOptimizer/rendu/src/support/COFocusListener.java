package support;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.border.LineBorder;

/**
 * I am a class that display the focus of elements by displaying a blue rectangle around them.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 */
public class COFocusListener implements FocusListener {

	/**
	 * The element focused.
	 * 
	 * @uml.property  name="model"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	DualColorComponent model;

	/**
	 * The basic constructor.
	 * Set the model value.
	 * 
	 * @param model the model value.
	 */
	public COFocusListener(DualColorComponent model){
		this.model = model;
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		this.model.setBorder(new LineBorder(Color.blue, 2) );
		((COColorChooser)(this.model.getParent())).scrollRectToVisible(this.model.getBounds());
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		this.model.setBorder(new LineBorder(Color.blue, 0) );
	}
}