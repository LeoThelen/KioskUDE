package util;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Savepoint;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.*;

import domain.Game;

public class MiscUtil {

	public static void printCurrentDir() {
		System.out.println(
				"Current dir:" + Paths.get("tomcat\\wtpwebapps\\Kiosk\\screenshots").toAbsolutePath().toString());
		System.out.println("using System:" + System.getProperty("user.dir"));

	}

	public static void saveResizedThumbnail(Game g) {
		File f = new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_" + g.getGameID() + ".jpg");
		if (!f.exists()) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new URL(g.getThumbnailLink()).openStream());
				img = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, 460, 215);
				ImageIO.write(img, "png",
						new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_" + g.getGameID() + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveAllResizedThumbnails() {
		LinkedList<Game> gamelist = DBUtil.getGameList();
		for (Game g : gamelist) {
			File f = new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_" + g.getGameID() + ".jpg");
			if (!f.exists())
				if (g.getSteamID() == null) {
					saveResizedThumbnail(g);
				} else {
					saveThumbnailAndScreenshot(g);
				}
		}
	}

	public static void saveThumbnailAndScreenshot(Game g) {
		File f = new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_" + g.getGameID() + ".jpg");
		if (!f.exists()) {
			try (InputStream in = new URL(g.getThumbnailLink()).openStream()) {
				Files.copy(in, Paths.get("C:\\Users\\Leo\\git\\KioskUDE\\Kiosk\\WebContent\\screenshots\\thumb_"
						+ g.getGameID() + ".jpg"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		g = DBUtil.getGameDescriptionByID(g.getGameID());
		try (InputStream in = new URL(g.getScreenshotLink()).openStream()) {
			Files.copy(in, Paths.get("C:\\Users\\Leo\\git\\KioskUDE\\Kiosk\\WebContent\\screenshots\\screenshot_"
					+ g.getGameID() + ".jpg"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
