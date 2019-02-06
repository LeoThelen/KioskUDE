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
//		for (Object imglink : images) {//purge first one bc it's just the title image.
//			System.out.println(imglink);
//		}
		g.setName(jsonObj.get("name").toString());
		return g;
	}

	public static Game getGameWithDetails(String oculusID) {
		Game g = new Game();
		g.setOculusID(oculusID);
		try {
			Document doc = Jsoup.connect("https://www.oculus.com/experiences/go/"+oculusID+"/?update_locale=en_US").get();
//			System.out.println("Getting information about "+doc.title());
			g=getJSONObject(g, new JSONObject(doc.selectFirst("script[type]").html()));	//englische Beschreibung und image(s)
			doc = Jsoup.connect("https://www.oculus.com/experiences/go/"+oculusID+"/?update_locale=de_DE").get();
			g.setGermanDescription(new JSONObject(doc.selectFirst("script[type]").html()).get("description").toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		MiscUtil.saveResizedThumbnail(g);//TODO
//		System.setProperty("webdriver.chrome.driver","C:/xampp/chromedriver.exe");
//		WebDriver webDriver = new ChromeDriver();
//		webDriver.navigate().to("https://www.oculus.com/experiences/go/"+oculusID+"/?update_locale=de_DE");
//		// Waiting a little bit before closing
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
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
		DBUtil.addGame(getGameWithDetails("890562797701371"));
		DBUtil.addGame(getGameWithDetails("2252817104759749"));
		DBUtil.addGame(getGameWithDetails("2084588764916379"));
		DBUtil.addGame(getGameWithDetails("1152440564774310"));
		DBUtil.addGame(getGameWithDetails("1064866736899927"));
		DBUtil.addGame(getGameWithDetails("967457083325115"));
		DBUtil.addGame(getGameWithDetails("1092021544167262"));
		DBUtil.addGame(getGameWithDetails("1692021557498395"));
		DBUtil.addGame(getGameWithDetails("878262692296965"));
	}
}
