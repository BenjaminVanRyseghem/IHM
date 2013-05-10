package updates;

import player.MPPlayerView;

public class MPAllUpdate extends MPUpdate {
	@Override
	public void applyTo(MPPlayerView view){
		view.update(this);
	}
}
