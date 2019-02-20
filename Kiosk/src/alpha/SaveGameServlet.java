package alpha;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Game;
import domain.Tag;
import domain.TagCategory;
import util.DBUtil;
import util.ScreenshotUtil;
import util.ServletUtil;

/**
 * Servlet implementation class AddGameServlet
 * Fügt Spiele hinzu oder speichert deren Änderungen. Es folgt eine Weiterleitung zum Tagformular.
 */
@WebServlet({ "/AddGameServlet", "/save_game",  "/add_game", "/edit_game" })
public class SaveGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtil.checkAndRefreshLogin(request, response);
		request.setCharacterEncoding("UTF-8"); //Sonderzeichen und Umlaute
		System.out.println("SaveGameServlet."); 
		
		Game g = getValuesFromGameForm(request);		
		g = saveGame(g);
		request.setAttribute("game", g);
		LinkedList<TagCategory> tagCatList = tickCheckboxesWithActiveTags(g);
		request.setAttribute("tagCats", tagCatList);
		request.getRequestDispatcher("tagFormular.ftl").forward(request, response);
	}


	private LinkedList<TagCategory> tickCheckboxesWithActiveTags(Game g) {
		LinkedList<TagCategory> tagCatList = DBUtil.getTagCategoryList();
		LinkedList<Tag> gameTagList = DBUtil.getGameTagsByGameID(g.getGameID());
		for(int i=0; i < tagCatList.size(); i++) {
			for(int j = 0; j < tagCatList.get(i).getTaglist().size(); j++) {
				for (Tag gtag : gameTagList) {
					if(gtag.getTagID().equals(tagCatList.get(i).getTaglist().get(j).getTagID())) {
						tagCatList.get(i).getTaglist().get(j).setChecked(true);
					}
				}
			}
		}
		return tagCatList;
	}

	private Game saveGame(Game g) {
		if(g.getGameID() != null) {	//update game
			System.out.println("updating Game " + g.getGameID());
			DBUtil.updateGame(g);
			System.out.println("updated");
		}else {
			System.out.println("adding new Game...");
			g = DBUtil.addGame(g);			
			System.out.println("added" + g.getGameID());
		}
		if (g.getSteamID()!=null) {						//save screenshots locally
			ScreenshotUtil.saveThumbnailAndScreenshot(g);
		}else {
			ScreenshotUtil.saveResizedThumbnailAndScreenshot(g);
		}
		return g;
	}

	private Game getValuesFromGameForm(HttpServletRequest request) {
		Game g = new Game();
		g.setName(request.getParameter("gameTitle"));
		System.out.println("Name von App:\t"+request.getParameter("gameTitle"));
		if(request.getParameter("gameID") != null) {	//update game
			g.setGameID(request.getParameter("gameID"));
		}
		if(request.getParameter("steamID") != null) {
			g.setSteamID(request.getParameter("steamID"));
			System.out.println("SteamID von Seite:\t"+request.getParameter("steamID"));
		}
		if(request.getParameter("oculusID") != null) {
			g.setOculusID(request.getParameter("oculusID"));
			System.out.println("oculusID von Seite:\t"+request.getParameter("oculusID"));
		}
		if(request.getParameter("gamePath") != null) {
			g.setPath(request.getParameter("gamePath"));
		}
		g.setGermanDescription(request.getParameter("germanDescription"));
		g.setEnglishDescription(request.getParameter("englishDescription"));
		g.setThumbnailLink(request.getParameter("thumbnailLink"));
		g.setScreenshotLink(request.getParameter("screenshotLink"));
		return g;
	}
}
