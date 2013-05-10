package player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

public class PlayerModel extends Observable{

	protected List<String[]> musics = new ArrayList<String[]>();
	protected List<String[]> database = new ArrayList<String[]>();
	protected String search = "";
	protected Thread thread;
	
	public PlayerModel() {
		this.fillUpDebugList();
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
		Connection connection = null;
		try
		{
			Class.forName("org.sqlite.JDBC");

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:./mp3database.sqlite");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			//int i = 1;
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

	
	public List<String[]> getMusics() {
		if(search.isEmpty())
			return database;
		return musics;
	}
	
	public void notifyObservers(Object arg){
		this.setChanged();
		super.notifyObservers(arg);
	}
	
	protected void addInMusic(String[] element){
		musics.add(element);
		this.notifyObservers(new MPOneUpdate(element));
	}
	
	
	public static void main(String[] args) {
		new PlayerView();
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
		thread = new SearchThread(text, this);
		thread.start();
	}

	public List<String[]> getDatabase() {
		return database;
	}
}
