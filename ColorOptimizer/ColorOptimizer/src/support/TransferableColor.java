package support;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 * I am a class that wrap the Color to make them Transferable.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 */
class TransferableColor implements Transferable {
	
	/**
	 * the DataFlavor for this class.
	 */
	protected static DataFlavor colorFlavor = new DataFlavor(Color.class, "A Color Object");
	
	/**
	 * The supported DataFlavor for this class.
	 */
	protected static DataFlavor[] supportedFlavors = { colorFlavor };

	/**
	 * The color wrapped.
	 * 
	 * @uml.property  name="color"
	 */
	Color color;

	/**
	 * Set the value of the wrapped Color.
	 * 
	 * @param color the color wrapped.
	 */
	public TransferableColor(Color color) {
		this.color = color;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}


	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (flavor.equals(colorFlavor) || flavor.equals(DataFlavor.stringFlavor))
			return true;
		return false;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException {
		if (flavor.equals(colorFlavor))
			return color;
		else if (flavor.equals(DataFlavor.stringFlavor))
			return color.toString();
		else
			throw new UnsupportedFlavorException(flavor);
	}
}