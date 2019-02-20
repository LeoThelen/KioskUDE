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
import util.ScreenshotUtil;
import util.ServletUtil;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet({"/DeleteServlet", "/delete_game"})
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("request DeleteServlet.java");
		ServletUtil.checkAndRefreshLogin(request, response);
		deleteGame(request);
		response.sendRedirect("main");
	}

	private void deleteGame(HttpServletRequest request) {
		String gameID = request.getParameter("delID");
		Game g = new Game();
		g.setGameID(gameID);
		DBUtil.deleteGame(g);
		ScreenshotUtil.deleteThumbnailAndScreenshot(g);
	}



}
