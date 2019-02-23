package alpha;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CookieUtil;
import util.DBUtil;

/**
 * Servlet implementation class PasswordServlet
 */
@WebServlet({"/PasswordServlet", "/edit_password"})
public class PasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CookieUtil.checkAndRefreshLogin(request, response);
		String newPassword = request.getParameter("newPassword");
		DBUtil.updatePassword("admin", newPassword, "saaaaalt32483641682");
		response.sendRedirect("login");
	}

}
