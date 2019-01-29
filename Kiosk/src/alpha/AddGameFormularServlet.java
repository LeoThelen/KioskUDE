package alpha;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Game;
import domain.TagCategory;
import util.DBUtil;
import util.OculusUtil;
import util.SteamUtil;

/**
 * Servlet implementation class AddGameFormularServlet
 */
@WebServlet({"/addGameFormular","/import_oculusgo","/import_steam"})
public class AddGameFormularServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGameFormularServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * doGetMethode, falls Spiel ohne Vorverarbeitung durch Steam oder Oculus erzeugt werden soll.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedList<TagCategory> tagList = DBUtil.getTagList();
		request.setAttribute("filterOffen", false);
		request.setAttribute("tagCats", tagList);
		
		String titel = request.getParameter("gameTitle");
		String path = request.getParameter("gamePath");
		String germanDescription = request.getParameter("germanDescription");
		String englishDescription = request.getParameter("englishDescription");
		String thumbnailPath = request.getParameter("thumbnail");
		String screenshotPath = request.getParameter("screenshot");
		if (titel != null && path != null) {
			Game g = new Game();
			g.setName(titel);
			g.setPath(path);
			g.setEnglishDescription(englishDescription);
			g.setGermanDescription(germanDescription);
			g.setThumbnailLink(thumbnailPath);
			g.setScreenshotLink(screenshotPath);
			//DBUtil.addGame(g);
			request.setAttribute("filterOffen", true);
			//request.setAttribute("game", g);
			//TODO Tags in DB speichern
		}
		
		request.getRequestDispatcher("entwurfAddGame.ftl").forward(request, response);

	}

	/**
	 * TODO doPostMethode, falls Spiel schon durch Steam oder Oculus vorverarbeitet wurde oder geändert werden soll. 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String steamID = request.getParameter("steamID");
		String oculusID = request.getParameter("oculusID");
		System.out.println("steamGameID:\t"+steamID);
		System.out.println("oculusGameID:\t"+oculusID);
		
		if (steamID!=null) {
			Game g = SteamUtil.getSteamGameWithAllDetails(steamID);
			request.setAttribute("game", g);
			
		}else {
			if (oculusID != null) {
				Game g = OculusUtil.getGameWithDetails(oculusID);
				request.setAttribute("game", g);
			}
		}
		
		// TODO request.setAttribute(game und gametags) oder so, evtl. nochmal eigene DB-Methode notwendig...

		doGet(request, response);
	}
}
