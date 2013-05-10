package actions;

import player.MPPlayerModel;

/**
 * 
 * This class represents an action as a node of a doubly-linked list.
 * Indeed, each node knows the next node and the previous one.
 * This way, the manager can easily navigate through actions to undo/redo them.
 * 
 * Note that the first node as null as previous action, and the last one has null as next action.
 * 
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public abstract class MPAction {
	
	protected MPAction previous;
	protected MPAction next;
	protected MPPlayerModel model;
	
	public MPAction(MPAction prev, MPPlayerModel m){
		model = m;
		this.previous = prev;
		if(prev != null)
			prev.next = this;
	}
	
	/**
	 * Redo the action
	 * @return the new last action (most often this). Returns null if this action can not be re-done
	 */
	protected MPAction redo(){
		if(!canRedo()) return null;
		next.redoOn(model);
		return next;
	}
	
	/**
	 * Dispatch the message to get the dynamic type of the action instead of the compilation one.
	 * @param model, the model to dispatch to
	 */
	protected void redoOn(MPPlayerModel model){
		model.redoFrom(this);
	}
	
	/**
	 * Undo the action. By default I dispatch this action to the model
	 * @return the new last action (most often the previous action). Returns null if this action can not be undone
	 */
	protected MPAction undo() {
		if(!canUndo()) return null;
		model.undoFrom(this);
		return previous;
	}
	
	/**
	 * Can always undo (because the first action of the list is a HeadAction)
	 * @return true
	 */
	public boolean canUndo(){
		return true;
	}
	
	/**
	 * Can this action redo itself. It can if it is not the last item
	 * @return if this action can be re-done.
	 */
	public boolean canRedo(){
		return next != null;
	}	
}
