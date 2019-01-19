package util;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONException;

import domain.Game;
import domain.Tag;



public class SteamUtil {
	static String steamk = "95FED09450A068AEA6FBB7BD94259949";
	static String steamAccountID="76561198025357496";
	
	private static String getHTML(String urlString) {
		HttpURLConnection conn; // The actual connection to the web page
		BufferedReader rd;		// Used to read results from the web page
		String linecontent;			
		String htmlcontent = "";
		try {
			conn = (HttpURLConnection) new URL(urlString).openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((linecontent = rd.readLine()) != null) {
				htmlcontent += linecontent;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlcontent;

	}
	
	public static LinkedList<Game> getOwnedSteamGamesFromAPI() {

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

	public static void addSteamGameToDB(String steamID) {
		Game g = SteamUtil.getGameWithDetails(steamID);
		g = SteamUtil.getSteamGenreTags(g);
		g= SteamUtil.getHMDAgeAndPlayAreaTags(g);
		DBUtil.addGame(g);
	}
	
	private static Game getHMDAgeAndPlayAreaTags(Game game) {//TODO this is hardcoded
		Elements elements = null;
		Elements uskElements = null;
		try {
			Document doc = Jsoup.connect("https://store.steampowered.com/app/"+game.getSteamID()).get();
			System.out.println("Getting information about "+doc.title());
			elements = doc.select("a[href^=https://store.steampowered.com/search/?vrsupport=]");
			//nimm jeden zweiten Eintrag:
			uskElements = doc.select("img[src^=https://steamstore-a.akamaihd.net/public/images/ratings/USK/]");
			String x = uskElements.get(0).attr("src");
			x=x.substring(x.length()-6, x.length()-4);
			System.out.println("USK"+x);
			switch (x) {
			case "18":
			case "16":game.addTag(new Tag("13"));break;
			case "12":game.addTag(new Tag("12"));break;
			case "/6":
			case "/0":game.addTag(new Tag("11"));break;
			
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("keine USK-Angaben gefunden.");
		}
		for (int i=1; i<elements.size();i+=2) {
			System.out.println(elements.get(i).text());
			switch (elements.get(i).text()) {
			case "HTC Vive":game.addTag(new Tag("1"));
				break;
//			case "Oculus Rift":
//				break;
			case "Seated":game.addTag(new Tag("42"));
				break;
			case "Standing":game.addTag(new Tag("41"));
				break;
			case "Room-Scale":game.addTag(new Tag("44"));
				break;
			}
		}
		return game;
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
	
	@SuppressWarnings("unused")
	private static void addAllSteamGamesToDB() {
		LinkedList<Game> list = SteamUtil.getOwnedSteamGamesFromAPI();
		for (Game game : list) {
			//TODO if already in db, dont add
			game = SteamUtil.getGameWithDetails(game.getSteamID());
			game=getHMDAgeAndPlayAreaTags(game);
			System.out.println(game.getName());
			DBUtil.addGame(game);
		}
	}
	
	/**gets genre tags from steamspy
	*/
	public static Game getSteamGenreTags(Game g) {
		List<String> taglist = new ArrayList<String>();
		try {
			JSONObject j = new JSONObject(getHTML("http://steamspy.com/api.php?request=appdetails&appid="+g.getSteamID()));
				String[] genres = j.getString("genre").split(", ");
				j=j.getJSONObject("tags");
				Iterator<String> keysToCopyIterator = j.keys();
				while(keysToCopyIterator.hasNext()) {
				    String key = keysToCopyIterator.next();
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
		getHMDAgeAndPlayAreaTags(new Game("406860"));
//		addSteamGameToDB("451520");//theBlu
//		addSteamGameToDB("397750");//Guided Meditation VR
//		addSteamGameToDB("450390");//The Lab
//		addSteamGameToDB("620980");//Beat Saber
//		addSteamGameToDB("548010");//3D Organon VR Anatomy
//		addSteamGameToDB("550520");//B. Braun Future Operating Room
//		addSteamGameToDB("566580");//The Jigsaw Puzzle Room
//		addSteamGameToDB("531990");//Egg Time
//		addSteamGameToDB("348250");//Google Earth VR
//		addSteamGameToDB("490840");//Gnomes & Goblins (preview)
//		addSteamGameToDB("787790");//Epic Roller Coasters
//		addSteamGameToDB("804490");//Creed: Rise to Glory
//		addSteamGameToDB("406860");//Blind [USK 16]
//		addSteamGameToDB("448280");//Job Simulator
	}
}
