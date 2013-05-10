package updates;

import core.COView;

/**
 * I am a class that update the colors generator.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 */
public class COGeneratorUpdate extends COUpdate {

	@Override
	public void updateOn(COView view) {
		view.update(this);		
	}

}
