package support;

import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v1;
import org.farng.mp3.id3.AbstractID3v2;

public class MPID3TagRetriever {

	public MPID3Tags retrieveTags(String filename){
		MP3File file = this.retrieveMP3File(filename);
		return this.retrieveInformation(file);
	}

	private MPID3Tags retrieveInformation(MP3File file) {
		String title="", artist="", album="", genre="", year="";
		
		if(file.hasID3v1Tag()){
			AbstractID3v1 tag = file.getID3v1Tag();
			if(tag != null){
				try{ title = tag.getSongTitle(); }catch(Exception e){}
				try{ artist = tag.getLeadArtist(); }catch(Exception e){}
				try{ album = tag.getAlbumTitle(); }catch(Exception e){}
				try{ genre = tag.getSongGenre(); }catch(Exception e){}
				try{ year = tag.getYearReleased(); }catch(Exception e){}
			}
		}else if(file.hasID3v2Tag()){
			AbstractID3v2 tag = file.getID3v2Tag();
			if(tag != null){
				title = tag.getFrame("TT2").toString();
				artist = tag.getFrame("TP1").toString();
				album = tag.getFrame("TAL").toString();
				year = tag.getFrame("TYE").toString();
				genre = tag.getFrame("TCO").toString();
			}
		}
		return new MPID3Tags(title, artist, album, genre, year);
	}

	private MP3File retrieveMP3File(String filename) {
		MP3File mp3file = null;
		File sourcefile = new File(filename);
			try {
				mp3file = new MP3File(sourcefile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TagException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return mp3file;
	}
}
