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
	
	//TODO Refactoring
	public static Game getSteamGameWithDetailsAndTags(String steamID) {
		Game g = SteamUtil.getGameObjectFromSteamAPI(steamID);
		g = SteamUtil.addGenreTagsFromSteamSpyTo(g);
		g = SteamUtil.getUSKAndVRTags(g);
		return g;
	}

	public static Game getSteamGameWithTags(String steamID) {
		Game g = Game.FromSteamID(steamID);
		g = SteamUtil.addGenreTagsFromSteamSpyTo(g);
		g = SteamUtil.getUSKAndVRTags(g);
		return g;
	}
	
	
	private static Game getUSKAndVRTags(Game game) {//TODO has to be commented
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
			case "16":game.addTag(Tag.FromTagID("13"));break;
			case "12":game.addTag(Tag.FromTagID("12"));break;
			case "/6":
			case "/0":game.addTag(Tag.FromTagID("11"));break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("keine USK-Angaben gefunden.");
		}
		for (int i=1; i<elements.size();i+=2) {
			System.out.println(elements.get(i).text());
			switch (elements.get(i).text()) {
			case "HTC Vive":game.addTag(Tag.FromTagID("1"));
				break;
//			case "Oculus Rift":
//				break;
			case "Seated":game.addTag(Tag.FromTagID("42"));
				break;
			case "Standing":game.addTag(Tag.FromTagID("41"));
				break;
			case "Room-Scale":game.addTag(Tag.FromTagID("44"));
				break;
			}
		}
		return game;
	}

	public static Game getGameObjectFromSteamAPI(String steamID) {
		Game g = Game.FromSteamID(steamID);
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
	
	public static Game addGenreTagsFromSteamSpyTo(Game g) {
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
		//compare english label from SteamSpy and DB. If equal label then add Tag to GameObject
		for(int i = 0; i < taglist.size(); i++) {
			Tag newTag = DBUtil.getTagByLabelEN(taglist.get(i));
			if(newTag != null) {
				System.out.println("Tag von Steamspy: "+newTag.getLabelEN());
				g.addTag(newTag);
			}
		}
		return g;
	}

}
