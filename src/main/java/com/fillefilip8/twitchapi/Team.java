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
	public String getName(){
		return name;
	}
	public String getInfo() throws IOException, ParseException{
		return (String)Utils.sendGETRequest(new URL("https://api.twitch.tv/kraken/teams/" + name)).get("info");
	}
	public String getDisplayName() throws IOException, ParseException{
		return (String)Utils.sendGETRequest(new URL("https://api.twitch.tv/kraken/teams/" + name)).get("display_name");
	}
	public URL getLogoURL() throws MalformedURLException, IOException, ParseException{
		return (URL)Utils.sendGETRequest(new URL("https://api.twitch.tv/kraken/teams/" + name)).get("logo");
	}
	public URL getBannerURL() throws MalformedURLException, IOException, ParseException{
		return (URL)Utils.sendGETRequest(new URL("https://api.twitch.tv/kraken/teams/" + name)).get("banner");
	}
	public URL getBackgroundURL() throws MalformedURLException, IOException, ParseException{
		return (URL)Utils.sendGETRequest(new URL("https://api.twitch.tv/kraken/teams/" + name)).get("background");
	}
	public URL getURL() throws MalformedURLException, IOException, ParseException{
		return new URL("http://www.twitch.tv/team/" + name);
	}
	
}
