package updates;

import player.MPPlayerView;

/**
 * This class acts as an abstract class
 * @author Lepan Fran�ois, Benjamin Van Ryseghem
 *
 */
public class MPUpdate {

	
	public void applyTo(MPPlayerView view){
		view.update(this);
	}
	
}
