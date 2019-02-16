package alpha;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Game;
import util.DBUtil;
import util.MiscUtil;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet({"/DeleteServlet", "/delete_game"})
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		System.out.println("request DeleteServlet.java");
		Cookie loginCookie = MiscUtil.getLoginCookie(request.getCookies());
		if(loginCookie != null) {
			loginCookie.setMaxAge(3600); // expires after 1h
			response.addCookie(loginCookie);
		}

		String gameID = request.getParameter("delID");
		Game g = new Game();
		g.setGameID(gameID);
		DBUtil.deleteGame(g);
		File f = new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\thumb_" + g.getGameID() + ".jpg");
		if (!f.exists()) {
			f.delete();
		}
		f = new File("tomcat\\wtpwebapps\\Kiosk\\screenshots\\screenshot_" + g.getGameID() + ".jpg");
		if (!f.exists()) {
			f.delete();
		}
		try {
			response.sendRedirect("main");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
