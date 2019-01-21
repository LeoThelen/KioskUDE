package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import domain.Game;
import domain.Tag;

class AppTest {
	@Nested
	@DisplayName("Given a Tag with some values")
	class givenTag {
		Tag tag;
		Tag emptyTag;
		String tagID="9999";
		String catID="9998";
		String labelDE="testDE";
		String labelEN="testEN";

		@BeforeEach
		void setUp() {
			tag = new Tag(tagID, catID, labelDE, labelEN);
			emptyTag = new Tag();
		}

		@Test
		@DisplayName("Tagcontent is valid")
		void verifyTagContent() {
			assertEquals(tagID, tag.getTagID(), "TagID is correct");
			assertEquals(catID, tag.getCatID(), "catID is correct");
			assertEquals(labelDE, tag.getLabelDE(), "labelDE is correct");
			assertEquals(labelEN, tag.getLabelEN(), "labelEN is correct");
			
			assertEquals(null, emptyTag.getTagID(), "empty TagID is null");
			assertEquals(null, emptyTag.getCatID(), "empty catID is null");
			assertEquals(null, emptyTag.getLabelDE(), "empty labelDE is null");
			assertEquals(null, emptyTag.getLabelEN(), "empty labelEN is null");
		}
	}
}
