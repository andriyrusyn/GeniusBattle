package test2;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception{		
		
		java.util.Scanner s = new Scanner(System.in);
		System.out.println("Enter the artist name: ");
		String artistName = s.nextLine().replace(' ', '-');
		
		
		Artist thisArtist = new Artist(artistName);
	
		System.out.println("Enter the song name: ");
		String songNameNoSpaces = s.nextLine().replace(' ','+');

		Song searchedSong = thisArtist.addSongByName(songNameNoSpaces);
		searchedSong.populateExplanations();
		searchedSong.populateLyrics();
		String lyrWithNewLines = StringOps.getLyricsWithNewlines(searchedSong);
//		String expsWithNewLines = StringOps.getExplanationsWithNewlines(searchedSong);
//		System.out.println(lyrWithNewLines);
		
		StringOps.analyzeRhymes(lyrWithNewLines);
		
		
//		System.out.println(StringOps.countWords("lyric word count " + lyrWithNewLines));
//		System.out.println(StringOps.countWords("explanations word count " + expsWithNewLines));


	
		  
//		Song thisSong = new Song(RAP_GENIUS_URL + artistName + "-" + songName + "-lyrics"); 

//		System.out.println(thisSong.getTitle());
		
//		Song thisSong = thisArtist.getPopularSong(0);
		
		for(int i = 0; i<searchedSong.explanations.size(); i++){ //prints explanations for the song
		System.out.println(searchedSong.explanations.get(i));
		}

		
//		String url = RAP_GENIUS_ARTIST_URL + artistName;

		
	}
}