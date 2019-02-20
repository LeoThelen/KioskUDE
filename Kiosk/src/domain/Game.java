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
	private String path=null;
	private String lastTimeUsed=null;
	
	/**
	 * Konstruktor für ein leeres Gameobjekt
	 */
	public Game(){

	}

	/**
	 * Konstruktor für GameObjekt. Enthält alle nötigen Informationen für die Übersichtsliste.
	 * 
	 * @param gameID wird von der Datenbank erzeugt.
	 * @param name
	 * @param thumbnailLink
	 * @param taglist
	 */
	public Game(String gameID, String name, String thumbnailLink, LinkedList<Tag> taglist){
		this.gameID=gameID;
		this.name=name;
		this.thumbnailLink=thumbnailLink;
		this.taglist=taglist;
	}

	/**
	 * Konstruktor für Gameobjekt. Erzeugt automatisch den Thumbnaillink aus der SteamID.
	 * 
	 * @param steamID wird als String erwartet.
	 */
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
	
	/**
	 * nur fuer die Uebergabe an Freemarker oder zum kopieren von Kategorisierungen.
	 */
	public LinkedList<Tag> getTaglist() {	
		return taglist;
	}
	
	public void setTaglist(LinkedList<Tag> taglist) {
		this.taglist = taglist;
	}
	
	/**
	 * @return Klassennamen für die Isotope Sortierung aus den Tags. 
	 * nur fuer die Uebergabe an Freemarker oder zum kopieren von Kategorisierungen.
	 */
	public String getClasstags() {	
		if(taglist==null) {
			return null;
		}
		String classtags="";
		for (int i = 0; i < taglist.size(); i++) {
			classtags+=" tag"+taglist.get(i).getTagID();
		}
		return classtags;
	}

	/**
	 * Markiert dieses GameObjekt mit einem Gametag. Wenn Gametag schon vorhanden, passiert nichts.
	 * @param tag
	 */
	public void addTag(Tag tag) {
		for (Tag gTag : taglist) {	//wenn tag in liste, skip
			if (gTag.getTagID().equals(tag.getTagID())) {
				return;
			}
		}
		taglist.add(tag);
		System.out.println("Added tag " + tag.getTagID() + " to Game.");
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

	/**
	 * @return Spielpfad oder Ausführungspfad von Steamspielen (für Protocol Handler) 
	 */
	public String getPath() {
		return this.steamID==null ? this.path:"steam://run/"+steamID;
	}

	public void setPath(String path) {
		//Anführungsstriche entfernen erhöht Sicherheit
		if(path!=null){
			this.path = path.replace("\"", "");
		}
	}

	/**
	 * @return Zeit des letzten Spielstarts. Wenn noch nie gestartet dann Installationszeitpunkt.
	 */
	public String getLastTimeUsed() {
		return this.lastTimeUsed==null?Long.toString(System.currentTimeMillis()):this.lastTimeUsed;
	}

	public void setLastTimeUsed(String lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}
}
