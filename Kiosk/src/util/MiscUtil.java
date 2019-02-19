package util;

import java.nio.file.Paths;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Game;

public class MiscUtil {

	public static void printCurrentDir() {
		System.out.println(
				"Current dir:" + Paths.get("tomcat\\wtpwebapps\\Kiosk\\screenshots").toAbsolutePath().toString());
		System.out.println("using System:" + System.getProperty("user.dir"));

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

	private static void pSteam(String steamID) {
		Game g = SteamUtil.getGameWithDetails(steamID);
		System.out.println(g.getName()+"\t"+g.getGermanDescription()+"\t"+g.getEnglishDescription());
	}
}
