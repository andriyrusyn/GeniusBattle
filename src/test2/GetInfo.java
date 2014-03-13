package test2;
/* Scrapes song info using JSoup */
import java.io.IOException;
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetInfo {
	
	private static int songNum = 0;
	public static void scrape(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String songSelector = "span.chart_position ~ h1";
		String infoSelector = "span.chart_position ~ p.chart_info";

		Elements songs 	= doc.select(songSelector);
		Elements info 	= doc.select(infoSelector);
		
		assert(songs.size() == info.size());
		
		/* write to file */
		PrintWriter pw = null;
		try {
		    pw = new PrintWriter(new BufferedWriter(new FileWriter("songNames.txt", true)));
			System.out.println("Got here TROLL WHAT");
			for (int i = 0; i < songs.size(); i++) {
				Element s = songs.get(i);
				Element in = info.get(i);
				pw.println(songNum++ + " " + s.text() + ": " + in.text());
			}
		} catch (IOException e) {
		    System.err.println(e);
		} finally{
		    pw.close();
		} 
		
	}
	
	public static void main(String[] args) {
		int numPages = 9;
		String url = "http://rapgenius.com/artists/Eminem";
		scrape(url);
		for (int i = 1; i <= numPages; i++) {
			String temp = url + "?page=" + Integer.toString(i);
			scrape(temp);
		}
	}

}
