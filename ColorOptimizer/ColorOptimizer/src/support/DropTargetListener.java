package support;

import java.awt.Color;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JComponent;


/**
 * I am a listener that drop the dragged element onto the dropTarget.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class DropTargetListener extends DropTargetAdapter {
	/**
	 * The drop wrapper.
	 *  
	 * @uml.property  name="dropTarget"
	 */
	private DropTarget dropTarget;
	
	
	/**
	 * The panel on which the color will change 
	 * 
	 * @uml.property  name="panel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JComponent panel;
	
	
	/**
	 * The basic constructor.
	 * 
	 * Set the panel and the drop target.
	 * 
	 * @param panel
	 */
	public DropTargetListener(JComponent panel) {
		this.panel = panel;
		dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, this, true, null);
	}

	@Override
	public void drop(DropTargetDropEvent event) {
		try {
			Transferable tr = event.getTransferable();
			Color color = (Color) tr.getTransferData(TransferableColor.colorFlavor);
			if (event.isDataFlavorSupported(TransferableColor.colorFlavor)) {
				event.acceptDrop(DnDConstants.ACTION_COPY);
				this.panel.setBackground(color);
				event.dropComplete(true);
				return;
			}
			
			event.rejectDrop();
		} catch (Exception e) {
			e.printStackTrace();
			event.rejectDrop();
		}
	}
}
