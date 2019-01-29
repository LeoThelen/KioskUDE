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
 * Servlet implementation class AddGameFormularServlet
 */
@WebServlet("/addGameFormular")
public class AddGameFormularServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGameFormularServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * doGetMethode, falls Spiel ohne Vorverarbeitung durch Steam oder Oculus erzeugt werden soll.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.getRequestDispatcher("entwurfAddGame.ftl").forward(request, response);

	}
	/**
	 * TODO doPostMethode, falls Spiel schon durch Steam oder Oculus vorverarbeitet wurde oder ge�ndert werden soll. 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO request.setAttribute(game und gametags) oder so, evtl. nochmal eigene DB-Methode notwendig...

	}
}
