package test2;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Album {

	private String name;
	private String url;
	private ArrayList<Song> songs = new ArrayList<Song>();
	private Document albumHTML;
	
	public Album (String name, String url) {
		this.name = name;
		this.url = StringOps.RAP_GENIUS_URL + url;
		try {		
			this.albumHTML = Jsoup
					.connect(this.url)
					.timeout(10000)
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName(){return this.name;}
	public String geturl(){return this.url;}
	public ArrayList<Song> getSongs(){return songs;}
	
	public void populateSongList(){
		Elements songResults = albumHTML.select(".song_list a");
		for (Element a : songResults){
			System.out.println("name is "+ a.text());
			System.out.println("link is "+ a.attr("href"));
			Song thisSong = new Song(a.text(), (StringOps.RAP_GENIUS_URL + a.attr("href")));
			songs.add(thisSong);
		}
	}
	
	

}
