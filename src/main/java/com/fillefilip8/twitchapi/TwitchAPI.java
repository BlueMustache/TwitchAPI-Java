package com.fillefilip8.twitchapi;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TwitchAPI {
	private String client_id;
	private int port;
	private List<String> scopes = new ArrayList<String>();
	private String access_token;
	static boolean debug = false;
	/**
	 * Create a application on http://www.twitch.tv/settings/connections
	 * Make sure the redirect url needs to be http://localhost:[port]
	 * @param client_id Application Client ID
	 * @param port http://localhost:[port]
	 */
public TwitchAPI(String client_id, int port, String[] scopes){
	this.client_id = client_id;
	this.port = port;
}
public String getClientID(){
	return client_id;
}
public int getPort(){
	return port;	
}
public List<String> getScopes(){
	return scopes;	
}
public void setDebug(boolean debug){
	this.debug = debug;
}
public void authUser() throws IOException, URISyntaxException{
	Desktop.getDesktop().browse(new URI("https://api.twitch.tv/kraken/oauth2/authorize?response_type=token&client_id="+getClientID()+"&redirect_uri=http://localhost:" + getPort() + "&scope=channel_read"));

//Server Stuff
	ServerSocket serverSock = new ServerSocket(getPort());
	Socket sock = serverSock.accept();

	InputStream sis = sock.getInputStream();
	BufferedReader br = new BufferedReader(new InputStreamReader(sis));
	String request = br.readLine(); // Now you get GET index.html HTTP/1.1`
	String[] requestParam = request.split(" ");
	String re = requestParam[1];
	System.out.println(re);
	String code = "";
	for (int i=7;i<re.indexOf('&');i++) code=code+re.charAt(i);
	System.out.println("Code: "+code);
	access_token = code;
	PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
	
	/*out.write("HTTP/1.0 200 OK\r\n");
    out.write("Content-Type: text/html\r\n");
    out.write("\r\n");
    out.write("<TITLE>Success</TITLE>");
   // out.write("<P>Please Read the URL in your browser. It will have a '#' and \"access_token=SOMETHING\" - This something is your Auth token. It might say that the access got denied - it means it got denied.</P>");
	out.write("You have successfully logged in! - The application will now get your access_token!");
	out.write("<br>");
	out.write("Your access_token: " + code);
    out.write("<p style='color: lime'>You can now close this tab or click <a href='http://google.com'>Here!</a></p>");
    out.write("<footer>");
    out.write("<h2>Powered by TwitchAPI for Java</h2>");
    out.write("</footer>");*/
	try {
		File file = new File("success.html");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
			out.write(line);
			stringBuffer.append("\n");
		}
		fileReader.close();
		System.out.println(stringBuffer.toString());
	} catch (IOException e) {
		e.printStackTrace();
	}
    out.flush();
	

	
	br.close();
	out.close();
	serverSock.close();
	//return new User(code);
}



public void waitForToken() throws Exception {
	ServerSocket serverSock = new ServerSocket(getPort());
	Socket sock = serverSock.accept();

	InputStream sis = sock.getInputStream();
	BufferedReader br = new BufferedReader(new InputStreamReader(sis));
	String request = br.readLine(); // Now you get GET index.html HTTP/1.1`
	String[] requestParam = request.split(" ");
	String re = requestParam[1];
	
	System.out.println(re);
	String code = "";
	for (int i=7;i<re.indexOf('&');i++) code=code+re.charAt(i);
	System.out.println("Code: "+code);
	access_token = code;
	PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
	
	out.write("HTTP/1.0 200 OK\r\n");
    out.write("Content-Type: text/html\r\n");
    out.write("\r\n");
    out.write("<TITLE>Success</TITLE>");
    out.write("<P>Please Read the URL in your browser. It will have a '#' and \"access_token=SOMETHING\" - This something is your Auth token. It might say that the access got denied - it means it got denied.</P>");
	out.flush();
	

	
	br.close();
	out.close();
	serverSock.close();
}
/**
 * 
 * @param game Name of the game!
 * @param debug Should we debug all the data to the console?
 * @return ArrayList of all streamers
 * @throws IOException
 * @throws org.json.simple.parser.ParseException
 */
public static List<Stream> getAllGameStreamers(String game,int total, int maxViewers) throws IOException, org.json.simple.parser.ParseException{
	List<Stream> streamers = new ArrayList<Stream>();
	URL url = new URL("https://api.twitch.tv/kraken/streams?game=" + game + "&limit=" + total + "&offset=" + maxViewers);
	HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
	// optional default is GET
	con.setRequestMethod("GET");

	//add request header
	con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
	int responseCode = con.getResponseCode();
	if(TwitchAPI.debug){
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);
	}
	BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
	StringBuffer response = new StringBuffer();
	JSONParser parser = new JSONParser();
	JSONObject json = (JSONObject) parser.parse(in);
	

	
		
	
	in.close();

	//print result
	// get an array from the JSON object
//	JSONArray streams= (JSONArray) json.get("streams");
	JSONArray streams = (JSONArray) json.get("streams");

    JSONObject channel;
    JSONObject name;
	//JSONArray streamsChannel = (JSONArray) ;

   Iterator<JSONObject> streamsI = streams.iterator();
   while (streamsI.hasNext()){
	   for (int i=0; i<streams.size(); i++) {
		   JSONObject yay = streamsI.next();
	    	channel = (JSONObject) yay.get("channel");
	    	
	    	
		     streamers.add(new Stream((String)channel.get("display_name")));
		     
		    //System.out.println(streamers.get(i).getChannel().getFollowerCount());
		     //streamsI.remove();
	    	if(TwitchAPI.debug){
	    		System.out.println("Added " + channel.get("display_name") + " to the ArrayList");
	    	//String viewers = streamsI.next().get("viewers").toString();
	      System.out.println(channel.get("display_name") + " is playing " + channel.get("game") + "with title " +  channel.get("status") + " for " + yay.get("viewers") + "Viewers"); 
	    	}

		     }

      /*channel = (JSONObject) streams.get(i);
    	 JSONObject slide = streams.iterator();
    	 String title = (String)slide.get("title");
    		System.out.println(channel);*/
    	
    }
return streamers;


	//+streams.get(i)
	// take the elements of the json array
	/*for(int i=0; i<streams.length(); i++){
		streamsViewers = streams.getJSONObject(i);
		streamsName = (JSONArray) streams.getJSONArray(i);
		displayName = streamsName.getJSONObject(i);
		System.out.println(channel.get(i) + " " + streamsViewers.optString("viewers"));
	}*/



	}



@Deprecated
public static Stream getRandomStream(String game, int maxViewers) throws IOException, ParseException{
	//https://api.twitch.tv/kraken/streams?game=Minecraft&limit=1&offset=1
	Random r = new Random();
	int R = r.nextInt(1000-1) + 1;
	URL url = new URL("https://api.twitch.tv/kraken/streams?game=" + game +  "&limit=1" + "&offset=" + maxViewers);

	HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
	try {
		Desktop.getDesktop().browse(con.getURL().toURI());
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// optional default is GET
	con.setRequestMethod("GET");

	//add request header
	con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'GetRandomStream' request for with URL: " + url);
	System.out.println("Response Code : " + responseCode);

	BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
	JSONParser parser = new JSONParser();
	JSONObject json = (JSONObject) parser.parse(in);
	System.out.println(json);
	in.close();
	return null;
	
}
}