package alpha;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.GameEntry;

/**
 * Servlet implementation class SelectionServlet
 */
@WebServlet("/main")
public class SelectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedList<GameEntry> list = createSampleList();		
		request.setAttribute("gamelist", list);
		request.setAttribute("taglistlist", list.get(0).getTaglistlist());
		request.getRequestDispatcher("selection.ftl").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private LinkedList<GameEntry> createSampleList(){
		LinkedList<GameEntry> list = new LinkedList<>();
		list.add(new GameEntry("BeatSaber", 12344, "unter12"));
		list.add(new GameEntry("Zahnputzsimulator VR", 12345, "unter12"));
		list.add(new GameEntry("Schattenwelt", 12346, "16+"));
		list.getLast().addTagToCat(1, "Simulation");
		list.getLast().setScreenshotLink("https://steamcdn-a.akamaihd.net//steam//apps//503630//ss_67c274c2e497792d210a7a027f5ad58c56d37187.600x338.jpg");
		list.add(new GameEntry("Lorem", 897641, "unter12"));
		list.getLast().addTagToCat(1, "Simulation");
		list.add(new GameEntry("Ipsum", 873186, "unter12"));
		list.getLast().addTagToCat(1, "Simulation");
		list.add(new GameEntry("Dolor", 183736, "unter12"));
		list.getLast().addTagToCat(1, "Simulation");
		list.add(new GameEntry("Sit amet", 726781, "unter12"));
		list.getLast().addTagToCat(1, "Simulation");
		list.add(new GameEntry("Lorem", 897641, "unter12"));
		list.getLast().addTagToCat(1, "Simulation");
		list.add(new GameEntry("Ipsum", 873186, "unter12"));
		list.getLast().addTagToCat(1, "Simulation");
		list.add(new GameEntry("Dolor", 183736, "unter12"));
		list.getLast().addTagToCat(1, "Simulation");
		list.add(new GameEntry("Sit amet", 726781, "unter12"));
		list.getLast().addTagToCat(1, "Simulation");
		
		
		
		
		return list;
	}
}
