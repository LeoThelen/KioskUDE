package domain;

import java.util.ArrayList;

public class TagList {
	private String tagCat;
	private ArrayList<Tag> tags = new ArrayList<>();
	
	public String getTagCat(){
		return tagCat;
	}
	
	public void setTagCat(String tagCat){
		this.tagCat = tagCat;
	}
	
	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
}
