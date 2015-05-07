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
	private String code;
	private boolean isSetup;
	static boolean debug = false;

	/**
	 * Create a application on http://www.twitch.tv/settings/connections Make
	 * sure the redirect url needs to be http://localhost:[port]
	 * 
	 * @param client_id
	 *            Application Client ID
	 * @param port
	 *            http://localhost:[port]
	 */
	public TwitchAPI(String client_id, String client_secret, int port,
			String[] scopes) {
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.port = port;
		this.isSetup = true;
	}

	public TwitchAPI() {
		this.isSetup = false;
	}

	public String getClientID() {
		return client_id;
	}

	public int getPort() {
		return port;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getCode() {
		return code;

	}

	public void authUser() {
		if (isSetup) {
			try {
				Desktop.getDesktop().browse(
						new URI(
								"https://api.twitch.tv/kraken/oauth2/authorize?response_type=code&client_id="
										+ getClientID()
										+ "&redirect_uri=http://localhost:"
										+ getPort() + "&scope=channel_read"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		} else {
			System.out
					.println("You need to use the contructor with multiple parameters");
		}
		/*
		 * //Server Stuff ServerSocket serverSock = new ServerSocket(getPort());
		 * Socket sock = serverSock.accept();
		 * 
		 * InputStream sis = sock.getInputStream(); BufferedReader br = new
		 * BufferedReader(new InputStreamReader(sis)); String request =
		 * br.readLine(); // Now you get GET index.html HTTP/1.1` String[]
		 * requestParam = request.split(" "); String re = requestParam[1];
		 * System.out.println(re); String code = ""; for (int
		 * i=7;i<re.indexOf('&');i++) code=code+re.charAt(i);
		 * System.out.println("Code: "+code); access_token = code; PrintWriter
		 * out = new PrintWriter(sock.getOutputStream(), true);
		 * 
		 * /*out.write("HTTP/1.0 200 OK\r\n");
		 * out.write("Content-Type: text/html\r\n"); out.write("\r\n");
		 * out.write("<TITLE>Success</TITLE>"); // out.write(
		 * "<P>Please Read the URL in your browser. It will have a '#' and \"access_token=SOMETHING\" - This something is your Auth token. It might say that the access got denied - it means it got denied.</P>"
		 * ); out.write(
		 * "You have successfully logged in! - The application will now get your access_token!"
		 * ); out.write("<br>"); out.write("Your access_token: " + code);
		 * out.write(
		 * "<p style='color: lime'>You can now close this tab or click <a href='http://google.com'>Here!</a></p>"
		 * ); out.write("<footer>");
		 * out.write("<h2>Powered by TwitchAPI for Java</h2>");
		 * out.write("</footer>"); try { File file = new File("success.html");
		 * FileReader fileReader = new FileReader(file); BufferedReader
		 * bufferedReader = new BufferedReader(fileReader); StringBuffer
		 * stringBuffer = new StringBuffer(); String line; while ((line =
		 * bufferedReader.readLine()) != null) { stringBuffer.append(line);
		 * out.write(line); stringBuffer.append("\n"); } fileReader.close();
		 * System.out.println(stringBuffer.toString()); } catch (IOException e)
		 * { e.printStackTrace(); } out.flush();
		 * 
		 * 
		 * 
		 * br.close(); out.close(); serverSock.close();
		 */
		// return new User(code);
		try {
			waitForToken();
			URL url = new URL("https://api.twitch.tv/kraken/oauth2/authorize");
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			// optional default is GET
			con.setRequestMethod("POST");
			//
			// add request header
			con.setDoOutput(true);
			String urlParameters = "?client_id=" + getClientID()
					+ "&client_secret=" + client_secret
					+ "&grant_type=authorization_code"
					+ "&redirect_uri=http://localhost:" + getPort() + "&code="
					+ code;
			// con.setRequestProperty("User-Agent",
			// "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			System.out.println(urlParameters);
			int responseCode = con.getResponseCode();
			if (TwitchAPI.debug) {
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(in);

			in.close();

			System.out.println(json);
		} catch (Exception e) {
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
		for (int i = 7; i < re.indexOf('&'); i++)
			code = code + re.charAt(i);
		System.out.println("Code: " + code);
		this.code = code;
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

		out.write("HTTP/1.0 200 OK\r\n");
		out.write("Content-Type: text/html\r\n");
		out.write("\r\n");
		out.write("<TITLE>Success</TITLE>");
		out.write("<P>Twitch Monitor has the accesstoken now please close this page!</P>");
		out.flush();

		br.close();
		out.close();
		serverSock.close();

		// lka6vahqgcxslurtpdabg0fv1ip8cc
	}

	/**
	 * 
	 * @param game
	 *            Name of the game!
	 * @return ArrayList of all streamers
	 * @throws IOException
	 * @throws org.json.simple.parser.ParseException
	 */
	@SuppressWarnings("unchecked")
	public static List<Stream> getAllGameStreamers(String game, int total,
			int maxViewers) throws IOException,
			org.json.simple.parser.ParseException {
		List<Stream> streamers = new ArrayList<Stream>();
		URL url = new URL("https://api.twitch.tv/kraken/streams?game=" + game
				+ "&limit=" + total + "&offset=" + maxViewers);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if (TwitchAPI.debug) {
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);

		in.close();

		// print result
		// get an array from the JSON object
		// JSONArray streams= (JSONArray) json.get("streams");
		JSONArray streams = (JSONArray) json.get("streams");

		JSONObject channel;
		Iterator<JSONObject> streamsI = streams.iterator();
		while (streamsI.hasNext()) {
			for (int i = 0; i < streams.size(); i++) {
				JSONObject yay = streamsI.next();
				channel = (JSONObject) yay.get("channel");

				streamers.add(new Stream((String) channel.get("display_name")));

				// System.out.println(streamers.get(i).getChannel().getFollowerCount());
				// streamsI.remove();
				if (TwitchAPI.debug) {
					System.out.println("Added " + channel.get("display_name")
							+ " to the ArrayList");
					// String viewers =
					// streamsI.next().get("viewers").toString();
					System.out.println(channel.get("display_name")
							+ " is playing " + channel.get("game")
							+ "with title " + channel.get("status") + " for "
							+ yay.get("viewers") + "Viewers");
				}

			}

			/*
			 * channel = (JSONObject) streams.get(i); JSONObject slide =
			 * streams.iterator(); String title = (String)slide.get("title");
			 * System.out.println(channel);
			 */

		}
		return streamers;

		// +streams.get(i)
		// take the elements of the json array
		/*
		 * for(int i=0; i<streams.length(); i++){ streamsViewers =
		 * streams.getJSONObject(i); streamsName = (JSONArray)
		 * streams.getJSONArray(i); displayName = streamsName.getJSONObject(i);
		 * System.out.println(channel.get(i) + " " +
		 * streamsViewers.optString("viewers")); }
		 */

	}

	/**
	 * 
	 * @param game What game does the streamer needs to play right now?
	 * @param maxViewers How many viewers does the streamer needs to have right now?
	 * @return Stream Object of a random streamer
	 * @throws IOException
	 * @throws ParseException
	 */
	public static Stream getRandomStream(String game, int maxViewers)
			throws IOException, ParseException {
		// https://api.twitch.tv/kraken/streams?game=Minecraft&limit=1&offset=1
		//1
		URL url1 = new URL("https://api.twitch.tv/kraken/streams?game=" + game
				+ "&limit=1" + "&offset=" + 10000);
		HttpsURLConnection con1 = (HttpsURLConnection) url1.openConnection();
		
		con1.setRequestMethod("GET");
		con1.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con1.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);
		
		//2
		long total = (long) json.get("_total");
		total--;
		in.close();
		URL url2 = new URL("https://api.twitch.tv/kraken/streams?game=" + game
				+ "&limit=1" + "&offset=" + Utils.random(0, (int) total));

		HttpsURLConnection con2 = (HttpsURLConnection) url2.openConnection();
		try {
			Desktop.getDesktop().browse(con2.getURL().toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		con2.setRequestMethod("GET");
		con2.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con2.getResponseCode();
		System.out.println("\nSending 'GetRandomStream' request for with URL: "
				+ url2);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in2 = new BufferedReader(new InputStreamReader(
				con2.getInputStream()));
		JSONParser parser2 = new JSONParser();
		JSONObject json2 = (JSONObject) parser2.parse(in2);
		JSONArray streams = (JSONArray) json2.get("streams");
		JSONObject stream = (JSONObject) streams.get(0);
		JSONObject channel = (JSONObject) stream.get("channel");
		System.out.println(channel.get("display_name"));
		Stream s = new Stream((String)channel.get("display_name"));
		in.close();
		return s;

	}

	/**
	 * 
	 * @param total
	 *            How many teams should we get?
	 * @param offset
	 *            Offset
	 * @return List of teams received
	 * @throws IOException
	 * @throws org.json.simple.parser.ParseException
	 */
	@SuppressWarnings("unchecked")
	public static List<Team> getAllTeams(int total, int offset)
			throws IOException, ParseException {
		List<Team> teamsList = new ArrayList<Team>();
		URL url = new URL("https://api.twitch.tv/kraken/teams?limit=" + total
				+ "&offset=" + offset);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		int responseCode = con.getResponseCode();
		if (TwitchAPI.debug) {
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(in);

		in.close();
		JSONArray teams = (JSONArray) json.get("teams");
		System.out.println(teams);
		Iterator<JSONObject> teamsI = teams.iterator();
		while (teamsI.hasNext()) {
			for (int i = 0; i < teams.size(); i++) {
				JSONObject yay = teamsI.next();
				teamsList.add(new Team((String) yay.get("name")));
				if (TwitchAPI.debug) {
					System.out.println("Added " + yay.get("name")
							+ " to the ArrayList");
				}

			}

		}
		return teamsList;
	}
}