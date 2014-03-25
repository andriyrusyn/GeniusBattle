package test2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import backend.Analysis;
import backend.Analyzer;
import backend.Detector;
import backend.PLine;
import backend.RhymeCollection;
import backend.Scoring;
import backend.Stats;
import backend.Transcriptor;


//This is a class meant to be used to perform basic string manipulation methods that are used in Artist and Song. Also contains definitions for RapGenius url snippets

public class StringOps {
	public static final String RAP_GENIUS_URL = "http://rapgenius.com";
	public static final String RAP_GENIUS_ARTIST_URL = "http://rapgenius.com/artists/";
	public static final String RAP_GENIUS_SEARCH_URL = "http://rapgenius.com/search?q=";
	
	protected static int countWords(String s){ //from http://stackoverflow.com/a/5864184 
	    int wordCount = 0;
	    boolean word = false;
	    int endOfLine = s.length() - 1;

	    for (int i = 0; i < s.length(); i++) {
	        // if the char is a letter, word = true.
	        if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
	            word = true;
	            // if char isn't a letter and there have been letters before,
	            // counter goes up.
	        } else if (!Character.isLetter(s.charAt(i)) && word) {
	            wordCount++;
	            word = false;
	            // last word of String; if it doesn't end with a non letter, it
	            // wouldn't count without this.
	        } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
	            wordCount++;
	        }
	    }
	    return wordCount;
	}
	
	public static double round(double d, int decimalPlace){
		    BigDecimal bd = new BigDecimal(Double.toString(d));
		    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
		    return bd.doubleValue();
	}
	  
	protected static String explanationCleaner(String dirtyExplanation){ //ghetto way to fix the fact that my selectors aren't working perfectly for the annotations
		int firstPart = dirtyExplanation.indexOf("Genius") + 7;
		String cleanerExplanation = dirtyExplanation.substring(firstPart);
		int lastPart = cleanerExplanation.indexOf("To help improve");
		String cleanestExplanation = cleanerExplanation.substring(0,lastPart);
		return cleanestExplanation;
	}
	
	public static String getLyricsWithNewlines(Song song){ //returns a string containing all the lyrics from a song with newlines for display or to pass to the rhymeanalyzer
		String lyrics = "";
		for(int i = 0; i<song.getLyrics().size();i++){
			lyrics += song.getLyrics().get(i) + '\n';
		}
		return lyrics;
	}
	
	protected static String getExplanationsWithNewlines(Song song) throws IOException{ //doesn't work very well for some reason, should work just like getLyricsWithNewLines but doesn't...
		String explanations = "";
		for(int i = 0; i<song.explanations.size();i++){  
			explanations += song.explanations.get(i) + '\n';
		}
		return explanations;
	}
	
	public static double getGeniusBattleScore(Song song) throws IOException{ 
		double geniusBattleScore = (double) StringOps.countWords(StringOps.getExplanationsWithNewlines(song))/StringOps.countWords(StringOps.getLyricsWithNewlines(song));
		double roundedScore = StringOps.round(geniusBattleScore, 2);
		return roundedScore;
	}

	
}
