package alpha;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.TagCategory;
import util.DBUtil;

/**
 * Servlet implementation class TagFormularServlet
 */
@WebServlet({"/TagFormularServlet", "/addTag"})
public class TagFormularServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagFormularServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedList<TagCategory> tagList = DBUtil.getTagList();
		request.setAttribute("tagCats", tagList);

		// TODO Auto-generated method stub
		request.getRequestDispatcher("tagFormular.ftl").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String tagid = request.getParameter("tagid");
		String action = request.getParameter("action");
		if(tagid!=null) {
			System.out.println(tagid + action);
			response.getWriter().append(tagid);
		}
		
		
		
	}

}
