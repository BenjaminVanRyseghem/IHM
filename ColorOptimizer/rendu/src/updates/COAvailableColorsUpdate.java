package updates;

import core.COView;

/**
 * I am a class that update the available colors.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class COAvailableColorsUpdate extends COUpdate {

	@Override
	public void updateOn(COView view) {
		view.update(this);
	}

}
