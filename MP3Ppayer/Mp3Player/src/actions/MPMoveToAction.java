package actions;

import player.MPPlayerModel;

public class MPMoveToAction extends MPAction {

	int from, to;
	public MPMoveToAction(MPAction lastAction, MPPlayerModel model, int from, int to) {
		super(lastAction, model);
		this.from = from;
		this.to = to;
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

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

}
