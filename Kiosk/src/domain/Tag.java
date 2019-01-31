package domain;

public class Tag {

	private String tagID;
	private String catID;
	private String labelDE;
	private String labelEN;
	private boolean checked=false;
	
	public Tag() {

	}

	public Tag(String tagID) {
		this.tagID = tagID;
	}

	public Tag(String tagID, String catID, String labelDE, String labelEN) {
		this.tagID = tagID;
		this.catID = catID;
		this.labelDE = labelDE;
		this.labelEN = labelEN;
	}

	public String getTagID() {
		return tagID;
	}
	public String getFilter() {
		return "tag"+tagID;
	}

	public void setTagID(String tagID) {
		this.tagID = tagID;
	}

	public String getCatID() {
		return catID;
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

	public boolean isChecked() {
		return checked;
	}

	public String getCheckedString() {
		return checked?"checked":"";
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
