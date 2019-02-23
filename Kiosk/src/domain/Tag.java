package domain;

public class Tag {

	private String tagID;
	private String catID;
	private String labelDE;
	private String labelEN;
	private boolean checked=false;
	
	/**
	 * leerer Tag-Konstruktor. Setzt checked auf false.
	 */
	public Tag() {

	}

	/**
	 * Konstruktor
	 * @param tagID
	 */
	private Tag(String tagID) {
		this.tagID = tagID;
	}
	
	public static Tag FromTagID(String tagID) {
		return new Tag(tagID);
	}

	/**
	 * Konstruktor
	 * @param tagID
	 * @param catID
	 * @param labelDE deutsche Beschreibung
	 * @param labelEN englische Beschreibung
	 */
	public Tag(String tagID, String catID, String labelDE, String labelEN) {
		this.tagID = tagID;
		this.catID = catID;
		this.labelDE = labelDE;
		this.labelEN = labelEN;
	}

	public String getTagID() {
		return tagID;
	}
	
	/**
	 * Zum setzen des html-Attributs
	 * @return data-attribut für die Filter
	 * Beispiel mit Freemarker und Isotope: data-filter=".${tag.filter}"
	 */
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

	/**
	 * für Tagformular
	 * @return checked, wenn der Tag für das Spiel aktiv ist.
	 * ansonsten leerer String. 
	 */
	public String getCheckedString() {
		return checked?"checked":"";
	}

	/**
	 * für Tagformular.
	 * @param checked true, falls der Tag für das Spiel aktiv ist.
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
