package test2;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Artist {
	private String name;
	private Document artistHTML;
	private String url;
	private String album;
	private String description;
	private ArrayList<Song> popularSongs = new ArrayList<Song>();
	private ArrayList<Song> songs = new ArrayList<Song>();
	private ArrayList<Album> albums = new ArrayList<Album>();

	public Artist (String name, String url){
		this.name = name.replace('-', ' ');
		this.url = url;
		try {		
			this.artistHTML = Jsoup
					.connect(url)
					.timeout(10000)
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.description = populateDescription();
//		this.songs = getSongList();                     //including these in the constructor seems to slow things down quite a bit, might just be my internet though...
//		this.popularSongs = getPopularSongList();       //for now you just have to call Artist.populateSongs() or .populatePopularSongs() if you want to access them
//	    this.albums = getAlbumList();
	}	
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
	
	public void populateSongs() { this.songs = getSongList(); }	
	public void populatePopularSongs() { this.popularSongs = getPopularSongList(); }
	public void populateAlbums () { this.albums = getAlbumList(); }
	public String populateDescription(){ Page pg = new Page(this.url);	return pg.getDescription();	}
	
	public String getName(){ return this.name; }
	public String getURL(){ return this.url; }
	
	public void addPopularSong (Song rapSong){ this.popularSongs.add(rapSong); }

	public Song getSongByName (String songNameNoSpaces){
		String url = StringOps.RAP_GENIUS_SEARCH_URL + songNameNoSpaces; 
		//below we load the search page HTML from rapgenius and loop through the first 15 results, only returning the Song object if it's the correct song
		try {		 
			Document searchHTML = Jsoup.connect(url).get();
			Elements results = searchHTML.select("ul.search_results > li.search_result"); //gets each search result from the list
			for (int i = 0; i<15; i++){
				String songTitle = results.get(i).text();  
				System.out.println("songTitle is: " + songTitle);
				String songLink = results.get(i).select("li > a").attr("href"); //gets each songs link
				Song thisSong = new Song(songTitle, songLink); //creates a new song Object TODO don't create a new object each time to save memory and increase performance??
				String firstPartOfSongName = thisSong.getName().substring(0, thisSong.getMainArtist().length()); //used to check the artist name for the track against this.name
				String endOfSongLink = thisSong.getLink().substring((thisSong.getLink().length()-7-songNameNoSpaces.length()), (thisSong.getLink().length()-7)); //used to check song name for the track against songNameNoSpaces
				if(firstPartOfSongName.equalsIgnoreCase(this.name) && endOfSongLink.equalsIgnoreCase(songNameNoSpaces.replace('+', '-'))){
					songs.add(thisSong);
					return thisSong;
				}
			}
		} catch (IOException e) { e.printStackTrace(); }
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
	
	public Song getSong(int index){ return songs.get(index); }
	
	
	public ArrayList<Album> getAlbumList(){ //grabs all the album names 
		ArrayList<Album> albumList = new ArrayList<Album>();
		for (Element element : artistHTML.select(".album_list a")){ 
			Album thisAlbum = new Album(element.text(), element.attr("href"));
			albumList.add(thisAlbum);
			System.out.println(element.text());
		}
		return albumList;
	}
	
	public Album getAlbum(int index){ return albums.get(index); }
	
	public Album getAlbum(String albumNameNoSpaces){
		for(Album a : albums){
			if(a.getName().equalsIgnoreCase(albumNameNoSpaces)){
				return a;
			}
		}
		return null;
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
	
	public Song getPopularSong(int index){ return popularSongs.get(index); }
	
	
}
