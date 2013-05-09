package updates;

import java.awt.Color;

import core.COView;


/**
 * I am a class that update the chosen colors.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class COChosenColorUpdate extends COUpdate {

	/**
	 * The index of the color to be updated
	 * @uml.property  name="index"
	 */
	int index;
	
	/**
	 * The color to be set at the index place of the chosen color list.
	 * @uml.property  name="color"
	 */
	Color color;
	
	/**
	 * the basic constructor.
	 * Set the index and the color to be set
	 * 
	 * @param index the index
	 * @param color the color to be set at the index place of the chosen color list
	 */
	public COChosenColorUpdate(int index, Color color) {
		this.index = index;
		this.color = color;
	}

	@Override
	public void updateOn(COView view) {
		view.update(this);
	}
	
	/**
	 * Get the index.
	 * 
	 * @return
	 * @uml.property  name="index"
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 
	 * Get the color to be set.
	 * 
	 * @return
	 * @uml.property  name="color"
	 */
	public Color getColor() {
		return color;
	}

}
