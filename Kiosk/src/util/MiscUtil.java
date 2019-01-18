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

	private static void saveResizedThumbnail(Game g) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new URL(g.getThumbnailLink()).openStream());
			img = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, 460, 215);
			ImageIO.write(img, "png", new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_"
					+ g.getGameID() + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveAllResizedThumbnails() {
		LinkedList<Game> gamelist = DBUtil.getGameList(-1);
		for(Game g:gamelist) {
		File f = new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_"
				+ g.getGameID() + ".jpg");
		if(!f.exists())
		saveResizedThumbnail(gamelist.getLast());
		}
	}

	private static void screenshotsRunterladen() {
		LinkedList<Game> list = DBUtil.getGameList();
		for (Game g : list) {
			// TODO check if already exists: if(Files.exists(Paths.get(".")))
			try (InputStream in = new URL(g.getThumbnailLink()).openStream()) {
				Files.copy(in, Paths.get("C:\\Users\\Leo\\git\\KioskUDE\\Kiosk\\WebContent\\screenshots\\thumb_"
						+ g.getGameID() + ".jpg"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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

}
