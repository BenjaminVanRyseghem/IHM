package support;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * I am a listener for the crtl-c command.
 * I copy the color of the selected element in the model.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class COCopyKeyListener implements KeyListener {

	/**
	 * The model associated to this listener.
	 * 
	 * @uml.property  name="model"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	DualColorComponent model;
	
	/**
	 * The basic constructor.
	 * Set the model associated to this listener.
	 * 
	 * @param model the model to be associated.
	 */
	public COCopyKeyListener(DualColorComponent model){
		this.model = model;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent event) {
		if(event.getModifiers() == Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() && event.getKeyChar() == 'c'){
			this.model.copy();
		}
	}
}