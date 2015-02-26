package com.fillefilip8.twitchapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

public class Test {
public static void main(String[] args) throws IOException, ParseException, InterruptedException, URISyntaxException {
	List<Stream> list = TwitchAPI.getAllGameStreamers("Minecraft", 1, 1);
	System.out.println(list.get(0).getStreamQuality());

	//System.out.println(user.getChannel().getFollowerCount());
	/*
Channel channel = new Channel("chapMAD");
if(channel.isStreaming()){
	System.out.println(channel.getChannelName() + " is streaming " + channel.getStream().getGame() + " with " + channel.getStream().getViewers() + "Viewers " + "Title: " + channel.getStream().getStatus());
}else{
	System.out.println(channel.getChannelName() + " is not streaming!");
}*/


	}

}
