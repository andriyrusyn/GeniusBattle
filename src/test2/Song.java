package test2;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Song {
	protected String url;
	private Document songHTML;
	private String name;
	protected ArrayList<String> explanations;
	protected ArrayList<String> lyrics;
	
	public Song(String name) {
		this.name = name;
	}
	
	public Song(String name, String url){ //same as with Artist, constructor doesn't include populateSongLyrics or populateExplanations in the interests of speed, those have to called separately 
		this.name = name;
		this.url = url;
		try {
			this.songHTML = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void populateLyrics(){
		this.lyrics = getLyrics();
	}
	
	public void populateExplanations() throws IOException{
		this.explanations = getExplanations();
	}
	
	public String getName(){
		return this.name;
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
	
	public String getCleanTitle(Boolean withArtistName){ //a way of cleaning up the Song's title to remove anything like (ft. XXXXX) or prod. by XXXX which get returned from Rapgenius
		String titleDirty = songHTML.select(".song_header > .song_title").text();
		String titleClean = titleDirty.substring(0, titleDirty.length() - " Lyrics".length());
		if(titleClean.indexOf("prod.") != -1 ){ //if "prod." exists, delete it and anything after it
			titleClean = titleClean.substring(0, titleClean.indexOf("prod."));
		}
		if(titleClean.indexOf('(') != -1){ //if there is an open parantheses, delete it and anything after it
			titleClean = titleClean.substring(0, titleClean.indexOf('('));
		}
		if(withArtistName) return titleClean;
		if(withArtistName == false){
			int startOfSongTitle = titleClean.indexOf('–') + 2; //find where the "-" between artist and song name is and add 2 to remove the spaces after it
			titleClean = titleClean.substring(startOfSongTitle); //delete the artists name
			return titleClean;
		}
		else return null;
	}
	
	
	protected ArrayList<String> getLyrics(){
		//TODO currently only grabs lyrics that have an annotation, fix that
		ArrayList<String> songLyrics= new ArrayList<String>();
		for (Element element : songHTML.select(".lyrics > p > a")){ //.lyrics > p  grabs all lyrics (including unannotated) all in one element... if you add > a you get them back as separate elements but lose the linebreaks
			songLyrics.add(element.text());
		}
		return songLyrics;
	}
	

	protected ArrayList<String> getExplanations() throws IOException{ //seems to print them several times over, this needs a good bit of debugging so left the System.outs
			ArrayList<String> songExplanations= new ArrayList<String>();
			for (Element element : songHTML.select(".lyrics > p  > a[href]")){
				String lyricURL = StringOps.RAP_GENIUS_URL + element.attr("href"); //grabs the link for each ind. lyric's "page" which contains the explanation 
				Document explanationHTML = Jsoup.connect(lyricURL).get();
				String explanation = explanationHTML.select("div > p, blockquote").text();  //in jquery in my browser .body_text works and is simpler (also requires no cleaning)
				String cleanExplanation = StringOps.explanationCleaner(explanation);        //but in here and at try.jsoup.org I can't get any class selectors to work for some reason...
				songExplanations.add(cleanExplanation);
			}
			return songExplanations;
		} 
	
}
