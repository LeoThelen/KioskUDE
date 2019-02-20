package domain;

import java.util.LinkedList;

public class TagCategory {
	private String catID;
	private String labelDE;
	private String labelEN;
	private LinkedList<Tag> taglist = new LinkedList<>();

	public TagCategory(String catID, String labelDE, String labelEN, LinkedList<Tag> taglist) {
		this.catID = catID;
		this.labelDE = labelDE;
		this.labelEN = labelEN;
		this.taglist = taglist;
	}

	public String getCatID() {
		return catID;
	}
	
	/**für Isotope html-filtergroup-attribut*/
	public String getFilterGroup() {
		return "filtergroup"+catID;
	}

	public void setCatID(String catID) {
		this.catID = catID;
	}

	public String getLabelDE() {
		return labelDE;
	}

	public void setLabelDE(String labelDE) {
		this.labelDE = labelDE;
	}

	public String getLabelEN() {
		return labelEN;
	}

	public void setLabelEN(String labelEN) {
		this.labelEN = labelEN;
	}

	public LinkedList<Tag> getTaglist() {
		return taglist;
	}

	public void setTaglist(LinkedList<Tag> taglist) {
		this.taglist = taglist;
	}

}