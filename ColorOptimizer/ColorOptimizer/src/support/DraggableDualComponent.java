package support;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

import javax.swing.JScrollPane;

import core.COModel;

/**
 * I am a class that wrap the DualColorComponent to make it draggable.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class DraggableDualComponent extends DualColorComponent implements DragGestureListener {

	private static final long serialVersionUID = -216318349336826064L;

	/**
	 * The basic constructor.
	 * 
	 * Set the values needed by the Class DualColorComponent and create a Drag Gesture Recognizer.
	 * 
	 * @param model the model associated to the view
	 * @param index the index of the element
	 * @param color the color of the element
	 * @param sc the JScrollPane that contain the list of element (the column)
	 */
	public DraggableDualComponent(COModel model, int index, Color color, JScrollPane sc){
		super(model, index, color, sc);
		
		DragSource ds = new DragSource();
	    ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
	    	    
	    this.addKeyListener(new COCopyKeyListener(this));
	    this.addMouseListener(new COCopyPopupMenuListener(this));
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
	
	@Override
	protected void addPasteListener(){}
}
