package test2;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Song {
	protected String url;
	private Document songHTML;
	private String name;
	protected ArrayList<String> explanations;
	protected String lyrics;
	
	public Song(String name) { //TODO create URL automatically
		this.name = name;
	}
	
	public Song(String name, String url){ //same as with Artist, constructor doesn't include populateSongLyrics or populateExplanations in the interests of speed, those have to called separately 
		this.name = name;
		this.url = url;
		try {
			System.out.println("Song Link: " + url);
			this.songHTML = Jsoup
					.connect(url)
					.timeout(10000)
					.get();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void populateLyrics(){ //TODO update this so it works better
		this.lyrics = getLyrics();
	}
	
	public void populateExplanations() throws IOException{
		this.explanations = getExplanations();
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getLink(){
		return this.url;
	}

	public String getMainArtist(){
			String artist = songHTML.select(".song_header > .song_title > a").text();
			return artist;
	}
	
	public ArrayList<String> getFeaturedArtists(){ 
		ArrayList<String> featuredArtists = new ArrayList<String>();
		for (Element element : songHTML.select(".role_artists > .featured_artists > a")){
			featuredArtists.add(element.text());
		}
		return featuredArtists;
	}
	
	public String getAlbumName(){
		String albumDirty = songHTML.select(".other_songs_from_album > .label").text(); //selects the title of the album the song is from
		String albumClean = albumDirty.substring(5,albumDirty.indexOf(" lyrics")); //removes extra words
		return albumClean; 
	}
	
	public String getDescription(){ 
		String description = "";
		Elements descriptors = songHTML.select("div.description_body p");
		StringBuilder sb = new StringBuilder();
		for (Element i : descriptors) {
			sb.append(i.text() + "\n");
		}
		description = sb.toString();
		return description;
	}
	
//	protected ArrayList<String> getLyrics(){    //MY OLDER VERSION THAT DOESN'T WORK AS WELL as /mdreid's which I use below
//		//TODO currently only grabs lyrics that have an annotation, fix that
//		ArrayList<String> songLyrics= new ArrayList<String>();
//		for (Element element : songHTML.select(".lyrics > p > a")){ //.lyrics > p  grabs all lyrics (including unannotated) all in one element... if you add > a you get them back as separate elements but lose the linebreaks
//			songLyrics.add(element.text());
//		}
//		return songLyrics;
//	}
	
	protected String getLyrics(){ //alternative Lyrics grabber using mdreid's code
		Page pg = new Page(this.url);
		return pg.getLyrics();
	}
	
	protected String getCleanTitle(boolean withArtistName){
		String titleDirty = songHTML.select(".song_header > .song_title").text();
		String cleanTitle = StringOps.titleCleaner(titleDirty, withArtistName);
		return cleanTitle;
	}
	protected ArrayList<String> getExplanations() throws IOException{ //seems to print them several times over, this needs a good bit of debugging so left the System.outs
			ArrayList<String> songExplanations= new ArrayList<String>();
			for (Element element : songHTML.select(".lyrics > p  > a[href]")){
				String lyricURL = StringOps.RAP_GENIUS_URL + element.attr("href"); //grabs the link for each ind. lyric's "page" which contains the explanation 
				Document explanationHTML = Jsoup
						.connect(lyricURL)
						.timeout(10000)
						.get();
				String explanation = explanationHTML.select("div > p, blockquote").text();  //in jquery in my browser .body_text works and is simpler (also requires no cleaning)
				String cleanExplanation = StringOps.explanationCleaner(explanation);        //but in here and at try.jsoup.org I can't get any class selectors to work for some reason...
				songExplanations.add(cleanExplanation);
			}
			return songExplanations;
		} 
	
}
