package support;

import java.awt.Color;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JComponent;

public class DropTargetListener extends DropTargetAdapter {
	private DropTarget dropTarget;
	private JComponent panel;

	public DropTargetListener(JComponent panel) {
		this.panel = panel;
		dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, this, true, null);
	}

	public DropTarget dummyMethod(){
		return dropTarget;
	}
	
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
