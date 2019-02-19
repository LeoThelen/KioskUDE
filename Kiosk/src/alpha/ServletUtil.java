package alpha;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.MiscUtil;

public class ServletUtil {
	
	public static void checkAndRefreshLogin(HttpServletRequest request, HttpServletResponse response) {
		Cookie loginCookie = getLoginCookie(request.getCookies());
		if(loginCookie != null) {
			request.setAttribute("loggedin", true);
			loginCookie.setMaxAge(3600); // expires after 1h
			response.addCookie(loginCookie);
		}
	}

	public static Cookie getLoginCookie(Cookie cookies[]) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName());
				if (cookie.getName().equals("vrlogin")) {
					return cookie;
				}
			}
		}
		return null;
	}
	
}
