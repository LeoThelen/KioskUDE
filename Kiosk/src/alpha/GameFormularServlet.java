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
 * Servlet implementation class GameFormularServlet
 */
@WebServlet({"/gameFormular", "/import_oculusgo", "/import_steam"})
public class GameFormularServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameFormularServlet() {
        super();
    }

	/**
	 * doGetMethode, falls Spiel ohne Vorverarbeitung durch Steam oder Oculus erzeugt werden soll.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String editID = request.getParameter("editID");
		System.out.println("editGameID:\t"+editID);	

		request.setAttribute("action", "addGame");
		if (editID != null) {
			Game g = null;
			g = DBUtil.getGameDescriptionByID(editID);
			request.setAttribute("game", g);
			request.setAttribute("action", "edit_game");

		}
		request.getRequestDispatcher("gameFormular.ftl").forward(request, response);
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

		Game g = null;
 		if (steamID!=null) {
			g = SteamUtil.getSteamGameWithDetailsAndTags(steamID);
			request.setAttribute("game", g);
 		}
 		if (oculusID != null) {
			g = OculusUtil.getGameWithDetails(oculusID);
			request.setAttribute("game", g);
		}		
		request.setAttribute("action", "addGame");
		request.getRequestDispatcher("gameFormular.ftl").forward(request, response);
	}
}
