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
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName());
				if (cookie.getName().equals("vrlogin")) {
					request.setAttribute("loggedin", true);
					break;
				}
			}
		}
		if (user != null && password != null) {
			System.out.println(user+"<user password>"+password);
			request.setAttribute("wrongpassword", true);
			request.setAttribute("lastmail", user);
			
			/*if (user.equals("admin@god") && password.equals("0000")) { // TODO increase security
				Cookie loginCookie = new Cookie("vrlogin", user);
				loginCookie.setMaxAge(3600); // expires after 1h
				response.addCookie(loginCookie);
				request.setAttribute("loggedin", true);
			}*/
			if(DBUtil.verifyLogin(user, password)) {
				Cookie loginCookie = new Cookie("vrlogin", user);
				loginCookie.setMaxAge(3600); // expires after 1h
				response.addCookie(loginCookie);
				request.setAttribute("loggedin", true);
			}
		}
		
		request.getRequestDispatcher("login.ftl").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
