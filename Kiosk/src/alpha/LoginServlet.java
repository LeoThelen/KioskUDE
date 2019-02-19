package alpha;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("user");
		String password = request.getParameter("password");
		ServletUtil.checkAndRefreshLogin(request, response);
		if (notEmpty(username, password)) {
			if(DBUtil.verifyLogin(username, password)) {
				System.out.println("Login verified.");
				Cookie loginCookie = new Cookie("vrlogin", username);
				loginCookie.setMaxAge(3600); // expires after 1h
				response.addCookie(loginCookie);
				request.setAttribute("loggedin", true);
			}else {
				request.setAttribute("wrongpassword", true);
				request.setAttribute("username", username);
			}
		}
		
		request.getRequestDispatcher("login.ftl").forward(request, response);
	}

	private boolean notEmpty(String user, String password) {
		return user != null && password != null;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * wird nach logout aufgerufen
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
