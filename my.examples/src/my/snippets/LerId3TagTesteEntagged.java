package my.snippets;

import java.io.File;

import entagged.audioformats.AudioFile;
import entagged.audioformats.AudioFileIO;
import entagged.audioformats.Tag;
import entagged.audioformats.exceptions.CannotReadException;
import entagged.audioformats.exceptions.CannotWriteException;


public class LerId3TagTesteEntagged {

	/**
	 * @param args
	 * @throws CannotReadException 
	 * @throws CannotWriteException 
	 */
	public static void main(String[] args) throws CannotReadException, CannotWriteException {
		
		AudioFile file = AudioFileIO.read(new File("C:\\Ate o infinito-MixagemAntiga.mp3"));
		
		Tag tag = file.getTag();
		
		System.out.println("BITRATE:        "+file.getBitrate());
		System.out.println("NAME:           "+file.getName());
		System.out.println("FREE SPACE:     "+file.getFreeSpace());
		System.out.println("FILE LENGTH:    "+file.getLength());
		System.out.println("ENCODING TYPE:  "+file.getEncodingType());
		System.out.println("PRECISE LENGTH: "+file.getPreciseLength());
		System.out.println("USABLE SPACE:   "+file.getUsableSpace());
		System.out.println("TOTAL SPACE:    "+file.getTotalSpace());
		System.out.println("");
		System.out.println("***************************************************");
		System.out.println("");
		System.out.println("FIRST ALBUM:    "+tag.getFirstAlbum());
		System.out.println("FIRST ARTIST:   "+tag.getFirstArtist());
		System.out.println("FIRST COMMENT:  "+tag.getFirstComment());
		System.out.println("FIRST GENRE:    "+tag.getFirstGenre());
		System.out.println("FIRST TITLE:    "+tag.getFirstTitle());
		System.out.println("FIRST TRACK:    "+tag.getFirstTrack());
		System.out.println("FIRST YEAR:     "+tag.getFirstYear());
		System.out.println("ALBUM:          "+tag.getAlbum());
		System.out.println("ARTIST:         "+tag.getArtist());
		System.out.println("COMMNENT:       "+tag.getComment());
		System.out.println("FIELDS:         "+tag.getFields());
		System.out.println("GENRE:          "+tag.getGenre());
		System.out.println("TITLE:          "+tag.getTitle());
		System.out.println("TRACK:          "+tag.getTrack());
		System.out.println("YEAR:           "+tag.getYear());
		
		
		tag.setArtist("Banda 5 Sentidos");
		
		file.commit();
		
		System.out.println("");
		System.out.println("ARTISTA: "+tag.getArtist());
		

	}

}
