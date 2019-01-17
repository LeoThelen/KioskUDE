package util;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import domain.Game;
import domain.Tag;



public class SteamUtil {
	static String steamk = "95FED09450A068AEA6FBB7BD94259949";
	static String steamAccountID="76561198025357496";
	
	private static String getHTML(String urlString) {

		HttpURLConnection conn; // The actual connection to the web page
		BufferedReader rd; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try {
			conn = (HttpURLConnection) new URL(urlString).openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	public static LinkedList<Game> getSteamGames() {

		String urlToRead = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?"
				+ "key=" + steamk 
				+ "&steamid="+steamAccountID
				+ "&include_appinfo=1"
				+ "&format=json";
		JSONObject obj = new JSONObject(getHTML(urlToRead)).getJSONObject("response");
		LinkedList<Game> list = new LinkedList<>();
		JSONArray arr = obj.getJSONArray("games");
		for (int i = 0; i < arr.length(); i++)
		{
			list.add(new Game(arr.getJSONObject(i).get("appid").toString()));
			list.getLast().setName(arr.getJSONObject(i).get("name").toString());
		}
		return list;
	}

	public static Game getGameWithDetails(String steamID) {
		Game g = new Game(steamID);
		g.setGameID(steamID);//TODO diese Zeile evtl wieder entfernen.
		JSONObject j = new JSONObject(getHTML("https://store.steampowered.com/api/appdetails?appids="+steamID));
		if(j.getJSONObject(steamID).get("success").equals(true)) {
			j=j.getJSONObject(steamID).getJSONObject("data");//geht in die Verschachtelung des JSON-Objektes
			g.setName(				j.getString("name"));
			g.setEnglishDescription(j.getString("detailed_description"));
			g.setGermanDescription(	j.getString("short_description"));
			g.setScreenshotLink(	j.getJSONArray("screenshots").getJSONObject(0).getString("path_thumbnail"));//gets first Screenshot

		}
		return g;
	}
	
	public static void addSteamGameToDB(String steamID) {
		Game g = SteamUtil.getGameWithDetails(steamID);
		g = SteamUtil.getSteamTags(g);
		DBUtil.addGame(g);
	}
	
	@SuppressWarnings("unused")
	private static void addAllSteamGamesToDB() {
		LinkedList<Game> list = SteamUtil.getSteamGames();
		for (Game game : list) {
			//TODO if already in db, dont add
			game = SteamUtil.getGameWithDetails(game.getSteamID());
			System.out.println(game.getName());
			DBUtil.addGame(game);
		}
	}
	
	public static Game getSteamTags(Game g) {
		//gets tags from steamspy
		List<String> taglist = new ArrayList<String>();
		try {
			JSONObject j = new JSONObject(getHTML("http://steamspy.com/api.php?request=appdetails&appid="+g.getSteamID()));
				String[] genres = j.getString("genre").split(", ");
				j=j.getJSONObject("tags");
				Iterator keysToCopyIterator = j.keys();
				while(keysToCopyIterator.hasNext()) {
				    String key = (String) keysToCopyIterator.next();
				    taglist.add(key);
				}
				for(int i = 0; i < genres.length; i++) {
					taglist.add(genres[i]);
				}
		}catch(JSONException e) {
			e.printStackTrace();
		}
		//if Tag exists in DB then AddGameTag
		for(int i = 0; i < taglist.size(); i++) {
			Tag newTag = DBUtil.getTagByLabelEN(taglist.get(i));
			if(newTag != null) {
				System.out.println("Tag von Steamspy: "+newTag.getLabelEN());
				g.getTaglist().add(newTag);
			}
		}
		return g;
	}

	public static void main(String[] args) {
		addSteamGameToDB("451520");
		addSteamGameToDB("397750");
		addSteamGameToDB("450390");
		addSteamGameToDB("620980");
		addSteamGameToDB("548010");
		addSteamGameToDB("550520");
		addSteamGameToDB("566580");
		addSteamGameToDB("531990");
		addSteamGameToDB("348250");
		addSteamGameToDB("490840");
		addSteamGameToDB("787790");
		addSteamGameToDB("804490");
		addSteamGameToDB("406860");
		addSteamGameToDB("448280");
	}
}
