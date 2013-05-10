package actions;

import java.util.List;

import player.MPPlayerModel;

/**
 * Represent the action of replacing all of the music displayed in the list.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class MPAllReplacementAction extends MPAction {

	List<String[]> newList;
	List<String[]> oldList;
	
	/**
	 * the constructor that saves the data representing the 
	 * 
	 * @param prev
	 * @param model
	 * @param newList
	 * @param oldList
	 */
	public MPAllReplacementAction(MPAction prev, MPPlayerModel model, List<String[]> newList, List<String[]> oldList){
		super(prev, model);
		this.newList = newList;
		this.oldList = oldList;
	}

	public List<String[]> getNewList() {
		return newList;
	}

	public void setNewList(List<String[]> newList) {
		this.newList = newList;
	}

	public List<String[]> getOldList() {
		return oldList;
	}

	public void setOldList(List<String[]> oldList) {
		this.oldList = oldList;
	}

	@Override
	protected MPAction undo() {
		if(!canUndo()) return null;
		model.undoFrom(this);
		return previous;
	}
	
	@Override
	protected MPAction redo(){
		if(!canRedo()) return null;
		next.redoOn(model);
		return next;
	}
	
	@Override
	protected void redoOn(MPPlayerModel model){
		model.redoFrom(this);
	}
	
}
