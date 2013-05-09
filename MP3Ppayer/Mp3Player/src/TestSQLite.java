import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TestSQLite {
	public static void main(String[] args) throws ClassNotFoundException
	{
		if( args.length == 0 )
		{
			System.out.println( "Usage: TestSQLite <filename>" );
			System.exit( 0 );
		}

		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try
		{
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:" + args[0]);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			int i = 1;
			ResultSet rs = statement.executeQuery("select * from songs");
			while(rs.next())
			{
				// read the result set
				System.out.println(i + " " + rs.getString("album") + " " + rs.getString("artist") + " " +
						rs.getString("title") + " " + rs.getString("genre") + " " + rs.getString("year") + " " + rs.getString("duration"));
				i++;
			}
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
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