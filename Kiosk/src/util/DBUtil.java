package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;

import org.apache.commons.codec.digest.DigestUtils;
import javax.swing.JOptionPane;

import domain.Game;
import domain.Tag;
import domain.TagCategory;

/**
 * 
 * @author Leo Thelen, Suzanne Wrobel
 *
 * Datenbankfunktionen
 *
 */
public class DBUtil {

	static String dbHost = "localhost";
	static String database = "kiosk";
	static String dbPort = "3306";
	static String dbUser = "root";
	static String dbPassword = "";

	public static Connection MariaDBConnection_connect() {
		// Datenbanktreiber fuer ODBC Schnittstellen laden
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			// Verbindung zur Datenbank herstellen
			return DriverManager.getConnection("jdbc:mariadb://" + dbHost + ":" + dbPort + "/" + database, dbUser,
					dbPassword);
		} catch (SQLException e) {
			System.out.println("Verbindung nicht moeglich");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void MySQLConnection_close(Connection conn) {
		if (conn != null) {
			try {
//				System.out.println("\n Verbindung wird getrennt \n");
				conn.close();
			} catch (Exception e) {
				System.out.println("\n Fehler beim Trennen der Verbindung aufgetreten. Siehe db.log. \n");
			}
		}
	}
	
	/**
	 * Lädt alle Games aus der DB.
	 * @return List<Game> Informationsreduzierte Gameobjekte mit gameID, Name, ThumbnailLink
	 */
	public static LinkedList<Game> getGameList() {
		return getGameList(0);
	}

	/**
	 * @param librarySpecifier ermöglicht genaueres Spezifizieren der Library<br>
	 * case 1: nur Spiele mit SteamID<br>
	 * case 2: nur Spiele mit OculusID<br>
	 * case -1: nur Spiele ohne SteamID<br>
	 * case -2: nur Spiele ohne OculusID<br>
	 * ansonsten: alle Spiele
	 * @return List<Game> Informationsreduzierte Gameobjekte mit gameID, Name, ThumbnailLink
	 */
	public static LinkedList<Game> getGameList(int librarySpecifier) {
		String myQuery = "SELECT gameID, name, thumbnailLink FROM games ORDER BY lastTimeUsed DESC";
		switch (librarySpecifier) {
		case 1:
			myQuery += " WHERE steamID IS NOT NULL";
			break;
		case 2:
			myQuery += " WHERE oculusID IS NOT NULL";
			break;
		case -1:
			myQuery += " WHERE steamID IS NULL";
			break;
		case -2:
			myQuery += " WHERE oculusID IS NULL";
			break;
		}
		LinkedList<Game> gameList = new LinkedList<>();

		try (Connection conn = MariaDBConnection_connect()) {
			PreparedStatement stmt = conn.prepareStatement(myQuery);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next()) {
				gameList.add(new Game(rs.getString("gameID"), rs.getString("name"), rs.getString("thumbnailLink"), new LinkedList<Tag>()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		gameList = getGameTagsOnGameList(gameList);
		return gameList;
	}
	


	/**
	 * @return LinkedList<TagCategory> Liste aller Tagkategorien mit entsprechenden Tags
	 */
	public static LinkedList<TagCategory> getTagCategoryList() {
		String myQuery = "SELECT catID, labelDE, labelEN FROM tagCats";
		LinkedList<TagCategory> tagCats = new LinkedList<>();
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next()) {
				tagCats.add(new TagCategory(rs.getString("catID"), rs.getString("labelDE"), rs.getString("labelEN"),
						getAllTagsOfTagCategory(rs.getString("catID"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tagCats;
	}

	private static LinkedList<Tag> getAllTagsOfTagCategory(String catID) {
		String myQuery = "SELECT tagID, catID, labelDE, labelEN FROM tags WHERE catID = ?";
		LinkedList<Tag> taglist = new LinkedList<>();
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, catID);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next()) {
				taglist.add(new Tag(rs.getString("tagID"), rs.getString("catID"), rs.getString("labelDE"),
						rs.getString("labelEN")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taglist;
	}
	
	public static Game getGameDescriptionByGameID(String gameID) {
		String myQuery = "SELECT gameID, steamID, oculusID, name, germanDescription, englishDescription, thumbnailLink, screenshotLink, path, lastTimeUsed"
				+ " FROM games WHERE gameID=?";
		Game g = null;
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, gameID);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next()) {
				g = new Game();
				g.setGameID(rs.getString("gameID"));
				g.setName(rs.getString("name"));
				g.setSteamID(rs.getString("SteamID"));
				g.setOculusID(rs.getString("oculusID"));
				g.setGermanDescription(rs.getString("germanDescription"));
				g.setEnglishDescription(rs.getString("englishDescription"));
				g.setThumbnailLink(rs.getString("thumbnailLink"));
				g.setScreenshotLink(rs.getString("screenshotLink"));
				g.setPath(rs.getString("path"));
				g.setLastTimeUsed(rs.getString("lastTimeUsed"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		g.setTaglist(getGameTagsByGameID(g.getGameID()));
		return g;
	}

	public static LinkedList<Tag> getGameTagsByGameID(String gameID) {
		String myQuery = "SELECT tags.tagID, catID, labelDE, labelEN  FROM gametags JOIN tags ON gametags.tagID = tags.tagID WHERE gameID = ?";
		LinkedList<Tag> tagList = new LinkedList<>();
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, gameID);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next()) {
				tagList.add(new Tag(rs.getString("tagID"), rs.getString("catID"), rs.getString("labelDE"), rs.getString("labelEN")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tagList;
	}
	
	private static LinkedList<Game> getGameTagsOnGameList(LinkedList<Game> gameList) {
		String myQuery = "SELECT tagID FROM gametags WHERE gameID = ?";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			for (Game game : gameList) {
				stmt.setString(1, game.getGameID());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					game.getTaglist().add(Tag.FromTagID(rs.getString("tagID")));
				}
			}
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gameList;
	}

	public static Tag getTagByTagID(String tagID) {
		String myQuery = "SELECT * FROM tags WHERE tagID = ?";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, tagID);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			if (rs.next()) {
				return new Tag(rs.getString("tagID"), rs.getString("catID"), rs.getString("labelDE"),
						rs.getString("labelEN"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Tag getTagByLabelEN(String labelEN) {
		String myQuery = "SELECT * FROM tags WHERE  labelEN = ?";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, labelEN);
			ResultSet rs = stmt.executeQuery();
			System.out.println("DBUtil.getTagByLabel returns ");
			if (rs.next() == true) {
				System.out.println(rs.getString("tagID"));
				return new Tag(rs.getString("tagID"), rs.getString("catID"), rs.getString("labelDE"),
						rs.getString("labelEN"));
			}
			System.err.println("nothing.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void updateGame(Game game) {
		String myQuery = "UPDATE games SET ";
		if (game.getName() != null) {
			myQuery += "name=?, ";
		}
		if (game.getThumbnailLink() != null) {
			myQuery += "thumbnailLink=?, ";
		}
		if (game.getScreenshotLink() != null) {
			myQuery += "screenshotLink=?, ";
		}
//		if(game.getSteamID()!=null) {
//			myQuery+="steamID=?, ";
//		}
//		if(game.getOculusID()!=null) {
//			myQuery+="oculusID=?, ";
//		}
		if (game.getGermanDescription() != null) {
			myQuery += "germanDescription=?, ";
		}
		if (game.getEnglishDescription() != null) {
			myQuery += "englishDescription=?, ";
		}
		if (game.getPath() != null) {
			myQuery += "path=?, "; // kein Komma beim letzten Eintrag
		}
		myQuery = myQuery.substring(0, myQuery.length()-2);
		myQuery += " WHERE gameID=?";
		try (Connection conn = MariaDBConnection_connect()) {
			PreparedStatement stmt = conn.prepareStatement(myQuery);
			int c = 1;
			if (game.getName() != null)
				stmt.setString(c++, game.getName());
			if (game.getThumbnailLink() != null)
				stmt.setString(c++, game.getThumbnailLink());
			if (game.getScreenshotLink() != null)
				stmt.setString(c++, game.getScreenshotLink());
//			if(game.getSteamID()!=null)
//				stmt.setString(c++, game.getSteamID());
//			if(game.getOculusID()!=null)
//				stmt.setString(c++, game.getOculusID());
			if (game.getGermanDescription() != null)
				stmt.setString(c++, game.getGermanDescription());
			if (game.getEnglishDescription() != null)
				stmt.setString(c++, game.getEnglishDescription());
			if (game.getPath() != null)
				stmt.setString(c++, game.getPath().replace("\"", ""));	//Anführungsstriche entfernen erhöht Sicherheit
//			if(game.getLastTimeUsed()!=null)
//				stmt.setString(c++, game.getLastTimeUsed());
			if (game.getGameID() != null)
				stmt.setString(c++, game.getGameID());
			if (game.getGameID() != null)
				stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** TODO adds game, returns Gameobject with GameID from DB */
	public static Game saveGame(Game game) {
		
		if (steamIDNotInDB(game.getSteamID()) && oculusIDNotInDB(game.getOculusID()) ) {
			game = addGame(game);
			System.out.println("GameID:\t" + game.getGameID());
			//get Tags from Steam
			if (game.getSteamID() != null) {
				System.out.println("SteamID:\t" + game.getSteamID());
				game.setTaglist(SteamUtil.getSteamGameWithTags(game.getSteamID()).getTaglist());
			}
			if(game.getOculusID() != null) {
				game.addTag(DBUtil.getTagByLabelEN("Oculus Go"));
			}
			if (!game.getTaglist().isEmpty()) {
				addGameTagsByGame(game);
			}
		} else {
			System.out.println("Spiel ist schon in DB. Updatemethode wird aufgerufen.");
			game=getGameIDByGameObject(game);
			updateGame(game);
		}
		return game;
	}

	private static Game addGame(Game game) {
		String myQuery = "INSERT INTO GAMES(";
		if (game.getGameID() != null) {
			myQuery += "gameID,";
		}
		myQuery += "name, thumbnailLink, screenshotLink, steamID, oculusID, germanDescription, englishDescription, path, lastTimeUsed)"
				+ " values(";
		if (game.getGameID() != null) {
			myQuery += "?,";
		}
		myQuery += "?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try (Connection conn = MariaDBConnection_connect()) {
			PreparedStatement stmt = conn.prepareStatement(myQuery);
			int c = 1;
			if (game.getGameID() != null) {
				stmt.setString(c++, game.getGameID());
			}
			stmt.setString(c++, game.getName());
			stmt.setString(c++, game.getThumbnailLink());
			stmt.setString(c++, game.getScreenshotLink());
			stmt.setString(c++, game.getSteamID());
			stmt.setString(c++, game.getOculusID());
			stmt.setString(c++, game.getGermanDescription());
			stmt.setString(c++, game.getEnglishDescription());
			stmt.setString(c++, game.getPath());
			stmt.setString(c++, game.getLastTimeUsed());
			stmt.executeUpdate();
			stmt = conn.prepareStatement("SELECT LAST_INSERT_ID() AS gameID;");
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next()) {
				game.setGameID(rs.getString("gameID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return game;
	}

	/* returns true wenn nicht schon in der Datenbank */
	private static boolean steamIDNotInDB(String steamID) {
		return !isGameInDB(steamID, "Steam");
	}

	/* returns true wenn nicht schon in der Datenbank */
	private static boolean oculusIDNotInDB(String oculusID) {
		return !isGameInDB(oculusID, "Oculus");
	}
	
	/* returns true wenn schon in der Datenbank */
	private static boolean isGameInDB(String libraryID, String library) {
		String myQuery = "";
		if(library.equals("Steam")) {
			myQuery += "SELECT steamID FROM games WHERE steamID=?";
		}
		if(library.equals("Oculus")) {
			myQuery += "SELECT oculusID FROM games WHERE oculusID=?";
		}
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, libraryID);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next()) {
				if (rs.getString(library+"ID").equals(libraryID)) {
					System.out.println("is in db."); 
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(library + ""+ libraryID +"is not in db."); 
		return false;
	}
	
	/*
	 * versucht, gameID anhand der Steam-/OculusID zu bekommen
	 * @param g
	 * @return
	 */
	private static Game getGameIDByGameObject(Game game) {
		String myQuery = "SELECT gameID FROM games WHERE "; 
		if(game.getSteamID()!=null) {
			myQuery+="steamID=?";
		}
		if(game.getOculusID()!=null) {
			myQuery+="oculusID=?";
		}
		try (Connection conn = MariaDBConnection_connect()) {
			PreparedStatement stmt = conn.prepareStatement(myQuery);
			if(game.getSteamID()!=null) {
				stmt.setString(1, game.getSteamID());
			}
			if(game.getOculusID()!=null) {
				stmt.setString(1, game.getOculusID());
			}
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			if (rs.next() == true) {
				String gameID= Integer.toString(rs.getInt("gameID"));
				game.setGameID(gameID);
				return game;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return game;
	}

	public static void addGameTagByGameIDAndTagID(String gameID, String tagID) {
		String myQuery = "INSERT INTO gametags(gameID, tagID) values(?,?)";
		try (Connection conn = MariaDBConnection_connect();) {
			PreparedStatement stmt = conn.prepareStatement(myQuery);
			stmt.setString(1, gameID);
			stmt.setString(2, tagID);
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addGameTagsByGame(Game game) {
		String myQuery = "INSERT INTO gametags(gameID, tagID) VALUES ";
		for (Tag t : game.getTaglist()) {
			myQuery += "(?,?),";
		}
		myQuery = myQuery.substring(0, myQuery.length() - 1);
		try (Connection conn = MariaDBConnection_connect();) {
			PreparedStatement stmt = conn.prepareStatement(myQuery);
			int c = 1;
			for (Tag t : game.getTaglist()) {
				stmt.setString(c++, game.getGameID());
				stmt.setString(c++, t.getTagID());
			}
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addTagCategory(TagCategory tagCategory) {
		String myQuery = "INSERT INTO tagCats(catID, labelDE, labelEN) values(?,?,?)";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			int c = 1;
			stmt.setString(c++, tagCategory.getCatID());
			stmt.setString(c++, tagCategory.getLabelDE());
			stmt.setString(c, tagCategory.getLabelEN());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
		}
	}

	public static void addTag(Tag tag) {
		String myQuery = "INSERT INTO tags(catID, labelDE, labelEN) values(?,?,?)";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			int c = 1;
			stmt.setString(c++, tag.getCatID());
			stmt.setString(c++, tag.getLabelDE());
			stmt.setString(c, tag.getLabelEN());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateTag(Tag tag) {
		String myQuery = "UPDATE tags SET catID=?,labelDE=?,labelEN=? WHERE tagID=?";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			int c = 1;
			stmt.setString(c++, tag.getCatID());
			stmt.setString(c++, tag.getLabelDE());
			stmt.setString(c++, tag.getLabelEN());
			stmt.setString(c++, tag.getTagID());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Anm.: ON DELETE CASCADE loescht auch Eintraege in gametags-Tabelle automatisch mit.
	 * @param game
	 */
	public static void deleteGame(Game game) { 
											
		String myQuery = "DELETE FROM games WHERE gameID = ?";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, game.getGameID());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
			System.out.println("Game deleted: "+ game.getGameID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Anm.: ON DELETE CASCADE loescht auch Eintraege in gametags-Tabelle automatisch mit.
	 * @param tag
	 */
	public static void deleteTag(Tag tag) {
		String myQuery = "DELETE FROM tags WHERE tagID = ?";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, tag.getTagID());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteGameTagByGameIDAndTagID(String gameID, String tagID) {
		String myQuery = "DELETE FROM gametags WHERE gameID = ? AND tagID = ?";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			int c = 1;
			stmt.setString(c++, gameID);
			stmt.setString(c++, tagID);
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void writePassword(String username, String password, String salt) {
		String myQuery = "INSERT INTO login(username, password, salt) values(?, ?, ?)";
		String passwort = DigestUtils.sha256Hex(password + salt);
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, username);
			stmt.setString(2, passwort);
			stmt.setString(3, salt);
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean verifyLogin(String username, String password) {
		String myQuery = "SELECT salt, password FROM login WHERE username = ?";
		String salt = "";
		String passwordFromDB = "";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next() == true) {
				salt = rs.getString("salt");
				passwordFromDB = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		password = DigestUtils.sha256Hex(password + salt);
		if (password.equals(passwordFromDB)) {
			return true;
		} else {
			return false;
		}
	}

	public static void updatePassword(String username, String newPassword, String salt) {
		String myQuery = "UPDATE login SET password=?, salt=? WHERE username=?";
		String encodedPassword = DigestUtils.sha256Hex(newPassword + salt);
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			int c = 1;
			stmt.setString(c++, encodedPassword);
			stmt.setString(c++, salt);
			stmt.setString(c++, username);
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		long starttime;
		for (int i = 0; i < 20; i++) {
			starttime= System.currentTimeMillis();
			getGameList();
			System.out.println(System.currentTimeMillis()-starttime);
		}
	}
}
