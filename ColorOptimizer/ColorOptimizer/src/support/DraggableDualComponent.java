package support;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

import core.COModel;

public class DraggableDualComponent extends DualColorComponent implements DragGestureListener {

	private static final long serialVersionUID = -216318349336826064L;

	public DraggableDualComponent(COModel model, int index, Color color){
		super(model, index, color);
		DragSource ds = new DragSource();
	    ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
	}
	
	@Override
	public void dragGestureRecognized(DragGestureEvent event) {
		Cursor cursor = null;
	
		Color color = this.color.getBackground();
		if (event.getDragAction() == DnDConstants.ACTION_COPY) {
			cursor = DragSource.DefaultCopyDrop;
		}
		event.startDrag(cursor, new TransferableColor(color));
	}

}
