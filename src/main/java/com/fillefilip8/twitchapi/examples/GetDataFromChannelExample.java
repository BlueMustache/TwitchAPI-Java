package com.fillefilip8.twitchapi.examples;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.simple.parser.ParseException;

import com.fillefilip8.twitchapi.Channel;
import com.fillefilip8.twitchapi.TwitchAPI;

public class GetDataFromChannelExample {
	public static void main(String[] args) throws IOException, ParseException,
			InterruptedException, URISyntaxException {
		// Create the TwitchAPI object (useful for debugging and accessing
		// useful methods.)
		TwitchAPI api = new TwitchAPI();
		// Enable debug output
		api.setDebug(true);
		// Create the channel object that requires a channel name as a parameter
		Channel channel = new Channel("Monstercat");
		// Use the channel object to get data from that channel
		// If you wanna know what all these methods does please use hover the
		// methods to get a descrition (eclipse)
		System.out.println(channel.getFollowerCount());
		System.out.println(channel.getStatus());
		System.out.println(channel.getViews());
		System.out.println(channel.getLanguage());
		System.out.println(channel.getURL());
		System.out.println(channel.getProfileImageURL());
	}

}
