package com.fillefilip8.twitchapi;

import java.awt.Desktop;
import java.io.BufferedReader;
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

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TwitchAPI {
	private String client_id;
	private int port;
	private List<String> scopes = new ArrayList<String>();
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
public void authUser() throws IOException, URISyntaxException{
	Desktop.getDesktop().browse(new URI("https://api.twitch.tv/kraken/oauth2/authorize?response_type=token&client_id="+getClientID()+"&redirect_uri=http://localhost:" + getPort() + "&scope=channel_read"));

try {
	waitForToken();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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
public static List<Stream> getAllGameStreamers(String game,int total, int maxViewers, boolean debug) throws IOException, org.json.simple.parser.ParseException{
	List<Stream> streamers = new ArrayList<Stream>();
	URL url = new URL("https://api.twitch.tv/kraken/streams?game=" + game + "&limit=" + total + "&offset=" + maxViewers);
	HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
	// optional default is GET
	con.setRequestMethod("GET");

	//add request header
	con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);

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
	    	
	    	
		     streamers.add(new Stream((String)channel.get("display_name"),(String)channel.get("game"), 1, (String)channel.get("status")));
		     System.out.println("Added " + channel.get("display_name") + " to the ArrayList");
		    //System.out.println(streamers.get(i).getChannel().getFollowerCount());
		     //streamsI.remove();
	    	if(debug){
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
	public static void login() throws IOException, org.json.simple.parser.ParseException {
		
		
}}
