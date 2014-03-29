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
	private int year;
	
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
		this.year = getYear();
	}
	
	public String getName(){return this.name;}
	public String getURL(){return this.url;}
	public ArrayList<Song> getSongs(){return songs;}
	
	private int getYear(){
		Elements e = albumHTML.select(".name");
		String name = e.text();
		if(name.indexOf('(') != -1){
			String nameIfYearExists = name.substring(name.indexOf('(')+1, name.indexOf('(')+5);
			try{
				int yr = java.lang.Integer.parseInt(nameIfYearExists);
				System.out.println("Success! year is: " + yr + "  for the album: " + name);
				return yr;
			} catch (NumberFormatException n) {
				System.out.println("couldn't find a year for the album: " + name);
			}
		}
		return 0000;
		
	}
	
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
