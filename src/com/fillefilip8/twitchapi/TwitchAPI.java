package com.fillefilip8.twitchapi;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
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
import org.json.simple.parser.ParseException;

public class TwitchAPI {
	private String client_id;
	private String client_secret;
	private int port;
	private List<String> scopes = new ArrayList<String>();
	private boolean debug;
	private User currentLoggedInUser;
	/**
	 * Create a application on http://www.twitch.tv/settings/connections
	 * Make sure the redirect url needs to be http://localhost:[port]
	 * @param client_id Application Client ID
	 * @param port http://localhost:[port]
	 */
public TwitchAPI(String client_id, String client_secret, int port, String[] scopes){
	this.client_id = client_id;
	this.client_secret = client_secret;
	this.port = port;
}
public String getClientID(){
	return client_id;
}
public String getClientSecret(){
	return client_secret;
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
public User authUser() throws IOException, URISyntaxException, ParseException{
	Desktop.getDesktop().browse(new URI("https://api.twitch.tv/kraken/oauth2/authorize?response_type=code&client_id="+getClientID()+"&redirect_uri=http://localhost:" + getPort() + "&scope=channel_read"));

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
	
	
	String url = "https://api.twitch.tv/kraken/oauth2/token";
	URL obj = new URL(url);
	HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

	//add reuqest header
	con.setRequestMethod("POST");
	con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.91 Safari/537.36");
	con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

	String urlParameters = "client_id=" + getClientID() + "&client_secret=" + getClientSecret() + "&grant_type=authorization_code&redirect_uri=http://localhost:" + getPort() + "&code=" + code;

	// Send post request
	con.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	wr.writeBytes(urlParameters);
	wr.flush();
	wr.close();

	int responseCode = con.getResponseCode();
	if(debug){
	System.out.println("\nSending 'Accesscode' request to URL : " + url);
	System.out.println("Post parameters : " + urlParameters);
	System.out.println("Response Code : " + responseCode);
	}
	BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
	JSONObject json;
	JSONParser parser = new JSONParser();
	json = (JSONObject) parser.parse(response.toString());
	
	System.out.println(json.get("access_token"));
	currentLoggedInUser = new User(json.get("access_token").toString());
	return new User(json.get("access_token").toString());
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



}}
