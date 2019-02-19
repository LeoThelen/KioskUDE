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
import util.DBUtil;
import util.SteamUtil;

/**
 * Servlet implementation class DescriptionServlet
 */
@WebServlet("/description")
public class DescriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		provideGameDescription(request);
		request.getRequestDispatcher("description.ftl").forward(request, response);
	}

	private void provideGameDescription(HttpServletRequest request) {
		String gameID = request.getParameter("id");
		System.out.println("Loading description page for ID " + gameID);

		Game g = DBUtil.getGameDescriptionByID(gameID);
		request.setAttribute("game", g);
		
		provideTagInformation(request, g);
	}

	private void provideTagInformation(HttpServletRequest request, Game g) {
		LinkedList<Tag> gameTags = DBUtil.getGameTagsByID(request.getParameter("id"));
		g.setTaglist(gameTags);
		request.setAttribute("taglist", g.getTaglist());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
