package util;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import domain.Game;

public class MiscUtil {

	public static void main(String[] args) {
		screenshotsRunterladen();
	}

	private static void screenshotsRunterladen() {
		ArrayList<Game> list = DBUtil.getGameList();
		for(Game g:list) {
			try(InputStream in = new URL(g.getThumbnailLink()).openStream()){
			    Files.copy(in, Paths.get("C:\\Users\\Leo\\git\\KioskUDE\\Kiosk\\WebContent\\screenshots\\thumb_"+g.getGameID()+".jpg"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			g = DBUtil.getGameDescriptionByID(g.getGameID());
			try(InputStream in = new URL(g.getScreenshotLink()).openStream()){
			    Files.copy(in, Paths.get("C:\\Users\\Leo\\git\\KioskUDE\\Kiosk\\WebContent\\screenshots\\screenshot_"+g.getGameID()+".jpg"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}