package alpha;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Game;
import util.DBUtil;
import util.SteamUtil;

/**
 * Servlet implementation class SelectionServlet
 */
@WebServlet("/main")
public class SelectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try (Connection con = DBUtil.MariaDBConnection_connect()) {
			DBUtil.MySQLConnection_close(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<Game> list = DBUtil.getGameList();
		request.setAttribute("gamelist", list);
//		request.setAttribute("taglistlist", list.get(0).getTaglistlist());
		request.getRequestDispatcher("selection.ftl").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
