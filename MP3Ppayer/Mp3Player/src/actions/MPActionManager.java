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
	
	/**
	 * The basic constructor. 
	 * sets the model on which the action are saved, and set the Head action.  
	 * 
	 * @param model
	 */
	public MPActionManager(MPPlayerModel model){
		this.model = model;
		lastAction = new MPHeadAction(null, model);
	}

	protected void setAction(MPAction action){
			lastAction = action;
	}
	
	/**
	 * can this action undo ?
	 */
	public boolean canUndo(){
		return lastAction.canUndo();
	}
	
	/**
	 * can this action redo ?
	 */
	public boolean canRedo(){
		return lastAction.canRedo();
	}
	
	/**
	 * Undo the last action
	 */
	public MPAction undo(){
		if(! this.canUndo()) return null;
		lastAction = lastAction.undo();
		return lastAction;
	}
	
	/**
	 * Redo the action
	 */
	public MPAction redo(){
		if(! this.canRedo()) return null;
		lastAction = lastAction.redo();
		return lastAction;
	}
	
	/**
	 * Add an action that replace all of the list of music displayed.
	 * 
	 * @param newList 
	 * @param oldList
	 * @return the MPAction representing this action that stores the changed data.
	 */
	public MPAction addReplaceAllAction(List<String[]> newList, List<String[]> oldList){
		MPAction newAction = new MPAllReplacementAction(lastAction, model, newList, oldList);
		lastAction = newAction;
		return newAction;
	}

	/**
	 * Add an action that replace a part of the list of music displayed.
	 * 
	 * @param newList 
	 * @param oldList
	 * @return the MPAction representing this action that stores the changed data.
	 */
	public MPAction addMoveToAction(int from, int to) {
		MPAction newAction = new MPMoveToAction(lastAction, model, from, to);
		lastAction = newAction;
		return newAction;
	}
	
}
