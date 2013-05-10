package player;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.filechooser.FileFilter;

import support.MPID3TagRetriever;
import support.MPID3Tags;
import support.MPMusicListener;
import support.MPPlayer;
import support.MPSearchThread;
import updates.MPActionUpdate;
import updates.MPAllUpdate;
import updates.MPOneUpdate;
import updates.MPSwitchUpdate;

import actions.MPAction;
import actions.MPActionManager;
import actions.MPAllReplacementAction;
import actions.MPMoveToAction;

/**
 * I am the model of the MVC.
 * I make the change on the items content of the view and notify it.
 * 
 * @author Benjamin Van Ryseghem, Lepan Francois
 *
 */
public class MPPlayerModel extends Observable{

	/**
	 * the list that contain the music to be displayed depending on the search field.
	 */
	protected List<String[]> musics = new ArrayList<String[]>();
	
	/**
	 * the full music database. Contains all of the music from the SQL database 
	 * and the 3 songs of our choice. 
	 */
	protected List<String[]> database = new ArrayList<String[]>();
	
	/**
	 * the list of mp3
	 */
	protected List<String> mp3 = new ArrayList<String>();
	
	protected MPPlayer player = new MPPlayer();
	private String currentMP3 = "";
	
	protected String search = "";
	protected Thread thread;
	protected MPActionManager actionManager = new MPActionManager(this);
	
	public MPPlayerModel() {
		
		this.initMp3List();
		this.fillUpList();
	}

	
	private void initMp3List() {
		File dir = new File(getClass().getClassLoader().getResource("./data").getPath());
		
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".mp3");
			}
		};
		
		
		for(File file : dir.listFiles(filter)){
			mp3.add(file.getPath());
		}
		
		currentMP3 = mp3.get(0);
		player.Load(currentMP3);
	}

	
	public void fillUpDebugList() {
		database.add(new String[] {"aa", "aa", "aa"});
		database.add(new String[] {"ab", "ab", "ab"});
		database.add(new String[] {"ac", "ac", "ac"});
		database.add(new String[] {"ba", "ba", "ba"});
		database.add(new String[] {"bb", "bb", "bb"});
		database.add(new String[] {"bc", "bc", "bc"});
		database.add(new String[] {"ca", "ca", "ca"});
		database.add(new String[] {"cb", "cb", "cb"});
		database.add(new String[] {"cc", "cc", "cc"});
	}
	
	public void fillUpList() {
		
		MPID3TagRetriever retriever = new MPID3TagRetriever();
		
		for (String fileName : this.mp3) {
			MPID3Tags tag = retriever.retrieveTags(fileName);
			
			String[] info = new String[6];
			
			info[0] = tag.getAlbum();
			info[1] = tag.getArtist();
			info[2] = tag.getTitle();
			info[3] = tag.getGenre();
			info[4] = tag.getYear();
			info[5] = "";
			
			this.database.add(info);
		}
		
		
		
		Connection connection = null;
		try
		{
			Class.forName("org.sqlite.JDBC");

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:./mp3database.sqlite");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			ResultSet rs = statement.executeQuery("select * from songs");

			while(rs.next())
			{
				String[] element = new String[6];
				element[0] = rs.getString("album");
				element[1] = rs.getString("artist");
				element[2] = rs.getString("title");
				element[3] = rs.getString("genre");
				element[4] = rs.getString("year");
				element[5] = rs.getString("duration");
				
				this.database.add(element);

			}
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(connection != null)
					connection.close();
			}
			catch(SQLException e)
			{
				System.err.println(e);
			}
		}
	}

	protected void addPlayerListener(MPMusicListener listener){
		player.addListener(listener);
	}
	
	public void randomize(){
		List<String[]> newDatabase = new ArrayList<String[]>();
		
		for(String[] info : database){
			newDatabase.add(info);
		}
		
		Collections.shuffle(newDatabase);
		
		MPAction action = actionManager.addReplaceAllAction(newDatabase, database);
		this.database = newDatabase;
		this.notifyObservers(new MPAllUpdate());
		this.notifyObservers(new MPActionUpdate(action));
		
	}
	
	public List<String[]> getMusics() {
		if(search.isEmpty()) return database;
		
		return musics;
	}
	
	@Override
	public void notifyObservers(Object arg){
		this.setChanged();
		super.notifyObservers(arg);
	}
	
	public void addInMusic(String[] element){
		musics.add(element);
		
		this.notifyObservers(new MPOneUpdate(element));
	}
	
	
	public static void main(String[] args) {
		new MPPlayerView(new MPPlayerModel());
	}

	public void setSearch(String text) {
		if(this.search.equals(text)) return;
		if("".equals(text)) {
			this.search = text;
			this.notifyObservers(new MPAllUpdate());
			return;
		}
		
		this.search = text;
		
		musics.clear();
		this.notifyObservers(new MPAllUpdate());
		
		if(thread != null) thread.interrupt();
		
		thread = new MPSearchThread(text, this);
		thread.start();
	}

	public List<String[]> getDatabase() {
		return database;
	}

	public void redo() {
		MPAction action = this.actionManager.redo();
		if(action == null) return;
		this.notifyObservers(new MPActionUpdate(action));
	}


	public void undo() {
		MPAction action = this.actionManager.undo();
		if(action == null) return;
		this.notifyObservers(new MPActionUpdate(action));
	}
	

	public void redoFrom(MPAllReplacementAction allReplacementAction) {
		this.database = allReplacementAction.getNewList();
		notifyObservers(new MPAllUpdate());
	}


	public void undoFrom(MPAllReplacementAction allReplacementAction) {
		this.database = allReplacementAction.getOldList();
		notifyObservers(new MPAllUpdate());
	}
	
	public boolean canUndo() {
		return actionManager.canUndo();
	}
	
	public boolean canRedo() {
		return actionManager.canRedo();
	}


	public void undoFrom(MPAction action) {
		// Do nothing
	}


	public void redoFrom(MPAction next) {
		//Do nothing
	}
	
	public void undoFrom(MPMoveToAction action) {
		this.moveFromTo(action.getTo(), action.getFrom(), false);
	}


	public void redoFrom(MPMoveToAction action) {
		this.moveFromTo(action.getFrom(), action.getTo(), false);
	}
	
	
	public void moveFromTo(int dragIndex, int dropIndex, boolean notify) {
		
		if (dragIndex == dropIndex) return;

		String[] info = database.remove(dragIndex);
		database.add(dropIndex, info); 
			
		this.notifyObservers(new MPSwitchUpdate(dragIndex, dropIndex));
		if(notify){
			MPAction action = actionManager.addMoveToAction(dragIndex, dropIndex);
			this.notifyObservers(new MPActionUpdate(action));
		}
	}

	public float getVolume(){
		return player.getVolume();
	}
	
	public void setVolume(float volume){
		player.setVolume(volume);
	}
	
	public void playOrPause() {
		player.PlayPause();
	}

	public void setPlayerPosition(int value) {
		player.setPosition(value);
	}
	
	protected void playNextSong(){
		player.Stop();
		int index = mp3.indexOf(currentMP3) + 1;
		if (index == mp3.size()) index = 0;
		
		Object timer = new Object();
		synchronized(timer){
			try {
				timer.wait(100);
			} catch (InterruptedException e) {
				return;
			}
			
			currentMP3 = mp3.get(index);
			player.Load(currentMP3);
			player.PlayPause();
		};
	}
	
	protected void playPreviousSong(){
		player.Stop();
		int index = mp3.indexOf(currentMP3) -1;
		if (index == -1) index = mp3.size()-1;

		Object timer = new Object();
		synchronized(timer){
			try {
				timer.wait(100);
			} catch (InterruptedException e) {
				return;
			}
			
			currentMP3 = mp3.get(index);
			player.Load(currentMP3);
			player.PlayPause();
		};
	}
	
	public String getCurrentMp3Name() {
		return this.currentMP3.substring(this.currentMP3.lastIndexOf("/")+1);
	}


	public String getRemainingTime() {
		int duration = this.player.getDuration();
		int currentPosition = this.player.getPosition();
		
		int remainingTime = duration - currentPosition;
		
		int seconds = remainingTime / 1000;
		int minutes = seconds / 60;
		seconds = seconds - minutes*60;
		
		return "- " + minutes+ ":" + seconds;
	}


	public String getTimeElapsed() {
		int currentPosition = this.player.getPosition();
		
		int seconds = currentPosition / 1000;
		int minutes = seconds / 60;
		seconds = seconds - minutes*60;
		
		return minutes+ ":" + seconds;
	}
}
