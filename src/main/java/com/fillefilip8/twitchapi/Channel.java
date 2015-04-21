package com.fillefilip8.twitchapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Channel {
private String channelName;
	public Channel(String channelName){
		this.channelName = channelName;
	}
	public Stream getStream() throws IOException, ParseException{
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
		System.out.println(stream.get("viewers"));
		return new Stream(getChannelName());
	}
	public boolean isStreaming() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken/streams/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("\nSending 'IsStreaming' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		in.close();
		if(json.get("stream") !=null){
			return true;
			
		}else{
			return false;
		}
	}
	public long getFollowerCount() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken/channels/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("\nSending 'FollowerCount' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);
		}
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
	public String getStatus() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken/channels/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("\nSending 'Status' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		in.close();
		return (String)json.get("status");
	}
	@Override
		public String toString() {
			String result = null;
			try {
				result = "ChannelName: " + getChannelName() + "|Streaming: " + isStreaming() + "|" + "Status: " + getStatus() + "|" + "Followers: " + getFollowerCount();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
	public String getLanguage() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken/channels/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("\nSending 'Language' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		in.close();
		return (String)json.get("broadcaster_language");
	}
	public URL getURL() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken/channels/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("\nSending 'URL' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		in.close();
		
		return new URL((String)json.get("url"));
	}
	public boolean isPartner() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken/channels/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("\nSending 'isPartner' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		in.close();
		
		return (boolean)json.get("partner");
	}
	public long getViews() throws IOException, ParseException{
		URL url = new URL("https://api.twitch.tv/kraken/channels/" + channelName);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("\nSending 'Views' request for " + channelName + " with URL: " + url);
		System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		in.close();
		
		return (long)json.get("views");
	}

}
