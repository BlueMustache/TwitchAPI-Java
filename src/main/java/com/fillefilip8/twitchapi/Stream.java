package com.fillefilip8.twitchapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Stream {
	private long viewers;
	private String game = "";
	private String channelName;
	public Stream(String channelName) {
		//this.game = game;
		//this.viewers = viewers;
		this.channelName = channelName;
	}

	/**
	 * Get the channel of the stream
	 * 
	 * @return
	 */
	public Channel getChannel() {
		return new Channel(channelName);
	}

	public long getViewers() throws IOException, ParseException {
		URL url = new URL("https://api.twitch.tv/kraken/streams/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("\nSending 'Viewers' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		in.close();
		JSONObject stream = (JSONObject) json.get("stream");
		return (long)stream.get("viewers");
	}
	public String getGame() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken/streams/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("\nSending 'Game' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		in.close();
		JSONObject stream = (JSONObject) json.get("stream");
		return (String)stream.get("game");
		
	}
	/**
	 * Get the title/status of the stream
	 * 
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public String getStatus() throws IOException, ParseException {
		return getChannel().getStatus();
	}
	public long getStreamQuality() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken/streams/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("\nSending 'Stream' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		in.close();
		JSONObject stream = (JSONObject) json.get("stream");
		return (long)stream.get("video_height");
	}

}
