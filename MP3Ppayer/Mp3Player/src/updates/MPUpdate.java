package updates;

import player.MPPlayerView;

/**
 * This class acts as an abstract class
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public class MPUpdate {

	
	public void applyTo(MPPlayerView view){
		view.update(this);
	}
	
}
