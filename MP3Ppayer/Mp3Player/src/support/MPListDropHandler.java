package support;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JList;
import javax.swing.TransferHandler;

import player.MPPlayerModel;

/**
 * This class is used to handle correctly the drop mechanism of the @MPPlayerView list.
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public class MPListDropHandler extends TransferHandler {

	/**
	 * Generated UID for serialization
	 */
	private static final long serialVersionUID = 8344924218740415465L;
	JList list;
	MPPlayerModel model;

	  public MPListDropHandler(JList list, MPPlayerModel m) {
	    this.list = list;
	    model = m;
	  }

	  @Override
	public boolean canImport(TransferHandler.TransferSupport support) {
	    if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
	      return false;
	    }
	    JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
	    return (dl.getIndex() != -1);
	  }

	  @Override
	public boolean importData(TransferHandler.TransferSupport support) {
	    if (!canImport(support)) {
	      return false;
	    }

	    Transferable transferable = support.getTransferable();
	    String indexString;
	    try {
	      indexString = (String) transferable.getTransferData(DataFlavor.stringFlavor);
	    } catch (Exception e) {
	      return false;
	    }

	    int index = Integer.parseInt(indexString);
	    JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
	    int dropTargetIndex = dl.getIndex();

	    model.moveFromTo(index, dropTargetIndex, true);
	    return true;
	  }

}
