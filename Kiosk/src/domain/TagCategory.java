package domain;

import java.util.ArrayList;

public class TagCategory {
	private String catID;
	private String labelDE;
	private String labelEN;
	private ArrayList<Tag> taglist = new ArrayList<>();

	public TagCategory(String catID, String labelDE, String labelEN, ArrayList<Tag> taglist) {
		this.catID = catID;
		this.labelDE = labelDE;
		this.labelEN = labelEN;
		this.taglist = taglist;
	}

	public String getCatID() {
		return catID;
	}
	
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

	public ArrayList<Tag> getTaglist() {
		return taglist;
	}

	public void setTaglist(ArrayList<Tag> taglist) {
		this.taglist = taglist;
	}

}