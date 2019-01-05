package domain;

public class Tag {
	
	private long tagID;
	private String tagCat;
	private String tagName;
	
	public Tag() {
		
	}
	
	public long getTagID() {
		return tagID;
	}
	
	public void setTagID(long tagID) {
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
