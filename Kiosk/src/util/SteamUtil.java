package util;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import domain.Game;



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
	public static void main(String[] args) {
		LinkedList<Game> list = SteamUtil.getSteamGames();
		for (Game game : list) {
			//TODO if already in db, dont add
			DBUtil.setGame(game);
		}
		//addtodb
	}
}