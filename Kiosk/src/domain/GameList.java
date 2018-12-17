package domain;

import java.util.LinkedList;

public class GameList {
	private LinkedList<GameEntry> list = null;

	public GameList() {
		list = createSampleList();
		System.out.println("marvelous");
	}
	
	public LinkedList<GameEntry> getList() {
		return list;
	}

	public void setList(LinkedList<GameEntry> list) {
		this.list = list;
	}

	private LinkedList<GameEntry> createSampleList(){
		LinkedList<GameEntry> list = new LinkedList<>();
		list.add(new GameEntry("BeatSaber", 12344, "unter12"));
		list.add(new GameEntry("Zahnputzsimulator VR", 12345, "unter12"));
		list.add(new GameEntry("Schattenwelt", 12346, "16+"));
		list.getLast().addTagToCat(1, "Simulation");
		list.getLast().setScreenshotLink("https://steamcdn-a.akamaihd.net//steam//apps//503630//ss_67c274c2e497792d210a7a027f5ad58c56d37187.600x338.jpg");
		return list;
	}
}
