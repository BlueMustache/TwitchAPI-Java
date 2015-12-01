package com.fillefilip8.twitchapi.examples;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.simple.parser.ParseException;

import com.fillefilip8.twitchapi.Channel;
import com.fillefilip8.twitchapi.Stream;
import com.fillefilip8.twitchapi.TwitchAPI;

public class GetDataFromStreamExample {
	public static void main(String[] args) throws IOException, ParseException,
			InterruptedException, URISyntaxException {
		// Create the TwitchAPI object (useful for debugging and accessing
		// useful methods.)
		TwitchAPI api = new TwitchAPI();
		// Enable debug output
		api.setDebug(true);
		// Create the channel object that requires a channel name as a parameter
		Channel channel = new Channel("Monstercat");

		// Use the channel.getStream() to get the stream object of the channel
		Stream stream = channel.getStream();
		// Makes sure that the channel is streaming right now
		// To check that use this method
		if (channel.isStreaming()) {
			// To get data from the stream you can use these methods
			// If you wanna know what all these methods does please use hover
			// the
			// methods to get a descrition (eclipse)
			System.out.println(stream.getGame().getName());
			System.out.println(stream.getStreamQuality());
			System.out.println(stream.getViewers());
			System.out.println(stream.getAverageFPS());
		} else {
			// Do something else here
		}
	}

}
