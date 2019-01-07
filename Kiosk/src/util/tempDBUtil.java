package util;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import domain.Game;
import domain.GameList;
import domain.Tag;
import domain.TagList;
/*
 * Funktionen der Klasse:
 * loadDriver();
 * 
 * addGame(Game g);
 * addGameTags; (private methode in addGame aufgerufen)
 * readGameByID(String ID);
 * readGameTagByID(String gameID); (private methode in readGameByID aufgerufen)
 * readAllGames(); liest gameID, name, thumbnail
 * deleteGame(Game g);
 * updateGame(Game g);
 * 
 * addTag(Tag t);
 * readTagByID(long ID);
 * readAllTags();
 * readAllTagsOfCat(String cat);
 * deleteTag(Tag t);
 * updateTag(Tag t);
 */
public class tempDBUtil {

	private static String connect = "jdbc:sqlite:C:\\Users\\suzan\\git\\KioskUDE\\Kiosk\\database.db";
    private static String insert = "";
    private static String query = "";

	public static void main(String[] args) {
		Game g1 = new Game();
		g1.setId("8");
		g1.setName("TestDerTags");
		
		Tag t1 = new Tag("1","12","alter");
		Tag t2 = new Tag("2","16","alter");
		Tag t3 = new Tag("3","Entspannung","genre");
		
		ArrayList<String> alter = new ArrayList();
		alter.add(t1.getTagName());
		alter.add(t2.getTagName());
		ArrayList<String> genre = new ArrayList();
		genre.add(t3.getTagName());
		ArrayList<ArrayList<String>> taglistlist = new ArrayList();
		taglistlist.add(alter);
		taglistlist.add(genre);
		g1.setTaglistlist(taglistlist);
		System.out.println("Davor:");
		for(int i = 0; i < g1.getTaglistlist().size(); i++) {
			System.out.println("Test");
			for(int j = 0; j < g1.getTaglistlist().get(i).size(); j++) {
				System.out.println(g1.getTaglistlist().get(i).get(j));
			}
		}
		//addGame(g1);
		//readGameByID("8");
		System.out.println("Danach:");
		for(int i = 0; i < g1.getTaglistlist().size(); i++) {
			System.out.println("Test");
			for(int j = 0; j < g1.getTaglistlist().get(i).size(); j++) {
				System.out.println(readGameByID("8").getTaglistlist().get(i).get(j));
			}
		}
		
	}
	
	public static void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	//Game methoden
	public static void addGame(Game g) {
		insert = "INSERT INTO GAMES(gameID, name, thumbnailLink, screenshotLink, steamID, germanDescription, englishDescription, path, lastTimeUsed)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(insert)){
			pstmt.setString(1, g.getID());
			pstmt.setString(2, g.getName());
			pstmt.setString(3, g.getThumbnailLink());
			pstmt.setString(4, g.getScreenshotLink());
			pstmt.setString(5, g.getSteamID());
			pstmt.setString(6, g.getGermanDescription());
			pstmt.setString(7, g.getEnglishDescription());
			pstmt.setString(8, g.getPath());
			pstmt.setString(9, g.getLastTimeUsed());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < g.getTaglistlist().size(); i++) {
			for(int j = 0; j < g.getTaglistlist().get(i).size(); j++) {
				addGameTagByID(g.getID(), g.getTaglistlist().get(i).get(j));
			}
		}
	}
	
	private static void addGameTagByID(String gameID, String tagName) {
		query = "SELECT tagID FROM tags WHERE tagName = ?";
		String tagID = "";
		
		try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(query)){
			pstmt.setString(1, tagName);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next() == true) {
				tagID = rs.getString("tagID");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		insert = "INSERT INTO gametags(gameID, tagID) values(?,?)";
		try (Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(insert)){
			pstmt.setString(1, gameID);
			pstmt.setString(2, tagID);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Game readGameByID(String ID) {
		query = "SELECT * FROM games WHERE gameID = ?";
    	Game game = null;
    	
    	try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, ID);
			ResultSet rs = pstmt.executeQuery();
    		if(rs.next() == true) {
    			game = new Game();
    			game.setID(rs.getString("gameID"));
    			game.setName(rs.getString("name"));
    			game.setThumbnailLink(rs.getString("thumbnailLink"));
    			game.setScreenshotLink(rs.getString("screenshotLink"));
    			game.setSteamID(rs.getString("steamID"));
    			game.setGermanDescription(rs.getString("germanDescription"));
    			game.setEnglishDescription(rs.getString("englishDescription"));
    			game.setPath(rs.getString("path"));
    			game.setLastTimeUsed(rs.getString("lastTimeUsed"));
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	game.setTaglistlist(readGameTagsByID(ID));
    	return game;
	}
	
	private static ArrayList<ArrayList<String>> readGameTagsByID(String ID) {
		query = "SELECT tags.tagID, tags.tagName, tags.tagCat FROM tags INNER JOIN gametags ON gametags.tagID = tags.tagID WHERE gameID = ?";
		Tag readTag = null;
		ArrayList<Tag> taglist = new ArrayList();
		ArrayList<String> allCategorys = new ArrayList();
		ArrayList<ArrayList<String>> taglistlist = new ArrayList();
		String tagCat;
		
		try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(query)){
			pstmt.setString(1,  ID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next() == true) {
				readTag = new Tag();
				readTag.setID(rs.getString("tagID"));
				readTag.setTagName(rs.getString("tagName"));
				tagCat = rs.getString("tagCat");
				readTag.setTagCat(tagCat);
				if(!(allCategorys.contains(tagCat))) allCategorys.add(tagCat);
				taglist.add(readTag);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < allCategorys.size(); i++) {
			ArrayList<String> cat = new ArrayList();
			for(int j = 0; j < taglist.size(); j++) {
				if(taglist.get(j).getTagCat().equals(allCategorys.get(i))) {
					cat.add(taglist.get(j).getTagName());
				}
			}
			taglistlist.add(cat);
		}
		return taglistlist;
	}
	
	public static ArrayList<Game> readAllGames(){
		String myQuery = "SELECT gameID, name, thumbnailLink FROM games";
		Game g;
		ArrayList<Game> gameList = new ArrayList<>();
		
		try (Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(myQuery)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				g = new Game();
				g.setGameID(rs.getString("gameID"));
				g.setName(rs.getString("name"));
				g.setThumbnailLink(rs.getString("thumbnailLink"));
				gameList.add(g);
			}
			return gameList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void deleteGame(Game g) {
		insert = "DELETE FROM games WHERE gameID = ?";
		try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(insert)) {
			pstmt.setString(1, g.getGameID());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		insert = "DELETE FROM gametags WHERE gameID = ?";
		try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(insert)) {
			pstmt.setString(1, g.getGameID());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateGame(Game g) {
		deleteGame(g);
		addGame(g);
	}
	
	
	//Tag methoden
	public static void addTag(Tag t) {
		insert = "INSERT INTO tags(tagID, tagName, tagCat) values(?,?,?)";
		try (Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(insert)){
			pstmt.setString(1, t.getID());
			pstmt.setString(2, t.getTagName());
			pstmt.setString(3, t.getTagCat());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Tag readTagByID(String ID) {
		query = "SELECT * FROM tags WHERE tagID = ?";
    	Tag readTag = null;
    	
    	try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, ID);
			ResultSet rs = pstmt.executeQuery();
    		if(rs.next() == true) {
    			readTag = new Tag();
    			readTag.setID(rs.getString("tagID"));
    			readTag.setTagName(rs.getString("tagName"));
    			readTag.setTagCat(rs.getString("tagCat"));
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return readTag;
	}
	
	public static ArrayList<Tag> readAllTags(){
		String myQuery = "SELECT tagID, tagName, tagCat FROM tags";
		Tag t;
		ArrayList<Tag> tagList = new ArrayList<>();
		
		try (Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(myQuery)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				t = new Tag();
				t.setID(rs.getString("tagID"));
				t.setTagName(rs.getString("tagName"));
				t.setTagCat(rs.getString("tagCat"));
				tagList.add(t);
			}
			return tagList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Tag> readAllTagsOfCat(String cat){
		String myQuery = "SELECT tagID, tagName, tagCat FROM tags WHERE tagCat = ?";
		Tag t;
		ArrayList<Tag> tagList = new ArrayList<>();
		
		try (Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(myQuery)) {
			pstmt.setString(1, cat);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				t = new Tag();
				t.setID(rs.getString("tagID"));
				t.setTagName(rs.getString("tagName"));
				t.setTagCat(rs.getString("tagCat"));
				tagList.add(t);
			}
			return tagList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void deleteTag(Tag t) {
		insert = "DELETE FROM tags WHERE tagID = ?";
		try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(insert)) {
			pstmt.setString(1, t.getID());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		insert = "DELETE FROM gametags WHERE tagID = ?";
		try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(insert)) {
			pstmt.setString(1, t.getID());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateTag(Tag t) {
		deleteTag(t);
		addTag(t);
	}

}
