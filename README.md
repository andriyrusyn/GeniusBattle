GeniusBattle
============

simple Java API for querying RapGenius.com, also includes rhyme analysis through the RhymeAnalyzer project, this is all backend stuff for a webapp I'm working on

This will be used to populate a database, but for now can output lots of interesting stats about rap lyrics to the console.

Started with the idea that # of words in explanation/# words in a lyric (on rapgenius.com) would be a useful way of measuring how complex a rapper's lyrics are, ended up adding a lot more analysis after finding the RhymeAnalzer project made by Hussain Hirjee and Daniel G. Brown at the University of Waterloo (http://ismir2010.ismir.net/proceedings/late-breaking-demo-23.pdf)

Also this whole thing is very much inspired by kenshiro's rapgenius-js API (https://github.com/kenshiro-o/RapGenius-JS/), in fact this project started as a way to understand how exactly his API works.


USAGE
============
--ARTIST:
Artist nas = new Artist("nas") //creates an artist for nas and loads the HTML from his page on RG
nas.populatePopularSongs(); and nas.populateSongs //populates the songLists so they can be used later (chose not to include in the constructor because including these slowed things down waay too much)
nas.addSongByName("Ether") //finds a specific song for the artist, adds it to the songs ArrayList, and returns it

--SONG:
once you have an artist, create a song using Song s = Artist.addSongByName if you're looking for a specific song or use the constructors
as with Artist, populateLyrics() and populateExplanations() aren't included in the constructors so they need to be called if you want this info.
Song.getCleanTitle(true) //returns the songs title as a String in the format Artist - Song
Song.getLyrics() //returns an ArrayList containing the lyrics
Song.getExplanations() //same as with getLyrics

--STRINGOPS:
use these if you want to clean things up, count words, or transform ArrayList<String> into a String for use with the RhymeAnalyzer, or analyze some rhymes (will later refactor into several separate classes)
String lyrWithNewLines = StringOps.getLyricsWithNewlines(Song); //returns a verse that can be used in the RhymeAnalyzer
StringOps.analyzeRhymes(lyrWithNewLines); //analyzes a verse and prints the analysis to console


There are a lot more things that need to be done but progress has been made






