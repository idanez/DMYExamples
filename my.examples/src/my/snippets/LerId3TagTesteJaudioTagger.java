package my.snippets;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.ID3v24Tag;


public class LerId3TagTesteJaudioTagger {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			testeJaudioTagger();
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

	private static void testeJaudioTagger() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		
		File file = new File("C:\\Ate o infinito-MixagemAntiga.mp3");
		MP3File mp3File = (MP3File)AudioFileIO.read(file);
		
		MP3AudioHeader mp3AudioHeader = (MP3AudioHeader) mp3File.getAudioHeader();
		
		System.out.println("Track Lenght: "+mp3AudioHeader.getTrackLength());
		System.out.println("Sample rate as number: "+mp3AudioHeader.getSampleRateAsNumber());
		System.out.println("Chanels: "+mp3AudioHeader.getChannels());
		System.out.println("Is Varaible Bit Rate: "+mp3AudioHeader.isVariableBitRate());
		
		System.out.println("Track lenght as string: "+mp3AudioHeader.getTrackLengthAsString());
		System.out.println("Mpeg Version: "+mp3AudioHeader.getMpegVersion());
		System.out.println("Mpeg Layer: "+mp3AudioHeader.getMpegLayer());
		System.out.println("is original: "+mp3AudioHeader.isOriginal());
		System.out.println("is copyrighted: "+mp3AudioHeader.isCopyrighted());
		System.out.println("is private: "+mp3AudioHeader.isPrivate());
		System.out.println("is protected: "+mp3AudioHeader.isProtected());
		System.out.println("Bit rate: "+mp3AudioHeader.getBitRate());
		System.out.println("Encoding type: "+mp3AudioHeader.getEncodingType());
		
		Tag tag= mp3File.getTag();
		AbstractID3v2Tag v2Tag  = mp3File.getID3v2Tag();
		ID3v24Tag        v24Tag = (ID3v24Tag) mp3File.getID3v2TagAsv24();
	
		if(mp3File.hasID3v2Tag()) {
			
			//Lendo
			System.out.println(tag.getFirst(FieldKey.MUSICIP_ID));
			
			System.out.println("Artista: "+v24Tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST));
			System.out.println("Frame Id Album: "+v24Tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM));
			System.out.println("Frame id year: "+v24Tag.getFirst(ID3v24Frames.FRAME_ID_YEAR));	
			System.out.println("Initial Key: "+v24Tag.getFirst(ID3v24Frames.FRAME_ID_INITIAL_KEY));	
			System.out.println("BPM: "+v24Tag.getFirst(ID3v24Frames.FRAME_ID_BPM));
			System.out.println("Frame id lenght: "+v24Tag.getFirst(ID3v24Frames.FRAME_ID_LENGTH));
			System.out.println("Frame id orig title: "+v24Tag.getFirst(ID3v24Frames.FRAME_ID_ORIG_TITLE));
			System.out.println("Frame id TITLE: "+v24Tag.getFirst(ID3v24Frames.FRAME_ID_TITLE));	
			
			//Escrever (Obs. Nos primeiros testes, nao funcionou:
			tag.setField(FieldKey.ARTIST,"5 Sentidos");
			tag.setField(FieldKey.ALBUM_ARTIST, "5 Sentidos");
			
			//Esse
			//AudioFileIO.write(mp3File);
			//ou esse
			mp3File.commit();
			
						
		}
		
	}

}
