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

	
	public static void main(String[] args) {
//		p("451520");//theBlu
//		p("397750");//Guided Meditation VR
//		p("450390");//The Lab
//		p("620980");//Beat Saber
//		p("548010");//3D Organon VR Anatomy
//		p("550520");//B. Braun Future Operating Room
//		p("566580");//The Jigsaw Puzzle Room
//		p("531990");//Egg Time
//		p("348250");//Google Earth VR
//		p("490840");//Gnomes & Goblins (preview)
//		p("787790");//Epic Roller Coasters
//		p("804490");//Creed: Rise to Glory
//		p("406860");//Blind [USK 16]
//		p("448280");//Job Simulator

		pOculus("890562797701371");
		pOculus("2252817104759749");
		pOculus("2084588764916379");
		pOculus("1152440564774310");
		pOculus("1064866736899927");
		pOculus("967457083325115");
		pOculus("1092021544167262");
		pOculus("1692021557498395");
		pOculus("878262692296965");
		
	}

	private static void pOculus(String oculusID) {
		Game g = OculusUtil.getGameWithDetails(oculusID);
		System.out.println(g.getName()+"\n"+g.getGermanDescription()+"\n"+g.getEnglishDescription());
	}

	private static void p(String steamID) {
		Game g = SteamUtil.getGameWithDetails(steamID);
		System.out.println(g.getName()+"\t"+g.getGermanDescription()+"\t"+g.getEnglishDescription());
	}

}
