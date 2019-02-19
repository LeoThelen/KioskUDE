package alpha;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Game;
import domain.TagCategory;
import util.DBUtil;
import util.MiscUtil;
import util.OculusUtil;
import util.SteamUtil;

/**
 * Servlet implementation class GameFormularServlet
 */
@WebServlet({"/gameFormular", "/import_oculusgo", "/import_steam"})
public class GameFormularServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * wird beim Bearbeiten und bei neuen Drittanbieterspielen aufgerufen
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtil.checkAndRefreshLogin(request, response);
		fillFormWithGameInfosOnEdit(request);
		request.getRequestDispatcher("gameFormular.ftl").forward(request, response);
	}

	private void fillFormWithGameInfosOnEdit(HttpServletRequest request) {
		String editID = request.getParameter("editID");
//		System.out.println("edit Game with ID:\t"+editID);	
		if (exists(editID)) {
			Game g = DBUtil.getGameDescriptionByID(editID);
			request.setAttribute("game", g);
		}
	}

	private boolean exists(String editID) {
		return editID != null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * wird beim import neuer steam/oculus-Spiele aufgerufen
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtil.checkAndRefreshLogin(request, response);
		Game g = null;
		callImportForSteamGames(request);
 		callImportForOculusGames(request);		
		request.getRequestDispatcher("gameFormular.ftl").forward(request, response);
	}

	private void callImportForOculusGames(HttpServletRequest request) {
		Game g;
		String oculusID = request.getParameter("oculusID");
		System.out.println("oculusGameID:\t"+oculusID);
		if (exists(oculusID)) {
			g = OculusUtil.getGameWithDetails(oculusID);
			request.setAttribute("game", g);
		}
	}

	private void callImportForSteamGames(HttpServletRequest request) {
		Game g;
		String steamID = request.getParameter("steamID");
		System.out.println("steamGameID:\t"+steamID);
		if (exists(steamID)) {
			g = SteamUtil.getSteamGameWithDetailsAndTags(steamID);
			request.setAttribute("game", g);
 		}
	}
}
