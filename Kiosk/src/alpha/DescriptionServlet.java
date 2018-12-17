package alpha;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("&#1575;&#1604;&#1604;&#1607; &#1571;&#1614;&#1603;&#1618;&#1576;&#1614;&#1585;"+request.getContextPath()+
				"<br>ID:"+request.getParameter("id")+
				"<br><img src=\""+"https://steamcdn-a.akamaihd.net//steam//apps//503630//ss_67c274c2e497792d210a7a027f5ad58c56d37187.600x338.jpg"+"\" alt=\"Screenshot\">");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
