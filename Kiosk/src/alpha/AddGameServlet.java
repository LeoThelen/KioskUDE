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
import domain.Tag;
import domain.TagCategory;
import util.DBUtil;
import util.MiscUtil;
import util.ScreenshotUtil;

/**
 * Servlet implementation class AddGame2Servlet
 */
@WebServlet({ "/AddGame2Servlet", "/addGame", "/edit_game" })
public class AddGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddGameServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie loginCookie = MiscUtil.getLoginCookie(request.getCookies());
		if(loginCookie != null) {
			request.setAttribute("loggedin", true);
			loginCookie.setMaxAge(3600); // expires after 1h
			response.addCookie(loginCookie);
		}
		
		Game g = new Game();
		request.setCharacterEncoding("UTF-8"); //Sonderzeichen und Umlaute
		g.setName(request.getParameter("gameTitle"));
		System.out.println("Name von Seite:\t"+request.getParameter("gameTitle"));
		
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
		
		if(request.getParameter("gameID") != null) {	//update game
			g.setGameID(request.getParameter("gameID"));
			DBUtil.updateGame(g);	//TODO
			System.out.println("updated");
		}else {											//add new game
			System.out.println("adding...");
			g = DBUtil.addGame(g);
			
			System.out.println("added"+g.getGameID());
		}
		if (g.getSteamID()!=null) {						//save screenshots locally
			ScreenshotUtil.saveThumbnailAndScreenshot(g);
		}else {
			ScreenshotUtil.saveResizedThumbnailAndScreenshot(g);
		}
		
		request.setAttribute("game", g);
		LinkedList<TagCategory> tagCatList = DBUtil.getTagList();
//		System.out.println("getTaglist success");
		LinkedList<Tag> gameTagList = DBUtil.getGameTagsByID(g.getGameID());//TODO beispiel :(
//		System.out.println("getGameTaglist success");
		
		/**
		 * setzt Haken in die Checkboxen bei belegten Tags 
		 * */
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
		request.setAttribute("tagCats", tagCatList);
		request.getRequestDispatcher("tagFormular.ftl").forward(request, response);
	}

}
