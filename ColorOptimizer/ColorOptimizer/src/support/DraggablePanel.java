package support;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

import javax.swing.JPanel;

public class DraggablePanel extends JPanel implements DragGestureListener {

	private static final long serialVersionUID = -216318349336826064L;

	public DraggablePanel(){
		DragSource ds = new DragSource();
	    ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
	}
	
	@Override
	public void dragGestureRecognized(DragGestureEvent event) {
		Cursor cursor = null;
		JPanel panel = (JPanel) event.getComponent();
	
		Color color = panel.getBackground();
		if (event.getDragAction() == DnDConstants.ACTION_COPY) {
			cursor = DragSource.DefaultCopyDrop;
		}
		event.startDrag(cursor, new TransferableColor(color));
	}

}
