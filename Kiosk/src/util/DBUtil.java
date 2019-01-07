package util;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import domain.Game;

public class DBUtil {

	static Connection conn = null;
	static String dbHost = "localhost";
	static String database = "kiosk";
	static String dbPort = "3306";
	static String dbUser = "root";
	static String dbPassword = "";

	private static Connection MariaDBConnection_connect() {
		// Datenbanktreiber fuer ODBC Schnittstellen laden
		try {
			DriverManager.setLogWriter(new PrintWriter(System.out));
			// Verbindung zur Datenbank herstellen
			conn = DriverManager.getConnection("jdbc:mariadb://" + dbHost + ":" + dbPort + "/" + database, dbUser,
					dbPassword);
			System.out.println("Datenbankverbindung aufgebaut");
		} catch (SQLException e) {
			System.out.println("Verbindung nicht moeglich");
		}
		return conn;
	}

	private static void MySQLConnection_close(Connection conn) {
		if (conn != null) {
			try {
				System.out.println("\n Verbindung wird getrennt \n");
				conn.close();
			} catch (Exception e) {
				System.out.println("\n Fehler beim Trennen der Verbindung aufgetreten \n");
			}
		}
	}

	/**
	 * bekommt ein request.getParameter("...") übergeben und gibt eine
	 * List<GameEntry> zurück.
	 * @return 
	 */
	public static ArrayList<Game> getGameList() {
		String myQuery = "SELECT gameID, name, thumbnailLink FROM games";
		Game g;
		ArrayList<Game> gameList = new ArrayList<>();

		try (PreparedStatement stmt = MariaDBConnection_connect().prepareStatement(myQuery)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				g = new Game(rs.getString(1), rs.getString(2), rs.getString(3));
				gameList.add(g);
			}
			return gameList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets full description of Game. Da hier nur ein Eintrag abgefragt wird,
	 * wuerde eine passgenauere Funktion die Performance nicht wesentlich
	 * steigern. TODO: Klasse schreiben, die gametags für jedes Spiel gettet.
	 * @return 
	 */
	public static Game getGameDescriptionByID(String ID) {
		String myQuery = "SELECT gameID, steamID, name, germanDescription, englishDescription, arabDescription, thumbnailLink, screenshotLink, path, lastTimeUsed"
				+ " FROM games WHERE gameID=?";
		Game g=null;

		try (Connection con = MariaDBConnection_connect(); PreparedStatement stmt = con.prepareStatement(myQuery)) {
			stmt.setString(1, ID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				g = new Game(rs.getString("gameID"), rs.getString("name"), rs.getString("thumbnailLink"));
				g.setSteamID(rs.getString("SteamID"));
				g.setGermanDescription(rs.getString("germanDescription"));
				g.setEnglishDescription(rs.getString("englishDescription"));
				g.setArabDescription(rs.getString("arabDescription"));
				g.setThumbnailLink(rs.getString("thumbnailLink"));
				g.setScreenshotLink(rs.getString("screenshotLink"));
				g.setPath(rs.getString("path"));
				g.setLastTimeUsed(rs.getString("lastTimeUsed"));
			}
			return g;
		} catch (SQLException e) {
			e.printStackTrace();
		}return null;
	}
	
	public static void addGame(Game g) {
		String insert = "INSERT INTO GAMES(gameID, name, thumbnailLink, screenshotLink, steamID, germanDescription, englishDescription, path, lastTimeUsed)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement pstmt = MariaDBConnection_connect().prepareStatement(insert)){
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
	private static void addGameTagByID(String gameID, String tagID) {
		String insert = "INSERT INTO gametags(gameID, tagID) values(?,?)";
		try (PreparedStatement pstmt = MariaDBConnection_connect().prepareStatement(insert)){
			pstmt.setString(1, gameID);
			pstmt.setString(2, tagID);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/** Mainmethode zum testen:
  */
public static void main(String[] args) throws SQLException {
	try(Connection con = MariaDBConnection_connect()){
		MySQLConnection_close(con);
	}
}
}
