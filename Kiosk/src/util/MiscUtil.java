package util;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import domain.Game;

public class MiscUtil {

	public static void printCurrentDir() {
		System.out.println("Current dir:" + Paths.get("tomcat\\wtpwebapps\\Kiosk\\screenshots").toAbsolutePath().toString());
		System.out.println("using System:" + System.getProperty("user.dir"));

	}

	public static void main(String[] args) {
		// screenshotsRunterladen();
		printCurrentDir();
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
