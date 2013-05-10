package player;

public class MPAllUpdate extends MPUpdate {
	protected void applyTo(PlayerView view){
		view.update(this);
	}
}
