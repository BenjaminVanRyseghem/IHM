package actions;

import java.util.List;

import player.MPPlayerModel;

/**
 * This class is in charge of managing the actions.
 * It acts as a façade for actions creation, and is the entry point for undo/redo actions.
 * 
 * @author Lepan François, Benjamin Van Ryseghem
 */
public class MPActionManager {

	protected MPAction lastAction;
	protected MPPlayerModel model;
	
	public MPActionManager(MPPlayerModel model){
		this.model = model;
		lastAction = new MPHeadAction(null, model);
	}
	
	protected void setAction(MPAction action){
			lastAction = action;
	}
	
	public boolean canUndo(){
		return lastAction.canUndo();
	}
	
	public boolean canRedo(){
		return lastAction.canRedo();
	}
	
	public MPAction undo(){
		if(! this.canUndo()) return null;
		lastAction = lastAction.undo();
		return lastAction;
	}
	
	public MPAction redo(){
		if(! this.canRedo()) return null;
		lastAction = lastAction.redo();
		return lastAction;
	}
	
	public MPAction addReplaceAllAction(List<String[]> newList, List<String[]> oldList){
		MPAction newAction = new MPAllReplacementAction(lastAction, model, newList, oldList);
		lastAction = newAction;
		return newAction;
	}

	public MPAction addMoveToAction(int from, int to) {
		MPAction newAction = new MPMoveToAction(lastAction, model, from, to);
		lastAction = newAction;
		return newAction;
	}
	
}
