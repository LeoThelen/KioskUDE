package domain;

import java.util.ArrayList;

public class Game {
	private String name;
	private String gameID;
	private ArrayList<ArrayList<String>> taglistlist=null;
	private String thumbnailLink;
	private String screenshotLink;
	private String steamID;
	private String germanDescription;
	private String englishDescription;
	private String arabDescription;
	private String path;
	private String lastTimeUsed;
	
	
	
	
	
	
	
	public Game(String steamID){//from SteamUtil
		this.steamID=steamID;
		this.gameID=steamID;//TODO: braucht man diese Zeile noch oder haben wir schon eine DB?
		this.thumbnailLink="https://steamcdn-a.akamaihd.net//steam//apps//"+steamID+"//header.jpg";
	}
	
	public Game(String name, String gameID, String altersfreigabe){
		this.name=name;
		this.gameID=gameID;
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
	public String getId() {
		return gameID;
	}
	public void setId(String gameID) {
		this.gameID = gameID;
	}
	public ArrayList<ArrayList<String>> getTaglistlist() {	//nur für die Übergabe an Freemarker oder zum kopieren von Kategorisierungen.
		return taglistlist;
	}
	public void setTaglistlist(ArrayList<ArrayList<String>> taglistlist) {
		this.taglistlist = taglistlist;
	}
	
	public String getClasstags() {	//nur für die Übergabe an Freemarker oder zum kopieren von Kategorisierungen.
		if(taglistlist==null) {
			return null;
		}
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
		return this.thumbnailLink==null ? "screenshots/sample.png":this.thumbnailLink;
	}

	public void setThumbnailLink(String thumbnailLink) {
		this.thumbnailLink = thumbnailLink;
	}

	public String getScreenshotLink() {
		return this.screenshotLink==null ? "screenshots/sample.png":this.screenshotLink;
	}

	public void setScreenshotLink(String screenshotLink) {
		this.screenshotLink = screenshotLink;
	}

	public String getSteamID() {
		return steamID;
	}

	public void setSteamID(String steamID) {
		this.steamID = steamID;
	}

	public String getGermanDescription() {
		return germanDescription;
	}

	public void setGermanDescription(String germanDescription) {
		this.germanDescription = germanDescription;
	}

	public String getEnglishDescription() {
		return englishDescription;
	}

	public void setEnglishDescription(String englishDescription) {
		this.englishDescription = englishDescription;
	}

	public String getArabDescription() {
		return arabDescription;
	}

	public void setArabDescription(String arabDescription) {
		this.arabDescription = arabDescription;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLastTimeUsed() {
		return lastTimeUsed;
	}

	public void setLastTimeUsed(String lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}
	
	
}
