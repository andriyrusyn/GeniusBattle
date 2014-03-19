package test2;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception{		
		
		
		java.util.Scanner s = new Scanner(System.in);
		do{
		System.out.println("Enter the artist name: ");
		String artistName = s.nextLine().replace(' ', '-');
		
		
		Artist thisArtist = new Artist(artistName);
//		thisArtist.printImage();
		
		
		
		System.out.println("Enter the song name: ");
		String songNameNoSpaces = s.nextLine().replace(' ','+');

		Song searchedSong = thisArtist.addSongByName(songNameNoSpaces);
		System.out.println(searchedSong.getName());
		searchedSong.populateExplanations();
		searchedSong.populateLyrics();
		String lyrWithNewLines = StringOps.getLyricsWithNewlines(searchedSong);
		
		
		
		String expsWithNewLines = StringOps.getExplanationsWithNewlines(searchedSong);
//		System.out.println(lyrWithNewLines);
		


	
		  
//		Song thisSong = new Song(RAP_GENIUS_URL + artistName + "-" + songName + "-lyrics"); 

		//System.out.println(thisSong.getTitle());
		
//		Song thisSong = thisArtist.getPopularSong(0);
		StringOps.analyzeRhymes(lyrWithNewLines);
		
		
		String verse = "";
		String explanations = "";
		for(int i = 0; i<searchedSong.lyrics.size(); i++){ //prints explanations for the song
			verse += searchedSong.lyrics.get(i);
			explanations += searchedSong.explanations.get(i);
//			System.out.println(searchedSong.lyrics.get(i));
		}
//	System.out.println("There are " + StringOps.countWords(verse) + " words in the lyrics for " + searchedSong.getName());	
//	System.out.println("There are " + StringOps.countWords(explanations) + " words in the explanations for " + searchedSong.getName());
	double geniusBattleScore = (double) StringOps.countWords(explanations)/StringOps.countWords(verse);
	double roundedScore = StringOps.round(geniusBattleScore, 2);
	System.out.println("GeniusBattleScore: " + roundedScore);
	
//	System.out.println("and here is the verse for you : " + '\n' + verse);
		
	
	} while(!s.nextLine().equals("killme"));
	

		
		
//		String url = RAP_GENIUS_ARTIST_URL + artistName;


		
	}
}