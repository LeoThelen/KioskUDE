package alpha;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.TagCategory;
import util.DBUtil;

/**
 * Servlet implementation class TagFormularServlet
 */
@WebServlet({ "/TagFormularServlet", "/addTag" })
public class TagHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TagHandlerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		LinkedList<TagCategory> tagList = DBUtil.getTagList();
//		request.setAttribute("tagCats", tagList);

		request.getRequestDispatcher("tagFormular.ftl").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String tagID = request.getParameter("tagID");
		String action = request.getParameter("action");
		String gameID = request.getParameter("gameID");
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