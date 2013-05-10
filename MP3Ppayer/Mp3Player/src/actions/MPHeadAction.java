package actions;

import player.MPPlayerModel;

/**
 * This class is a null pattern object used to represent the first item of the actions list.
 * Indeed, even if this action do nothing, it should keep a pointer to the next action if present.
 * Otherwise the link will be broken.
 * (if you undo the first not null action, you have null as current action, and you lost the capability to redo).
 * 
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public class MPHeadAction extends MPAction {
	
	public MPHeadAction(MPAction prev, MPPlayerModel model) {
		super(prev, model);
	}

	@Override
	protected MPAction undo() {
		return this;
	}
	
	@Override
	public boolean canUndo(){
		return false;
	}

}
