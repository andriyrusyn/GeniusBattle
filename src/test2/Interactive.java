package test2;

import java.io.IOException;
import java.util.Scanner;

import backend.Analysis;
import backend.Analyzer;

public class Interactive {

	public static void artistThenAlbum() throws Exception{
		
		java.util.Scanner s = new Scanner(System.in);
		
		Artist thisArtist = inputCreateArtist();
		thisArtist.populateAlbums();
		ImagePrinter.printImage(thisArtist.getURL(), thisArtist.getName());
		
		Album thisAlbum = inputCreateAlbum(thisArtist);
		thisAlbum.populateSongList();
		ImagePrinter.printImage(thisAlbum.getURL(), thisAlbum.getName());
		
		for(Song song : thisAlbum.getSongs()){
			song.populateExplanations();
			song.populateLyrics();
			String lyrics = song.getLyrics();
			Analysis an = Analyzer.createAnalysis(lyrics);              //recreates song data printing with the analyzer object
			if( an != null ){
				for(int i = 0; i<an.numFeatures(); i++){
					System.out.println("stat # is: " + i +" and value is: " + an.getStatName(i) + " " + an.getStat(i));
				}
			}
			MySQL.insertSongInfo(thisArtist, song, an);
		}
	}
	
	public static void artistThenSong() throws Exception{
		java.util.Scanner s = new Scanner(System.in);
		
		Artist thisArtist = inputCreateArtist();
		thisArtist.populateAlbums();
		ImagePrinter.printImage(thisArtist.getURL(), thisArtist.getName());
		
		
		do{
		System.out.println("Enter the song name: ");
		String songNameNoSpaces = s.nextLine().replace(' ','+');
		Song searchedSong = thisArtist.getSongByName(songNameNoSpaces);
		searchedSong.populateExplanations();
		searchedSong.populateLyrics();
		String lyrics = searchedSong.getLyrics();


		Analysis an = Analyzer.createAnalysis(lyrics);              //recreates song data printing with the analyzer object
		for(int i = 0; i<an.numFeatures(); i++){
			System.out.println("i is: " + i +"   " + an.getStatName(i) + " " + an.getStat(i));
		}
		System.out.println("searchedSong.getAlbumName() is: " + searchedSong.getAlbumName());
		MySQL.insertSongInfo(thisArtist, searchedSong, an);
		} while(!s.nextLine().equals("killme"));

	}
	
	
	public static void printLyrics() throws Exception{
		java.util.Scanner s = new Scanner(System.in);
		Artist thisArtist = inputCreateArtist();
		do{
		System.out.println("Enter the song name: ");
		String songNameNoSpaces = s.nextLine().replace(' ','+');
		Song searchedSong = thisArtist.getSongByName(songNameNoSpaces);
		searchedSong.populateExplanations();
		searchedSong.populateLyrics();
		String lyrics = searchedSong.getLyrics();
		System.out.println(lyrics);
		} while(!s.nextLine().equals("killme"));
	}
	
	private static Artist inputCreateArtist(){
		java.util.Scanner s = new Scanner(System.in);
		System.out.println("Enter the artist name: ");
		String artistName = s.nextLine().replace(' ', '-');
		Artist thisArtist = new Artist(artistName);	
		return thisArtist;		
	}
	
	private static Album inputCreateAlbum(Artist a){
		java.util.Scanner s = new Scanner(System.in);
		System.out.println("Enter the album name: ");
		String albumName = s.nextLine();
		Album thisAlbum = a.getAlbum(albumName);
		return thisAlbum;
	}
}
