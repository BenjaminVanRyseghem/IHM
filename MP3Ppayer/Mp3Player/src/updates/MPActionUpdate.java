package updates;

import actions.MPAction;
import player.MPPlayerView;

/**
 * This update is triggered when an action has been added
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public class MPActionUpdate extends MPUpdate {
	MPAction action;
	
	public MPActionUpdate(MPAction a){
		action = a;
	}
	
	public boolean canUndo(){
		return action.canUndo();
	}
	
	public boolean canRedo(){
		return action.canRedo();
	}
	
	@Override
	public void applyTo(MPPlayerView view){
		view.update(this);
	}
}
