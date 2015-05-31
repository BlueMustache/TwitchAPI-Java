package com.fillefilip8.twitchapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Utils {
	/*
	 * Send a GET request to get data from the TwitchAPI
	 */
	public static JSONObject sendGETRequest(URL url) throws IOException, ParseException{
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if(TwitchAPI.debug){
		System.out.println("Response Code : " + responseCode);
		}
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		in.close();
		return json;
		
	}
	public static int random(int min, int max){
		Random r = new Random();
		int R = r.nextInt(max-min) + min;
		return R;
		
	}
	/**
	 * Replaces spaces with %20
	 * @return Converted string
	 */
	public static String convertStringToURL(String string){
		return string.replaceAll(" ", "%20");
	}
	
}
