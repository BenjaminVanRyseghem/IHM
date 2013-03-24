package updates;

import core.COView;

public class COClipBoardUpdate extends COUpdate {

	@Override
	public void updateOn(COView view) {
		view.update(this);
	}

}
