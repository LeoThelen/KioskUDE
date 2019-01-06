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
 * addGameTags; (private methode in addGame aufgerufen) (in arbeit)
 * readGameByID(String ID);
 * readGameTagsByID(String ID); (private methode in readGameByID aufgerufen) (in arbeit)
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
public class DBUtil {

	private static String connect = "jdbc:sqlite:C:\\Users\\suzan\\git\\KioskUDE\\Kiosk\\database.db";
    private static String insert = "";
    private static String query = "";

	public static void main(String[] args) {
		Game g1 = new Game();
		g1.setId("8");
		g1.setName("TestDerTags");
		
		Tag t1 = new Tag();
		t1.setID("1");
		t1.setTagName("12");
		t1.setTagCat("alter");
		//addTag(t1)
		Tag t2 = new Tag();
		t2.setID("2");
		t2.setTagName("16");
		t2.setTagCat("alter");
		//addTag(t2);
		Tag t3 = new Tag();
		t3.setID("3");
		t3.setTagName("Entspannung");
		t3.setTagCat("genre");
		//addTag(t3);
		
		ArrayList<String> alter = new ArrayList();
		alter.add(t1.getTagName());
		alter.add(t2.getTagName());
		ArrayList<String> genre = new ArrayList();
		genre.add(t3.getTagName());
		ArrayList<ArrayList<String>> taglistlist = new ArrayList();
		taglistlist.add(alter);
		taglistlist.add(genre);
		g1.setTaglistlist(taglistlist);
		
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
			pstmt.setString(1, g.getGameID());
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
		addGameTagsByID(g);
	}
	
	//arbeite dran
	private static void addGameTagsByID(Game g) {
		
	}
	
	public static Game readGameByID(String ID) {
		query = "SELECT * FROM games WHERE gameID = ?";
    	Game readGame = null;
    	
    	try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, ID);
			ResultSet rs = pstmt.executeQuery();
    		if(rs.next() == true) {
    			readGame = new Game();
    			readGame.setGameID(rs.getString("gameID"));
    			readGame.setName(rs.getString("name"));
    			readGame.setThumbnailLink(rs.getString("thumbnailLink"));
    			readGame.setScreenshotLink(rs.getString("screenshotLink"));
    			readGame.setSteamID(rs.getString("steamID"));
    			readGame.setGermanDescription(rs.getString("germanDescription"));
    			readGame.setEnglishDescription(rs.getString("englishDescription"));
    			readGame.setPath(rs.getString("path"));
    			readGame.setLastTimeUsed(rs.getString("lastTimeUsed"));
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	readGame.setTaglistlist(readGameTagsByID(ID));
    	return readGame;
	}
	
	//arbeite dran
	private static ArrayList<ArrayList<String>> readGameTagsByID(String ID) {
		return null;
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
	
	//arbeite dran
	public static void deleteGame(Game g) {
		insert = "DELETE FROM games WHERE gameID = ?";
		try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(insert)) {
			pstmt.setString(1, g.getGameID());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//vorrübergehende sehr ineffiziente methode
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
	
	//arbeite dran
	public static void deleteTag(Tag t) {
		insert = "DELETE FROM tags WHERE tagID = ?";
		try(Connection con = DriverManager.getConnection(connect); PreparedStatement pstmt = con.prepareStatement(insert)) {
			pstmt.setString(1, t.getID());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//vorrübergehende sehr ineffiziente methode
	public static void updateTag(Tag t) {
		deleteTag(t);
		addTag(t);
	}

}
