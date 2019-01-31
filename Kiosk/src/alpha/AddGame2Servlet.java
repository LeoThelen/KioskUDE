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

/**
 * Servlet implementation class AddGame2Servlet
 */
@WebServlet({ "/AddGame2Servlet", "/addGame" })
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Game g = new Game();
		g.setName(request.getParameter("gameTitle"));
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
		g.setThumbnailLink(request.getParameter("thumbnail"));
		g.setScreenshotLink(request.getParameter("screenshot"));
		System.out.println("62");
		if(request.getParameter("gameID") != null) {
			g.setGameID(request.getParameter("gameID"));
			System.out.println("updating...");
			DBUtil.updateGame(g);	//TODO
			System.out.println("updated");
		}else {
			System.out.println("adding...");
			g = DBUtil.addGame(g);
			System.out.println("added"+g.getGameID());
		}
		
		request.setAttribute("game", g);
		LinkedList<TagCategory> tagCatList = DBUtil.getTagList();
//		System.out.println("getTaglist success");
		LinkedList<Tag> gameTagList = DBUtil.getGameTagsByID(g.getGameID());//TODO beispiel :(
//		System.out.println("getGameTaglist success");
		for(int i=0; i < tagCatList.size(); i++) {
			for(int j = 0; j < tagCatList.get(i).getTaglist().size(); j++) {
//				System.out.println("i:"+i+"\tj:"+j);
				for (Tag gtag : gameTagList) {
//					System.out.println("i:"+i+"\tj:"+j);
					if(gtag.getTagID().equals(tagCatList.get(i).getTaglist().get(j).getTagID())) {
						tagCatList.get(i).getTaglist().get(j).setChecked(true);
					}
				}
			}
		}
		System.out.println("honda");
		request.setAttribute("tagCats", tagCatList);
		request.getRequestDispatcher("tagFormular.ftl").forward(request, response);
	}

}
