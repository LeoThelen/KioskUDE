package util;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Savepoint;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.*;

import domain.Game;

public class ScreenshotUtil {

	/**
	 * speichert Thumbnail und Screenshot eines Spiels (und passt dessen Größe an)
	 * im Pfad unter 
	 * "tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_" + game.getGameID() + ".jpg" (460x215)
	 * und unter
	 * "tomcat\\wtpwebapps\\Kiosk\\screenshots\\screenshot_" + game.getGameID() + ".jpg"
	 */
	public static void saveResizedThumbnailAndScreenshot(Game game) {
		BufferedImage img = null;

		try {
			System.out.println("Converting Thumbnail from " + game.getThumbnailLink());
			if (game.getThumbnailLink() != null) {
				img = ImageIO.read(new URL(game.getThumbnailLink()).openStream());
				img = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, 460, 215);
				ImageIO.write(img, "png",
						new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_" + game.getGameID() + ".jpg"));
			}
			if (game.getScreenshotLink() != null) {
				img = ImageIO.read(new URL(game.getScreenshotLink()).openStream());
				img = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.FIT_TO_HEIGHT, 999, 337);
				ImageIO.write(img, "png",
						new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\screenshot_" + game.getGameID() + ".jpg"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveAllThumbnailsAndScreenshots() {
		LinkedList<Game> gamelist = DBUtil.getGameList();
		for (Game g : gamelist) {
			if (g.getSteamID() == null) {
				saveResizedThumbnailAndScreenshot(g);
			} else {
				saveThumbnailAndScreenshot(g);
			}
		}
	}

	/**
	 * speichert Thumbnail und Screenshots (ohne die Größe zu verändern) im Pfad
	 * unter "tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_" + g.getGameID() +
	 * ".jpg" und unter "tomcat\\wtpwebapps\\Kiosk\\screenshots\\screenshot_" +
	 * g.getGameID() + ".jpg"
	 */
	public static void saveThumbnailAndScreenshot(Game g) {
		if (g.getThumbnailLink() != null) {
			System.out.println("Converting Thumbnail from " + g.getThumbnailLink());
			try (InputStream in = new URL(g.getThumbnailLink()).openStream()) {
				Files.copy(in, Paths.get("tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_" + g.getGameID() + ".jpg"),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (g.getScreenshotLink() != null) {
			try (InputStream in = new URL(g.getScreenshotLink()).openStream()) {
				Files.copy(in,
						Paths.get("tomcat\\wtpwebapps\\Kiosk\\screenshots\\screenshot_" + g.getGameID() + ".jpg"),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteThumbnailAndScreenshot(Game g) {
		File f = new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_" + g.getGameID() + ".jpg");
		if (!f.exists()) {
			f.delete();
		}
		f = new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\screenshot_" + g.getGameID() + ".jpg");
		if (!f.exists()) {
			f.delete();
		}
	}
	
	public static boolean isPicture(String link) {
		return link.contains(".jpg") || link.contains(".jpeg") || link.contains(".png");
	}
}
