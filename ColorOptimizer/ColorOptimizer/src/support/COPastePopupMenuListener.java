package support;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;


/**
 * I am a Listener for the element in the contextual menu.
 * I paste the previously copied element on the selected element.
 *  
 * @author Benjamin Van Ryseghem, Francois Lepan
 */
public class COPastePopupMenuListener implements MouseListener {
	
	/**
	 * The selected element.
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
	public COPastePopupMenuListener(DualColorComponent dualColorComponent){
		this.component = dualColorComponent;
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent event) {
		if(SwingUtilities.isRightMouseButton(event)){
			JPopupMenu menu = new JPopupMenu("Menu");
			JMenuItem item = new JMenuItem("Paste color", KeyEvent.VK_P);
			item.addActionListener(new COPasteMenuActionListener(this.component));
			menu.add(item);
			menu.show(event.getComponent(), event.getX(), event.getY());
		}
	}
}