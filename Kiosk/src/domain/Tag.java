package domain;

public class Tag {
	
	private String tagID;
	private String tagCat;
	private String tagName;
	
	public Tag() {
		
	}
	
	public Tag(String tagID, String tagName, String tagCat) {
		this.tagID = tagID;
		this.tagName = tagName;
		this.tagCat = tagCat;
	}
	
	public String getTagID() {
		return tagID;
	}
	
	public void setTagID(String tagID) {
		this.tagID = tagID;
	}
	
	public String getTagCat() {
		return tagCat;
	}
	
	public void setTagCat(String tagCat) {
		this.tagCat = tagCat;
	}
	
	public String getTagName() {
		return tagName;
	}
	
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
