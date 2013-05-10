package updates;

import player.MPPlayerView;

/**
 * This update is triggered when two element have been switched
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public class MPSwitchUpdate extends MPUpdate {
	
	private int dragIndex;
	private int dropIndex;
	
	public MPSwitchUpdate(int dragIndex, int dropIndex) {
		this.dragIndex = dragIndex;
		this.dropIndex = dropIndex;
	}

	public int getDragIndex() {
		return dragIndex;
	}

	public int getDropIndex() {
		return dropIndex;
	}

	@Override
	public void applyTo(MPPlayerView view){
		view.update(this);
	}
}
