package test2;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ImagePrinter {
	public static void printImage(String url, String name) throws IOException{
		//Connect to the website and get the html
        Document doc = Jsoup.connect(url).get();
        //Get first elements with img tag
        Element img = doc.getElementsByTag("img").first();
        String src = img.absUrl("src");

        System.out.println("Image Found!");
        System.out.println("src attribute is : "+src);
        
        getImages(src, name);

	}
	
	private static void getImages(String src, String name2) throws IOException {
		String folderPath = "C:\\Users\\arusyn\\Dropbox\\GeniusBattle";     
        //Open a URL Stream
        URL url = new URL(src);
        InputStream in = url.openStream();

        int indexname = src.lastIndexOf(".");

        String name = src.substring(indexname, src.length());
 

        OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath + "\\" + name2 + name));

        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();

    }
}
