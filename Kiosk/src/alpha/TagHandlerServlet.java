package alpha;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.TagCategory;
import util.DBUtil;
import util.MiscUtil;

/**
 * Servlet implementation class TagFormularServlet
 */
@WebServlet({ "/TagFormularServlet", "/addTag" })
public class TagHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtil.checkAndRefreshLogin(request, response);
		request.getRequestDispatcher("tagFormular.ftl").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtil.checkAndRefreshLogin(request, response);
		addOrDeleteTagFromGame(request, response);
	}

	private void addOrDeleteTagFromGame(HttpServletRequest request, HttpServletResponse response) throws IOException { //TODO refactor
		String tagID = request.getParameter("tagID");
		String gameID = request.getParameter("gameID");
		String action = request.getParameter("action");
		if (tagID != null && gameID != null) {
			if (action.equals("add")) {
				DBUtil.addGameTagByID(gameID, tagID);
				response.getWriter().append("Tag (ID: " + tagID + ") dem Spiel (ID: " + gameID + ") hinzugef&uuml;gt.");
			}
			if (action.equals("delete")) {
				DBUtil.deleteGameTagByID(gameID, tagID);
				response.getWriter().append("Tag (ID: " + tagID + ") von dem Spiel (ID: " + gameID + ") entfernt.");
			}
		}
	}
}