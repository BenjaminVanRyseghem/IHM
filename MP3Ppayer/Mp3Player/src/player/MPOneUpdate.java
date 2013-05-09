package player;

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

	protected void applyTo(PlayerView view){
		view.update(this);
	}
}
