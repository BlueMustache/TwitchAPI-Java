package com.fillefilip8.twitchapi;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.simple.parser.ParseException;

public class Test {
	public static void main(String[] args) throws IOException, ParseException,
			InterruptedException, URISyntaxException {

		System.out.println(new Game("123").exists());
	}

}
