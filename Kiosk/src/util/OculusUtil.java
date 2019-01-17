package util;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class OculusUtil {
	private static void getJSONObject(JSONObject jsonObj) {
		System.out.println(jsonObj.get("description"));
		JSONArray images = jsonObj.getJSONArray("image");
		for (Object imglink : images) {
			System.out.println(imglink);
		}
		System.out.println(jsonObj.get("name"));
	}
	
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect("https://www.oculus.com/experiences/go/1692021557498395/").get();
			System.out.println(doc.title());
			getJSONObject(new JSONObject(doc.selectFirst("script[type]").html()));	//deutsche Beschreibung und image(s)
			Elements x = doc.getElementsByClass("app-age-rating-icon__text");
			System.out.println(x.html());//USK
			System.out.println(x.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
