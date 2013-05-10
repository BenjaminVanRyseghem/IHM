package support;


public interface MPMusicListener {

	public void musicStarted(int duration);
	public void musicPositionChanged(int position);
	public void musicEnded();
	
}
