package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class GameEntry {
	private String name;
	private int id;
	private ArrayList<ArrayList<String>> taglistlist=null;
	private String thumbnailLink;
	private String screenshotLink;
	
	public GameEntry(String name, int id, String altersfreigabe){
		this.name=name;
		this.id=id;
		taglistlist=new ArrayList<>();
		for (int i = 0; i < 1; i++) {	//Anzahl der Kategorien, erstmal nur Alter
			taglistlist.add(new ArrayList<>());
		}
		taglistlist.get(0).add(altersfreigabe);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<ArrayList<String>> getTaglistlist() {	//nur für die Übergabe an Freemarker oder zum kopieren von Kategorisierungen.
		return taglistlist;
	}
	public void setTaglistlist(ArrayList<ArrayList<String>> taglistlist) {
		this.taglistlist = taglistlist;
	}
	
	public String getClasstags() {	//nur für die Übergabe an Freemarker oder zum kopieren von Kategorisierungen.
		String classtags="";
		for (int i = 0; i < taglistlist.size(); i++) {
			for (int j = 0; j < taglistlist.get(i).size(); j++) {
				classtags+=taglistlist.get(i).get(j).replaceAll("\\s+","")+" "; //entfernt whitspace aus dem tag
			}
		}
		return classtags;
	}
	public void addTagToCat(int catno, String tag) {
		while (taglistlist.size()<=catno) {
			taglistlist.add(new ArrayList<>());
		}
		taglistlist.get(catno).add(tag);
	}

	public String getThumbnailLink() {
		return this.thumbnailLink==null ? this.thumbnailLink : "screenshots/sample.png";
	}

	public void setThumbnailLink(String thumbnailLink) {
		this.thumbnailLink = thumbnailLink;
	}

	public String getScreenshotLink() {
		return this.screenshotLink==null ? this.screenshotLink : "screenshots/sample.png";
	}

	public void setScreenshotLink(String screenshotLink) {
		this.screenshotLink = screenshotLink;
	}
		
}
