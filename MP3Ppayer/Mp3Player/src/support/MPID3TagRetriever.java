package support;

import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v1;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.AbstractID3v2Frame;

public class MPID3TagRetriever {

	public MPID3Tags retrieveTags(String filename){
		MP3File file = this.retrieveMP3File(filename);
		return this.retrieveInformation(file);
	}

	private MPID3Tags retrieveInformation(MP3File file) {
		String title="", artist="", album="", genre="", year="", duration="";

		if(file.hasID3v1Tag()) {

			AbstractID3v1 tag = file.getID3v1Tag();

			if(tag != null) {
				title = tag.getSongTitle();
				artist = tag.getLeadArtist();
				album = tag.getAlbumTitle(); 
				genre = tag.getSongGenre(); 
				year = tag.getYearReleased(); 
			}

		} else if(file.hasID3v2Tag()) {
			AbstractID3v2 tag = file.getID3v2Tag();
			if(tag != null){
				AbstractID3v2Frame frame;

				frame = tag.getFrame("TT2");
				if (frame != null) title = frame.toString();

				frame = tag.getFrame("TP1");
				if (frame != null) artist = frame.toString();

				frame = tag.getFrame("TAL");
				if (frame != null) album = frame.toString();

				frame = tag.getFrame("TYE");
				if (frame != null) year = frame.toString();

				frame = tag.getFrame("TCO");
				if (frame != null) genre = frame.toString();

				frame = tag.getFrame("TLEN");
				if (frame != null) duration = frame.toString();
			}
		}
		return new MPID3Tags(title, artist, album, genre, year, duration);
	}

	private MP3File retrieveMP3File(String filename) {
		MP3File mp3file = null;
		File sourcefile = new File(filename);
		try {
			mp3file = new MP3File(sourcefile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		}
		
		return mp3file;
	}
}
