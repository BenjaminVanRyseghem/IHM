package support;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * I am a Listener for the selected element in the columns.
 * I copy the chosen element.
 *  
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class COCopyMenuActionListener implements ActionListener {
	
	/**
	 * The element to be copied
	 * 
	 * @uml.property  name="component"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	DualColorComponent component;
	
	/**
	 * The basic constructor.
	 * Set the component value.
	 * 
	 * @param component the component value.
	 */
	public COCopyMenuActionListener(DualColorComponent component){
		this.component = component;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.component.copy();
	}
}
