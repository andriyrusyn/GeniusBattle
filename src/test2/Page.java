package test2;

import java.io.*;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Page {
	
	private final String artist;
	private final String title;
	private final String url;
	private final String description;
	private final String lyrics;
		
	public Page(String sURL) {
		Document doc = null;
		try {
			doc = Jsoup.connect(sURL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// set url
		url = sURL;
		
		// get title and artist
		Element t = doc.getElementsByTag("title").first();
		System.out.println(t.text());
		String regex = "(\\S.*)\\s–\\s(\\S.*)\\sLyrics";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(t.text());
		matcher.find();
		assert(matcher.groupCount()==2);
		artist = matcher.group(1);
		title = matcher.group(2);
		
		// get description
		String descriptionSelector = "div.description_body p";
		Elements descriptors = doc.select(descriptionSelector);
		StringBuilder sb = new StringBuilder();
		for (Element i : descriptors) {
			sb.append(i.text() + "\n");
		}
		description = sb.toString();
		
		// get lyrics
		String lyricSelector = "div.lyrics p";
		Element e = doc.select(lyricSelector).first();
	
		String s1 = e.toString();
		String regex1 = "<br.{0,2}/>";
		String s2 = s1.replaceAll(regex1, "\n");
		
		String regex2 = "\\[.*?\\]";
		Pattern p2 = Pattern.compile(regex2);
		Matcher matcher2 = p2.matcher(s2);
		String s3 = matcher2.replaceAll("");

		String regex3 = "<.+?>";
		Pattern p3 = Pattern.compile(regex3);
		Matcher matcher3 = p3.matcher(s3);
		String s4 = matcher3.replaceAll("");
		lyrics = s4.replaceAll("^\\s+|\\s+$|\\s*(\n)\\s*|(\\s)\\s*", "$1$2").replace("\t"," ");
		
	}
	
	public Page(URL url) {
		this(url.toString());
	}
	
	
	public String getURL() { return url; }
	
	public String getArtist() { return artist; }
	
	public String getTitle() { return title; }
	
	public String getDescription() { return description; }
	
	public String getLyrics() { return lyrics; }
	
		public static void main(String[] args) {
			Page pg = new Page("http://rapgenius.com/Nas-rewind-lyrics");
			System.out.println("URL: " + pg.getURL());
			System.out.println("Artist: " + pg.getArtist());
			System.out.println("Title: " + pg.getTitle());
			System.out.println("Description: " + pg.getDescription());
			System.out.println("Lyrics: " + pg.getLyrics());
			
		}
	
}

