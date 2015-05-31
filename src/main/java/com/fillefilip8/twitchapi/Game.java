package com.fillefilip8.twitchapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Game {
	private String name;
	/**
	 * Create a game object that you can use to get info about that game
	 * @param name
	 */
	public Game(String name){
		this.name = name;
	}
	/**
	 * Get the name of the game
	 * @return
	 */
	public String getName(){
		return name;
	}
	/**
	 * Does this game exist on twitch?
	 * TODO: Fix this
	 * @return
	 * @throws IOException
	 */
	public boolean exists() throws IOException{
		URL url = new URL("http://static-cdn.jtvnw.net/ttv-boxart/" + Utils.convertStringToURL(name) + "-272x380.jpg");
	    HttpURLConnection huc =  (HttpURLConnection)  url.openConnection(); 
	    huc.setRequestMethod("GET");
	    huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
	    huc.connect(); 
	    System.out.println(huc.getResponseCode());
	    System.out.println(huc.getResponseMessage());
	    System.out.println("Redirect: " + huc.getHeaderField("Location"));
	    System.out.println(huc.getHeaderFields());
	    if(huc.getResponseCode() == 404){
	    	return false;
	    }else{
	    	return true;
	    }
	    
	}
	/**
	 * Get the URL of the BoxArt image
	 * @return URL of BoxArt image
	 * @throws MalformedURLException
	 */
	public URL getBoxArt() throws MalformedURLException{
		return new URL("http://static-cdn.jtvnw.net/ttv-boxart/" + Utils.convertStringToURL(name) + "-272x380.jpg");		
	}
}
