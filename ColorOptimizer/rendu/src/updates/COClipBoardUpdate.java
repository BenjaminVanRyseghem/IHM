package updates;

import core.COView;

/**
 * I am a class that update the clip board.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 */
public class COClipBoardUpdate extends COUpdate {

	@Override
	public void updateOn(COView view) {
		view.update(this);
	}

}
