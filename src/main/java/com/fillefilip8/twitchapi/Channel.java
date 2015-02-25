package com.fillefilip8.twitchapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Channel {
private String channelName;
private static long followers;
private static int views;
	public Channel(String channelName){
		this.channelName = channelName;
	}
	
	public long getFollowerCount() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken/channels/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'FollowerCount' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		Channel.followers = (long) json.get("followers");
			
		
		in.close();
		
		return (long) json.get("followers");
		
	}
	public String getChannelName(){
		return channelName;
		
	}

}
