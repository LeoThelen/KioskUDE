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

/**
 * Servlet implementation class AddGame2Servlet
 */
@WebServlet({"/AddGame2Servlet", "/addGame"})
public class AddGame2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGame2Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			//Game gnew = DBUtil.addGame(g);
			//request.setAttribute("game", gnew);
			LinkedList<TagCategory> tagList = DBUtil.getTagList();
			request.setAttribute("tagCats", tagList);
			request.getRequestDispatcher("tagFormular.ftl").forward(request, response);
		}else {
			//TODO zur startseite FEHLER
		}
		
	}

}
