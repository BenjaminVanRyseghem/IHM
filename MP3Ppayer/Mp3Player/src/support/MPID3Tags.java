package support;

/**
 * This class warps ID3 tags data into an object.
 * 
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public class MPID3Tags {
	
	/**
	 * the title tag
	 */
	String title="";
	
	/**
	 * the artist tag
	 */
	String artist="";
	
	/**
	 * the album tag
	 */
	String album="";
	
	/**
	 * the genre tag
	 */
	String genre="";
	
	/**
	 * the year tag
	 */
	String year="";
	
	/**
	 * the duration tag
	 */
	String duration="";

	/**
	 * The basic constructor.
	 */
	public MPID3Tags(String title, String artist, String album, String genre,
			String year, String duration) {
		super();
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.genre = genre;
		this.year = year;
		this.duration = duration;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
}
