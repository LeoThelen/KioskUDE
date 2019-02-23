package alpha;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;
import util.CookieUtil;

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
		CookieUtil.checkAndRefreshLogin(request, response);
		request.getRequestDispatcher("login.ftl").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * wird nach login aufgerufen
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		attemptToLogin(request, response);
		request.getRequestDispatcher("login.ftl").forward(request, response);		
	}

	private void attemptToLogin(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
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
	}

	private boolean notEmpty(String user, String password) {
		return user != null && password != null;
	}
}
