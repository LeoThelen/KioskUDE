package util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	/**
	 * überprüft anhand der Cookies vom Clientrequest, ob man eingeloggt ist
	 * und setzt dessen verbleibende Loginzeit in der response wieder auf 1 Stunde hoch.
	 * */
	public static void checkAndRefreshLogin(HttpServletRequest request, HttpServletResponse response) {
		Cookie loginCookie = getCookieByName(request.getCookies(), "vrlogin");
		if(loginCookie != null) {
			request.setAttribute("loggedin", true);
			loginCookie.setMaxAge(3600); // expires after 1h
			response.addCookie(loginCookie);
		}
	}

	/**
	 * gibt Cookie mit bestimmten Namen zurück, wenn dies in den übergebenen Cookies ist.
	 * */
	public static Cookie getCookieByName(Cookie cookies[], String cookiename) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName());
				if (cookie.getName().equals(cookiename)) {
					return cookie;
				}
			}
		}
		return null;
	}
	
}
