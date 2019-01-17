package util;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;

import domain.Game;


public class OculusUtil {
	private static Game getJSONObject(Game g, JSONObject jsonObj) {
		
		g.setEnglishDescription(jsonObj.get("description").toString());
		JSONArray images = jsonObj.getJSONArray("image");
		
		g.setThumbnailLink(images.getString(0));
		g.setScreenshotLink(images.getString(1));
//		for (Object imglink : images) {
//			System.out.println(imglink);
//		}
		g.setName(jsonObj.get("name").toString());
		return g;
	}
	
	public static Game getGameWithDetails(int gameID, String oculusID) {
		Game g = new Game();
		g.setGameID(Integer.toString(gameID));
		try {
			Document doc = Jsoup.connect("https://www.oculus.com/experiences/go/"+oculusID+"/?update_locale=en_US").get();
			System.out.println(doc.title());
			g=getJSONObject(g, new JSONObject(doc.selectFirst("script[type]").html()));	//englische Beschreibung und image(s)
			doc = Jsoup.connect("https://www.oculus.com/experiences/go/"+oculusID+"/?update_locale=de_DE").get();
			g.setGermanDescription(new JSONObject(doc.selectFirst("script[type]").html()).get("description").toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.setProperty("webdriver.chrome.driver","C:/xampp/chromedriver.exe");
//		WebDriver webDriver = new ChromeDriver();
//		webDriver.navigate().to("https://www.oculus.com/experiences/go/"+oculusID+"/?update_locale=de_DE");
//		// Waiting a little bit before closing
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////TODO: tagSetzen:
////		webDriver.findElement(By.className("app-age-rating-icon__text")).getText());
//		// Closing the browser and webdriver
//		webDriver.close();
//		webDriver.quit();
		return g;
	}
	
	public static void main(String[] args) {
		int c=10001;
		DBUtil.addGame(getGameWithDetails(c++,"890562797701371"));
		DBUtil.addGame(getGameWithDetails(c++,"2252817104759749"));
		DBUtil.addGame(getGameWithDetails(c++,"2084588764916379"));
		DBUtil.addGame(getGameWithDetails(c++,"1152440564774310"));
		DBUtil.addGame(getGameWithDetails(c++,"1064866736899927"));
		DBUtil.addGame(getGameWithDetails(c++,"967457083325115"));
		DBUtil.addGame(getGameWithDetails(c++,"1092021544167262"));
		DBUtil.addGame(getGameWithDetails(c++,"1692021557498395"));
		DBUtil.addGame(getGameWithDetails(c++,"878262692296965"));
	}
}
