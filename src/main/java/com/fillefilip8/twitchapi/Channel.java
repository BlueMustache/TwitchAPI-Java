package com.fillefilip8.twitchapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Channel {
	private String channelName;
	/**
	 * Create a channel object that you can use to get info about that channel
	 * @param channelName
	 */
	public Channel(String channelName){
		this.channelName = channelName;
	}
	public Stream getStream() throws IOException, ParseException{
		return new Stream(getChannelName());
	}
	/**
	 * Is the channel livestreaming right now?
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public boolean isStreaming() throws IOException, ParseException{
		URL url = new URL(TwitchAPI.baseURL + "streams/" + channelName);
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
	/**
	 * Get how many followers this channel got
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public long getFollowerCount() throws IOException, ParseException{
		return (long) Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "channels/" + channelName)).get("followers");	
	}
	/**
	 * Get the channel name of this channel object
	 * @return
	 */
	public String getChannelName(){
		return channelName;
	}
	/**
	 * Get the title/status of the stream
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getStatus() throws IOException, ParseException{
		return (String) Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "channels/" + channelName)).get("status");	
	}
	@Override
		public String toString() {
			String result = null;
			try {
				result = "ChannelName: " + getChannelName() + "|Streaming: " + isStreaming() + "|" + "Status: " + getStatus() + "|" + "Followers: " + getFollowerCount();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return result;
		}
	/**
	 * Get the language of the channel
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getLanguage() throws IOException, ParseException{
		return (String) Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "channels/" + channelName)).get("broadcaster_language");	
	}
	/**
	 * Get the URL of the channel
	 * Example: http://twitch.tv/testuser
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public URL getURL() throws IOException, ParseException{
		return new URL((String) Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "channels/" + channelName)).get("url"));
	}
	/**
	 * Check if this user is partnered with Twitch
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public boolean isPartner() throws IOException, ParseException{
		return (boolean) Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "channels/" + channelName)).get("partner");
	}
	/**
	 * Check if this user has turned on mature mode on thier channel
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public boolean isMature() throws IOException, ParseException{
		return (boolean) Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "channels/" + channelName)).get("mature");
	}
	/**
	 * Get the channel views of this channel
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public long getViews() throws IOException, ParseException{
		return (long) Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "channels/" + channelName)).get("views");
	}
	
	
	/**
	 * Get the profile image URL of the channel
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public URL getProfileImageURL() throws MalformedURLException, IOException, ParseException{
		return (URL)Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "channels/" + channelName)).get("logo");
	}
	/**
	 * Get the banner image URL of the channel
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	@Deprecated
	public URL getBannerURL() throws MalformedURLException, IOException, ParseException{
		return (URL)Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "channels/" + channelName)).get("banner");
	}
	/**
	 * Get the offline image URL of the channel
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public URL getOfflineImageURL() throws MalformedURLException, IOException, ParseException{
		return (URL)Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "channels/" + channelName)).get("video_banner");
	}
	public List<Object> getAllInfo() throws IOException, ParseException{
		List<Object> info = new ArrayList<Object>();
		info.add(getChannelName());
		info.add(getFollowerCount());
		info.add(getStatus());
		info.add(getViews());
		return info;
		
	}
}
