package updates;

import core.COView;

/**
 * I am an abstract class that is used to simplify the way elements are updated
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 */
public abstract class COUpdate {
	
	/**
	 * update the view depending on the class that inherit this one  
	 * 
	 * @param view the view to be updated.
	 */
	public abstract void updateOn(COView view);
}
