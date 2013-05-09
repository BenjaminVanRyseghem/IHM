package player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JList;

public class ListHandler {

	public ListHandler() {

		fillUpList();
	}


	public void fillUpList() {

		// load the sqlite-JDBC driver using the current class loader
		

		Connection connection = null;
		try
		{
			Class.forName("org.sqlite.JDBC");
			
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:/Users/WannaGetHigh/workspace/M1S2/IHM/MP3Ppayer/Mp3Player/mp3database.sqlite");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			int i = 1;
			ResultSet rs = statement.executeQuery("select * from songs");
			
			Vector<String> titles = new Vector<String>();
			
			while(rs.next())
			{

				String music =  rs.getString("album") + " " + rs.getString("artist") + " " +
						rs.getString("title") + " " + rs.getString("genre") + " " + rs.getString("year") + " " + rs.getString("duration");
				
				titles.add(music);
						
						// read the result set
//				System.out.println(i + " " + rs.getString("album") + " " + rs.getString("artist") + " " +
//						rs.getString("title") + " " + rs.getString("genre") + " " + rs.getString("year") + " " + rs.getString("duration"));
//				i++;
				
			}
			
			//this.list.setListData(titles);
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
}
