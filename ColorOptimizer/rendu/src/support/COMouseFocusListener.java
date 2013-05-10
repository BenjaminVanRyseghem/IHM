package support;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * I am a class that request the focus of an element on click.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 */
public class COMouseFocusListener implements MouseListener {

	/**
	 * The element that is clicked on.
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
	public COMouseFocusListener(DualColorComponent model){
		this.model = model;
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		this.model.requestFocus();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
