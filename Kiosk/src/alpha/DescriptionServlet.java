package alpha;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Game;
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
		Game g = SteamUtil.getGameWithDetails(request.getParameter("id"));
		System.out.println(g.getEnglishDescription());
		response.getWriter().append(
				"<h1>&#1575;&#1604;&#1604;&#1607; &#1571;&#1614;&#1603;&#1618;&#1576;&#1614;&#1585;"+g.getName()+
				"</h1><br><div class=\"anima\"><a href=\"TEST\"><button type=\"button\" class=\"btn btn-warning btn-lg\">ID:"+request.getParameter("id")+"</button></a></div>" +
				"<br><img src=\""+g.getScreenshotLink()+"\" alt=\"Screenshot\"><br>"
						+ g.getEnglishDescription());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
