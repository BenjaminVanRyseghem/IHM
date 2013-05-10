package support;

/**
 * Interface to propagte events about music player
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public interface MPMusicListener {

	public void musicStarted(int duration);
	public void musicPositionChanged(int position);
	public void musicEnded();
	
}
