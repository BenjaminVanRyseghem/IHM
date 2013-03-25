package support;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * I am a Listener for the selected element in the columns.
 * I paste the copied element on the selected component.
 *  
 * @author Benjamin Van Ryseghem, Francois Lepan
 */
public class COPasteMenuActionListener implements ActionListener {
	
	/**
	 * The selected component
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
	public COPasteMenuActionListener(DualColorComponent component){
		this.component = component;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.component.paste();
	}
}
