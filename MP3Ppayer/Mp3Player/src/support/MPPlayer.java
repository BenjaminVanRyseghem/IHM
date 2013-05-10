package support;
import java.util.ArrayList;
import java.util.List;

import javazoom.jl.decoder.Equalizer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.LillePlayer;

/**
 * This class is in charge of providing a façade for music playing.
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public class MPPlayer {
	
	private LillePlayer player;
	private String currentPath;
	
	private int state;  //0:stop, 1:load, 2:play
	private float volume = 1;
	private int position = 0;
	
	List<MPMusicListener> listeners;
	
	public void addListener(MPMusicListener listener){
		listeners.add(listener);
	}
	
	public void removeListener(MPMusicListener listener){
		listeners.remove(listener);
	}

	public MPPlayer() {
		player = null;
		listeners = new ArrayList<MPMusicListener>();
		currentPath = "";
		state = 0;
	}
		
	protected void fireMusicStarted(int duration){
		for(MPMusicListener l : listeners){
			l.musicStarted(duration);
		}
	}
	
	protected void fireMusicEnded(){
		for(MPMusicListener l : listeners){
			l.musicEnded();
		}
	}
	
	protected void fireMusicPositionChanged(int position){
		for(MPMusicListener l : listeners){
			l.musicPositionChanged(position);
		}
	}
	
	public void Load(String path){
		if(state != 0)
			Stop();
		
		try{
			currentPath = path;
			player = new LillePlayer(currentPath);
			
			player.setVolume(volume);
			Equalizer eq = new Equalizer();
			eq.getBand(0);
			state = 1;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void PlayPause(){
		if(state == 0){
			Load(currentPath);
			PlayPause();
		}else if(state == 1){
			LaunchListenThread llt = new LaunchListenThread(player);
			llt.start();
			state = 2;
		}else if(state == 2){
			player.pause();
		}
	}
	
	public void Stop(){			
		if(state == 1 || state == 2){
			player.close();
			state = 0;
		}
	}
	
	public float getVolume(){
		return volume;
	}
	
	public void setVolume(float level){
		volume = level;
		player.setVolume(level);
	}
	
	public int getDuration(){
		if(player == null)
			return 0;
		return player.getDuration();
	}
	
	public LillePlayer getMediaPlayer(){
		return player;
	}
	
	public int getPosition(){
		return position;
	}
	
	public void setPosition(int pos){
		player.setPosition(pos);
		position = pos;
	}
	
	
	class LaunchListenThread extends Thread{
		private LillePlayer playerInterne;
		public LaunchListenThread(LillePlayer p){
			playerInterne = p;
		}
		@Override
		public void run(){
			try{			
				MPPlayer.this.fireMusicStarted(playerInterne.getDuration());
				PlayThread pt = new PlayThread();
				pt.start();
				
				while(!playerInterne.isComplete()){
					position = playerInterne.getPosition();
					if(player == playerInterne)
						MPPlayer.this.fireMusicPositionChanged(position);
					try{
						Thread.sleep(200);
					}catch(Exception e){ e.printStackTrace(); }
				}
				
				if(player == playerInterne)
					MPPlayer.this.fireMusicEnded();
			}catch(Exception e){ e.printStackTrace(); }
		}
		
		class PlayThread extends Thread{
			@Override
			public void run(){
				try {
					playerInterne.play();
				}catch(JavaLayerException e){ e.printStackTrace(); }
			}
		}
	}
	
//	public static void main( String[] args )
//	{
//		if( args.length == 0 )
//		{
//			System.out.println( "Usage: MyPlayer <filename>" );
//			System.exit( 0 );
//		}
//
//		MyPlayer myPlayer = new MyPlayer();
//		myPlayer.Load(args[ 0 ]);
//		myPlayer.PlayPause();
//	}	
}