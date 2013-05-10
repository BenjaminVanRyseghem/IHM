package updates;

import player.MPPlayerView;

/**
 * This update is triggered when the full list has changed
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public class MPAllUpdate extends MPUpdate {
	@Override
	public void applyTo(MPPlayerView view){
		view.update(this);
	}
}
