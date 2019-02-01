package domain;

import java.util.LinkedList;

public class Game {
	private String name=null;
	private String gameID=null;
	private LinkedList<Tag> taglist = new LinkedList<>();
	private String thumbnailLink;
	private String screenshotLink;
	private String steamID=null;
	private String oculusID=null;
	
	private String germanDescription=null;
	private String englishDescription=null;
	private String arabDescription=null;
	private String path=null;
	private String lastTimeUsed=null;
	
	public Game(){

	}
	public Game(String gameID, String name, String thumbnailLink, LinkedList<Tag> taglist){
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
	public LinkedList<Tag> getTaglist() {	//nur fuer die Uebergabe an Freemarker oder zum kopieren von Kategorisierungen.
		return taglist;
	}
	public void setTaglist(LinkedList<Tag> taglist) {
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
		//TODO testen, ob Tag schon in Liste
		for (Tag gTag : taglist) {
			if (gTag.getTagID().equals(tag.getTagID())) {
				return;
			}
		}
		taglist.add(tag);
	}

	public String getThumbnailLink() {
		return this.thumbnailLink;
	}

	public void setThumbnailLink(String thumbnailLink) {
		this.thumbnailLink = thumbnailLink;
	}

	public String getScreenshotLink() {
		return this.screenshotLink;
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
	public String getOculusID() {
		return oculusID;
	}
	public void setOculusID(String oculusID) {
		this.oculusID = oculusID;
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
		return this.lastTimeUsed==null?this.lastTimeUsed:Long.toString(System.currentTimeMillis());
	}

	public void setLastTimeUsed(String lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

	
}
