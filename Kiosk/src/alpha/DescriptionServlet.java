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
     * @see HttpServlet#HttpServlet()
     */
    public DescriptionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String gameID = request.getParameter("id");
		Game g = DBUtil.getGameDescriptionByID(request.getParameter("id"));
		g.setTaglist(DBUtil.getGameTagsByID(request.getParameter("id")));
		LinkedList<Tag> taglist = new LinkedList<>();
		//LinkedList<Tag> taglist = DBUtil.getGameTagsByID(gameID);
		//new
		System.out.println("Loading description page for ID "+request.getParameter("id"));
		//new
		for(int i = 0; i < g.getTaglist().size(); i++) {
			String tagID = g.getTaglist().get(i).getTagID();
			taglist.add(DBUtil.getTagByID(tagID));
		}

		request.setAttribute("taglist", taglist);
		request.setAttribute("game", g);
		request.getRequestDispatcher("description.ftl").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
