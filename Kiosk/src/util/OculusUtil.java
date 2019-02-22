package util;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import domain.Game;


public class OculusUtil {
//	public static void main(String[] args) {
//		DBUtil.addGame(getGameWithDetails("890562797701371"));
//		DBUtil.addGame(getGameWithDetails("2252817104759749"));
//		DBUtil.addGame(getGameWithDetails("2084588764916379"));
//		DBUtil.addGame(getGameWithDetails("1152440564774310"));
//		DBUtil.addGame(getGameWithDetails("1064866736899927"));
//		DBUtil.addGame(getGameWithDetails("967457083325115"));
//		DBUtil.addGame(getGameWithDetails("1092021544167262"));
//		DBUtil.addGame(getGameWithDetails("1692021557498395"));
//		DBUtil.addGame(getGameWithDetails("878262692296965"));
//	}
	
	public static Game getGameWithDetails(String oculusID) {
		Game game = new Game();
		game.setOculusID(oculusID);
		try {
			/* Englische Beschreibung, Name und Screenshots */
			Document doc = Jsoup.connect("https://www.oculus.com/experiences/go/"+oculusID+"/?update_locale=en_US").get();
			game=getNameAndDescriptionAndImages(game, new JSONObject(doc.selectFirst("script[type]").html()));	
			/* Deutsche Beschreibung */
			doc = Jsoup.connect("https://www.oculus.com/experiences/go/"+oculusID+"/?update_locale=de_DE").get();
			game.setGermanDescription(new JSONObject(doc.selectFirst("script[type]").html()).get("description").toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		seleniumExtractedInformation(oculusID);
		return game;
	}

	
//private static void ExtractInformationWithSelenium(String oculusID) {
//	System.setProperty("webdriver.chrome.driver","C:/xampp/chromedriver.exe");
//	WebDriver webDriver = new ChromeDriver();
//	webDriver.navigate().to("https://www.oculus.com/experiences/go/"+oculusID+"/?update_locale=de_DE");
//	// Waiting a little bit before closing
//	try {
//		Thread.sleep(1000);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	//TODO: tagSetzen:
//	
//	//TODO folgende Zeile catch NoSuchElementException
////	System.out.println(webDriver.findElement(By.className("app-age-rating-icon__text")).getText());
//	//Closing the browser and webdriver
//	webDriver.close();
//	webDriver.quit();
//}
	
/**
 * überträgt Daten aus JSONObject ins Game
 */
private static Game getNameAndDescriptionAndImages(Game game, JSONObject jsonObj) {
		
		game.setEnglishDescription(jsonObj.get("description").toString());
		JSONArray images = jsonObj.getJSONArray("image");
		game.setThumbnailLink(images.getString(0));
		
		if (images.length()>1) {
			game.setScreenshotLink(images.getString(1));
		}else {	//wenn keine 2 screenshots existieren, dann wird nochmal der erste Link als Screenshotlink hinterlegt.
			
			game.setScreenshotLink(images.getString(0));
		}
//		for (Object imglink : images) {//purge first one bc it's just the title image.
//			System.out.println(imglink);
//		}
		game.setName(jsonObj.get("name").toString());
		return game;
	}
}