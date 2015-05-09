package com.fillefilip8.twitchapi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.parser.ParseException;

public class Team {
//https://api.twitch.tv/kraken/teams
	private String name;
	public Team(String name){
		this.name = name;
	}
	/**
	 * Get the name of the team
	 * @return
	 */
	public String getName(){
		return name;
	}
	/**
	 * Get the description of the team
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getInfo() throws IOException, ParseException{
		return (String)Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "teams/" + name)).get("info");
	}
	/**
	 * Get the display name of the team
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getDisplayName() throws IOException, ParseException{
		return (String)Utils.sendGETRequest(new URL(TwitchAPI.baseURL +  "teams/" + name)).get("display_name");
	}
	/**
	 * Get the logo image URL of the team
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public URL getLogoURL() throws MalformedURLException, IOException, ParseException{
		return (URL)Utils.sendGETRequest(new URL(TwitchAPI.baseURL + "teams/" + name)).get("logo");
	}
	/**
	 * Get the banner image URL of the team
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public URL getBannerURL() throws MalformedURLException, IOException, ParseException{
		return (URL)Utils.sendGETRequest(new URL("https://api.twitch.tv/kraken/teams/" + name)).get("banner");
	}
	/**
	 * Get the background image URl of the team
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public URL getBackgroundURL() throws MalformedURLException, IOException, ParseException{
		return (URL)Utils.sendGETRequest(new URL("https://api.twitch.tv/kraken/teams/" + name)).get("background");
	}
	/**
	 * Get the URL of the team
	 * Example: https://twitch.tv/teams/testteam
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public URL getURL() throws MalformedURLException, IOException, ParseException{
		return new URL("http://www.twitch.tv/team/" + name);
	}
	
}
