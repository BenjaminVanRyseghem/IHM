package updates;

import player.MPPlayerView;

public class MPOneUpdate extends MPUpdate {
	String[] element;
	
	public MPOneUpdate(String[] element) {
		this.element = element;
	}

	public String[] getElement() {
		return element;
	}

	public void setElement(String[] element) {
		this.element = element;
	}

	@Override
	public void applyTo(MPPlayerView view){
		view.update(this);
	}
}
