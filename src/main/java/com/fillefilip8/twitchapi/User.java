package com.fillefilip8.twitchapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fillefilip8.twitchapi.Channel;
public class User {
private String access_token;
	public User(String access_token){
		this.access_token = access_token;
	}
	
	public Channel getChannel() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken?oauth_token=" + access_token);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		StringBuffer response = new StringBuffer();
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		

		
			
		
		in.close();

		//print result
		// get an array from the JSON object
//		JSONArray streams= (JSONArray) json.get("streams");
		JSONObject token = (JSONObject) json.get("token");

	    JSONObject channel;
	    JSONObject name;
		//JSONArray streamsChannel = (JSONArray) ;
	   // System.out.println("Username: " + token.get("user_name"));
		return new Channel(token.get("user_name").toString());
		
	}
}
