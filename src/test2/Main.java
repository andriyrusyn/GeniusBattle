package test2;

import java.util.ArrayList;
import java.util.Scanner;

import backend.Analysis;
import backend.Analyzer;

public class Main {
	public static void main(String[] args) throws Exception{		
		
		
		java.util.Scanner s = new Scanner(System.in);
		
		
		System.out.println("Enter the artist name: ");
		String artistName = s.nextLine().replace(' ', '-');
		Artist thisArtist = new Artist(artistName);	
		thisArtist.populateAlbums();
		
		System.out.println("Enter the album name: ");
		String albumName = s.nextLine().replace(' ', '-');
		Album thisAlbum = thisArtist.getAlbum(albumName);
		thisAlbum.populateSongList();
		
		for(Song song : thisAlbum.getSongs()){
			System.out.println(song.getName());
		}
		do{
		

		
//		String verse = "";
//		String explanations = "";
//		
//		for(int i = 0; i<searchedSong.lyrics.size(); i++){ //fills up explanations and lyrics for the song
//			verse += searchedSong.lyrics.get(i);
//			explanations += searchedSong.explanations.get(i);
//		}

		
		
		} while(!s.nextLine().equals("killme"));

		
	}
}



/* put this in the do{ block and type each song name manually after you run it to populate SQL
System.out.println("Enter the song name: ");
String songNameNoSpaces = s.nextLine().replace(' ','+');
Song searchedSong = thisArtist.addSongByName(songNameNoSpaces);
searchedSong.populateExplanations();
searchedSong.populateLyrics();
String lyrWithNewLines = StringOps.getLyricsWithNewlines(searchedSong);


Analysis an = Analyzer.createAnalysis(lyrWithNewLines);              //recreates song data printing with the analyzer object
for(int i = 0; i<an.numFeatures(); i++){
	System.out.println("i is: " + i +"   " + an.getStatName(i) + " " + an.getStat(i));
}
System.out.println("searchedSong.getAlbumName() is: " + searchedSong.getAlbumName());
MySQL.insertSongInfo(thisArtist, searchedSong, an);
*/