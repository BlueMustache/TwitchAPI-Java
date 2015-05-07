package com.fillefilip8.twitchapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Game {
	private String name;
	/**
	 * 
	 * @param name
	 */
	public Game(String name){
		this.name = name;
	}
	/**
	 * TODO: Fix this
	 * @return
	 * @throws IOException
	 */
	public boolean exists() throws IOException{
		URL url = new URL("http://static-cdn.jtvnw.net/ttv-boxart/" + convertStringToURL() + "-272x380.jpg");
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
	 * @return URL of BoxArt
	 * @throws MalformedURLException
	 */
	public URL getBoxArt() throws MalformedURLException{
		return new URL("http://static-cdn.jtvnw.net/ttv-boxart/" + convertStringToURL() + "-272x380.jpg");		
	}
	public String convertStringToURL(){
		return name.replaceAll(" ", "%20");
	}
}
