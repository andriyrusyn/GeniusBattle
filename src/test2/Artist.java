package test2;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Artist {
	private String name;
	private Document artistHTML;
	private String url;
	private ArrayList<Song> popularSongs = new ArrayList<Song>();
	private ArrayList<Song> songs = new ArrayList<Song>();

	
	public Artist (String name){
		this.name = name.replace('-', ' ');
		this.url = StringOps.RAP_GENIUS_ARTIST_URL + name;
		try {		
			this.artistHTML = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		this.songs = getSongList();                     //including these in the constructor seems to slow things down quite a bit, might just be my internet though...
//		this.popularSongs = getPopularSongList();       //for now you just have to call Artist.populateSongs() or .populatePopularSongs() if you want to access them
	}
	
	
	public void addPopularSong (Song rapSong){
		this.popularSongs.add(rapSong);
	}
	
	public Song addSongByName (String songNameNoSpaces){
		String url = StringOps.RAP_GENIUS_SEARCH_URL + songNameNoSpaces; 
		//below we load the search page HTML from rapgenius and loop through the first 15 results, only returning the Song object if it's the correct song
		try {		 
			Document searchHTML = Jsoup.connect(url).get();
			Elements results = searchHTML.select("ul.search_results > li.search_result"); //gets each search result from the list
			for (int i = 0; i<15; i++){
				String songTitle = results.get(i).text();  
				String songLink = StringOps.RAP_GENIUS_URL + results.get(i).select("li > a").attr("href"); //gets each songs link
				Song thisSong = new Song(songTitle, songLink); //creates a new song Object TODO don't create a new object each time to save memory and increase performance??
				String firstPartOfSongName = thisSong.getName().substring(0, thisSong.getMainArtist().length()); //used to check the artist name for the track agains this.name
				String endOfSongName = thisSong.getName().substring(thisSong.getName().length() - songNameNoSpaces.length()); //used to check song name for the track against songNameNoSpaces
				if(firstPartOfSongName.equalsIgnoreCase(this.name) && endOfSongName.equalsIgnoreCase(songNameNoSpaces.replace('+', ' '))){
					return thisSong;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ArrayList<Song> getSongList(){  //gets the songs listed on the first page TODO figure out pagination with JSOUP to get more than the first 20 songs
		ArrayList<Song> songList = new ArrayList<Song>();
		for (Element element : artistHTML.select("#main .song_list")){ //alternatively: .song_list:eq(1) .song_name
			Song thisSong = new Song(element.text());
			songs.add(thisSong);
		}
		return songList;
	}

	public Song getPopularSong(int index){ 
		return popularSongs.get(index);
	}
	
	public ArrayList<Song> getPopularSongList() {  //one of the more useful methods, returns an arraylist of the artists popular songs - the top 6 or 7 according to rapgenius
		ArrayList<Song> popularSongList = new ArrayList<Song>();
		try {
			Document testHTML = Jsoup.connect(url).get();
			Elements artistSongNames = testHTML.select(".song_list:eq(1) .song_name"); //grabs the popular songs only			
			for(int i = 0;i<artistSongNames.size();i++){
				Song thisSong = new Song(artistSongNames.get(i).text(), StringOps.RAP_GENIUS_URL + artistSongNames.get(i).attr("href"));
				popularSongList.add(thisSong);
			}
			return popularSongList;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void printImage(){ //TODO figure out how to save the image to a folder, snippet below is from the JSOUP docs
	       //Open a URL Stream
//        Response resultImageResponse = Jsoup.connect(imageLocation).cookies(cookies).ignoreContentType(true).execute();
//
//        // output here
//        FileOutputStream out = (new FileOutputStream(new java.io.File(outputFolder + name)));
//        out.write(resultImageResponse.bodyAsBytes());           // resultImageResponse.body() is where the image's contents are.
//        out.close();
	}
}
