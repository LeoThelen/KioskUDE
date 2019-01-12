package domain;

import java.util.ArrayList;

public class Game {
	private String name;
	private String gameID;
	private ArrayList<Tag> taglist = new ArrayList<>();
	private String thumbnailLink;
	private String screenshotLink;
	private String steamID;
	private String germanDescription="";
	private String englishDescription="";
	private String arabDescription="";
	private String path="";
	private String lastTimeUsed="";
	
	public Game(){

	}
	public Game(String gameID, String name, String thumbnailLink, ArrayList<Tag> taglist){
		this.gameID=gameID;
		this.name=name;
		this.thumbnailLink=thumbnailLink;
		this.taglist=taglist;
	}

	public Game(String steamID){//from SteamUtil
		this.steamID=steamID;
		this.thumbnailLink="https://steamcdn-a.akamaihd.net//steam//apps//"+steamID+"//header.jpg";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGameID() {
		return gameID;
	}
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	public ArrayList<Tag> getTaglist() {	//nur fuer die Uebergabe an Freemarker oder zum kopieren von Kategorisierungen.
		return taglist;
	}
	public void setTaglist(ArrayList<Tag> taglist) {
		this.taglist = taglist;
	}
	
	public String getClasstags() {	//nur fuer die Uebergabe an Freemarker oder zum kopieren von Kategorisierungen.
		if(taglist==null) {
			return null;
		}
		String classtags="";
		for (int i = 0; i < taglist.size(); i++) {
			classtags+=" tag"+taglist.get(i).getTagID();
		}
		return classtags;
	}

	public void addTag(Tag tag) {
		taglist.add(tag);
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
		return steamID==null?"":steamID;
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
		return this.steamID==null ? this.path:"steam://run/"+steamID;
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
