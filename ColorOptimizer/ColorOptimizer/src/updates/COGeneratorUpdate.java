package updates;

import core.COView;

public class COGeneratorUpdate extends COUpdate {

	@Override
	public void updateOn(COView view) {
		view.update(this);		
	}

}
