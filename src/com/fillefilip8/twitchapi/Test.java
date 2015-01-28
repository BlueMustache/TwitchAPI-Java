package com.fillefilip8.twitchapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

public class Test {
public static void main(String[] args) throws IOException, ParseException, InterruptedException {
	String[] yay = {"",""};
TwitchAPI api = new TwitchAPI("r2eqpoppz3x65mohui205olf6vonqh9", "66mffgogtr8zkl9pflrhdoc92btlpfk", 1234, yay);
try {
	User user = api.authUser();
	System.out.println(user.getChannel().getFollowerCount());
} catch (URISyntaxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

	}

}
