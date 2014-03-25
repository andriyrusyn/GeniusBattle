package test2;

import java.util.Scanner;

import backend.Analysis;
import backend.Analyzer;

public class Main {
	public static void main(String[] args) throws Exception{		
		
		
		java.util.Scanner s = new Scanner(System.in);
		
		do{
		System.out.println("Enter the artist name: ");
		String artistName = s.nextLine().replace(' ', '-');
		Artist thisArtist = new Artist(artistName);	
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
		
		MySQL.insertSongScores(searchedSong, an);

		
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