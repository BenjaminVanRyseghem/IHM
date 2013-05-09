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

	List<String[]> musics = new ArrayList<String[]>();

	public PlayerModel() {
		this.fillUpList();
	}

	public void fillUpList() {
		Connection connection = null;
		try
		{
			Class.forName("org.sqlite.JDBC");

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:/Users/WannaGetHigh/workspace/M1S2/IHM/MP3Ppayer/Mp3Player/mp3database.sqlite");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			//int i = 1;
			ResultSet rs = statement.executeQuery("select * from songs");

			//Vector<String[]> elements = new Vector<String[]>();
			String[] element = new String[6];
			
			while(rs.next())
			{
				element[0] = rs.getString("album");
				element[1] = rs.getString("artist");
				element[2] = rs.getString("title");
				element[3] = rs.getString("genre");
				element[4] = rs.getString("year");
				element[5] = rs.getString("duration");
				
				this.musics.add(element);

				// read the result set
//				System.out.println(rs.getString("album") + " " + rs.getString("artist") + " " 
//						+ rs.getString("title") + " " + rs.getString("genre") + " " 
//						+ rs.getString("year") + " " + rs.getString("duration"));

			}
			
			this.notifyObservers(this.musics);
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
				// connection close failed.
				System.err.println(e);
			}
		}
	}

	
	public List<String[]> getMusics() {
		return musics;
	}
	
	public void notifyObservers(Object arg){
		this.setChanged();
		super.notifyObservers(arg);
	}
	
	
	public static void main(String[] args) {
		new PlayerView();
	}
}
