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
				gameList.add(new Game(rs.getString("gameID"), rs.getString("name"), rs.getString("thumbnailLink"),
						getGameTagsByGameID(rs.getString("gameID"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
						getAllTagsOfCategory(rs.getString("catID"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tagCats;
	}

	private static LinkedList<Tag> getAllTagsOfCategory(String catID) {
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
	
	public static Game getGameDescriptionByID(String ID) {
		String myQuery = "SELECT gameID, steamID, oculusID, name, germanDescription, englishDescription, thumbnailLink, screenshotLink, path, lastTimeUsed"
				+ " FROM games WHERE gameID=?";
		Game g = null;
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, ID);
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
		LinkedList<Tag> taglist = new LinkedList<>();
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, gameID);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next()) {
				taglist.add(new Tag(rs.getString("tagID"), rs.getString("catID"), rs.getString("labelDE"), rs.getString("labelEN")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taglist;
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

	@Deprecated
	public static int reserveGameID() {
		String myQuery = "SELECT id FROM games WHERE  name = 'Half Life 3 Gaben Deluxe Edition'"; // This game will
																									// never exist.
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			if (rs.next() == true) {
				return rs.getInt("gameID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		myQuery = "INSERT INTO games(name) VALUES('Half Life 3 Gaben Deluxe Edition')"; // This game will never exist.
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			MySQLConnection_close(conn);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		myQuery = "SELECT id FROM games WHERE  name = 'Half Life 3 Gaben Deluxe Edition'"; // This game will never
																							// exist.
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			if (rs.next() == true) {
				return rs.getInt("gameID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void updateGame(Game g) {
		// TODO Auto-generated method stub
		String myQuery = "UPDATE games SET ";
		if (g.getName() != null) {
			myQuery += "name=?, ";
		}
		if (g.getThumbnailLink() != null) {
			myQuery += "thumbnailLink=?, ";
		}
		if (g.getScreenshotLink() != null) {
			myQuery += "screenshotLink=?, "; // TODO screenshots und Thumbnails bei veränderung löschen.
		}
//		if(g.getSteamID()!=null) {
//			myQuery+="steamID=?, ";
//		}
//		if(g.getOculusID()!=null) {
//			myQuery+="oculusID=?, ";
//		}
		if (g.getGermanDescription() != null) {
			myQuery += "germanDescription=?, ";
		}
		if (g.getEnglishDescription() != null) {
			myQuery += "englishDescription=?, ";
		}
		if (g.getPath() != null) {
			myQuery += "path=?, "; // kein Komma beim letzten Eintrag
		}
		myQuery = myQuery.substring(0, myQuery.length()-2);
		myQuery += " WHERE gameID=?";
		try (Connection conn = MariaDBConnection_connect()) {
			PreparedStatement stmt = conn.prepareStatement(myQuery);
			int c = 1;
			if (g.getName() != null)
				stmt.setString(c++, g.getName());
			if (g.getThumbnailLink() != null)
				stmt.setString(c++, g.getThumbnailLink());
			if (g.getScreenshotLink() != null)
				stmt.setString(c++, g.getScreenshotLink());
//			if(g.getSteamID()!=null)
//				stmt.setString(c++, g.getSteamID());
//			if(g.getOculusID()!=null)
//				stmt.setString(c++, g.getOculusID());
			if (g.getGermanDescription() != null)
				stmt.setString(c++, g.getGermanDescription());
			if (g.getEnglishDescription() != null)
				stmt.setString(c++, g.getEnglishDescription());
			if (g.getPath() != null)
				stmt.setString(c++, g.getPath().replace("\"", ""));	//Anführungsstriche entfernen erhöht Sicherheit
//			if(g.getLastTimeUsed()!=null)
//				stmt.setString(c++, g.getLastTimeUsed());
			if (g.getGameID() != null)
				stmt.setString(c++, g.getGameID());
			if (g.getGameID() != null)
				stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// angepasst mit checkSteamID
	/** adds game, returns Gameobject with GameID from DB */
	public static Game addGame(Game g) {
		System.out.println("DBUtil.addGame called with " + g.getName()); 
		
		String myQuery = "INSERT INTO GAMES(";
		if (g.getGameID() != null) {
			myQuery += "gameID,";
		}
		myQuery += "name, thumbnailLink, screenshotLink, steamID, oculusID, germanDescription, englishDescription, path, lastTimeUsed)"
				+ " values(";
		if (g.getGameID() != null) {
			myQuery += "?,";
		}
		myQuery += "?, ?, ?, ?, ?, ?, ?, ?, ?);";
		if (steamIDNotInDB(g.getSteamID()) && oculusIDNotInDB(g.getOculusID()) ) {
			try (Connection conn = MariaDBConnection_connect()) {
				PreparedStatement stmt = conn.prepareStatement(myQuery);
				int c = 1;
				if (g.getGameID() != null) {
					stmt.setString(c++, g.getGameID());
				}
				stmt.setString(c++, g.getName());
				stmt.setString(c++, g.getThumbnailLink());
				stmt.setString(c++, g.getScreenshotLink());
				stmt.setString(c++, g.getSteamID());
				stmt.setString(c++, g.getOculusID());
				stmt.setString(c++, g.getGermanDescription());
				stmt.setString(c++, g.getEnglishDescription());
				stmt.setString(c++, g.getPath());
				stmt.setString(c++, g.getLastTimeUsed());
				stmt.executeUpdate();
				stmt = conn.prepareStatement("SELECT LAST_INSERT_ID() AS gameID;");
				ResultSet rs = stmt.executeQuery();
				MySQLConnection_close(conn);
				while (rs.next()) {
					g.setGameID(rs.getString("gameID"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("GameID:\t" + g.getGameID());
			//get Tags from Steam
			if (g.getSteamID() != null) {
				System.out.println("SteamID:\t" + g.getSteamID());
				g.setTaglist(SteamUtil.getSteamGameWithTags(g.getSteamID()).getTaglist());
			}
			if(g.getOculusID() != null) {
				// TODO oculusTags hinzufügen?
				g.addTag(DBUtil.getTagByLabelEN("Oculus Go"));
			}
			if (!g.getTaglist().isEmpty()) {
				addGameTagsByGame(g);
			}
		} else {
			System.out.println("Spiel ist schon in DB. Updatemethode wird aufgerufen.");
			g=getGameIDBy(g);
			updateGame(g);
		}
		return g;
	}

	/* returns true wenn nicht schon in der Datenbank */
	private static boolean steamIDNotInDB(String steamID) {
		return !isInDB(steamID, "Steam");
	}

	/* returns true wenn nicht schon in der Datenbank */
	private static boolean oculusIDNotInDB(String oculusID) {
		return !isInDB(oculusID, "Oculus");
	}
	
	/* returns true wenn schon in der Datenbank */
	private static boolean isInDB(String libraryID, String library) {
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
	private static Game getGameIDBy(Game g) {
		String myQuery = "SELECT gameID FROM games WHERE "; 
		if(g.getSteamID()!=null) {
			myQuery+="steamID=?";
		}
		if(g.getOculusID()!=null) {
			myQuery+="oculusID=?";
		}
		try (Connection conn = MariaDBConnection_connect()) {
			PreparedStatement stmt = conn.prepareStatement(myQuery);
			if(g.getSteamID()!=null) {
				stmt.setString(1, g.getSteamID());
			}
			if(g.getOculusID()!=null) {
				stmt.setString(1, g.getOculusID());
			}
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			if (rs.next() == true) {
				String gameID= Integer.toString(rs.getInt("gameID"));
				g.setGameID(gameID);
				return g;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return g;
	}

	public static void addGameTagByID(String gameID, String tagID) {
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

	public static void addGameTagsByGame(Game g) {
		String myQuery = "INSERT INTO gametags(gameID, tagID) VALUES ";
		for (Tag t : g.getTaglist()) {
			myQuery += "(?,?),";
		}
		myQuery = myQuery.substring(0, myQuery.length() - 1);
		try (Connection conn = MariaDBConnection_connect();) {
			PreparedStatement stmt = conn.prepareStatement(myQuery);
			int c = 1;
			for (Tag t : g.getTaglist()) {
				stmt.setString(c++, g.getGameID());
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

	public static void addTag(Tag t) {
		String myQuery = "INSERT INTO tags(catID, labelDE, labelEN) values(?,?,?)";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			int c = 1;
			stmt.setString(c++, t.getCatID());
			stmt.setString(c++, t.getLabelDE());
			stmt.setString(c, t.getLabelEN());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateTag(Tag t) {
		String myQuery = "UPDATE tags SET catID=?,labelDE=?,labelEN=? WHERE tagID=?";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			int c = 1;
			stmt.setString(c++, t.getCatID());
			stmt.setString(c++, t.getLabelDE());
			stmt.setString(c++, t.getLabelEN());
			stmt.setString(c++, t.getTagID());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteGame(Game g) {// Anm.: ON DELETE CASCADE loescht Eintraege in gametags-Tabelle automatisch
											// mit.
		String myQuery = "DELETE FROM games WHERE gameID = ?";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, g.getGameID());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
			System.out.println("Game deleted: "+ g.getGameID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteTag(Tag t) {// Anm.: ON DELETE CASCADE loescht Eintraege in gametags-Tabelle automatisch
											// mit.
		String myQuery = "DELETE FROM tags WHERE tagID = ?";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, t.getTagID());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteGameTagByID(String gameID, String tagID) {
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

	@SuppressWarnings("unused")
	private static void addCustom(String myQuery) {
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Passwort Methoden
	// Hashed und salted das passwort und trägt alles in eine DB ein
	// Beispiel salts: "*K&Jji" "rt%sH" "wwwQ*&%"
	public static void writePassword(String username, String originalPasswort, String salt) {
		String myQuery = "INSERT INTO login(username, password, salt) values(?, ?, ?)";
		String passwort = DigestUtils.sha256Hex(originalPasswort + salt);
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

	// nimmt username und passwort entgegen und guckt ob es richtig ist
	public static boolean verifyLogin(String username, String enteredPassword) {
		String myQuery = "SELECT salt, password FROM login WHERE username = ?";
		String salt = "";
		String password = "";
		try (Connection conn = MariaDBConnection_connect(); PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next() == true) {
				salt = rs.getString("salt");
				password = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		enteredPassword = DigestUtils.sha256Hex(enteredPassword + salt);
		if (enteredPassword.equals(password)) {
			return true;
		} else {
			return false;
		}
	}
}
