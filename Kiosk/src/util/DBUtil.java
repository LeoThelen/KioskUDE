package util;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import domain.Game;

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
			DriverManager.setLogWriter(new PrintWriter(System.out));
			// Verbindung zur Datenbank herstellen
			return DriverManager.getConnection("jdbc:mariadb://" + dbHost + ":" + dbPort + "/" + database, dbUser,
					dbPassword);
//			System.out.println("Datenbankverbindung aufgebaut");
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
	 * 
	 * @return
	 */
	public static ArrayList<Game> getGameList() {
		String myQuery = "SELECT gameID, name, thumbnailLink FROM games";
		Game g;
		ArrayList<Game> gameList = new ArrayList<>();

		try (PreparedStatement stmt = MariaDBConnection_connect().prepareStatement(myQuery)) {
			ResultSet rs = stmt.executeQuery();
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
	

	/**
	 * Gets full description of Game. Da hier nur ein Eintrag abgefragt wird, wuerde
	 * eine passgenauere Funktion die Performance nicht wesentlich steigern. TODO:
	 * Klasse schreiben, die gametags für jedes Spiel gettet.
	 * 
	 * @return
	 */
	public static Game getGameDescriptionByID(String ID) {
		String myQuery = "SELECT gameID, steamID, name, germanDescription, englishDescription, thumbnailLink, screenshotLink, path, lastTimeUsed"
				+ " FROM games WHERE gameID=?";
		Game g = null;

		try (PreparedStatement stmt = MariaDBConnection_connect().prepareStatement(myQuery)) {
			stmt.setString(1, ID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				g = new Game();
				g.setGameID(rs.getString("gameID"));
				g.setName(rs.getString("name"));
				g.setSteamID(rs.getString("SteamID"));
				g.setGermanDescription(rs.getString("germanDescription"));
				g.setEnglishDescription(rs.getString("englishDescription"));
				g.setThumbnailLink(rs.getString("thumbnailLink"));
				g.setScreenshotLink(rs.getString("screenshotLink"));
				g.setPath(rs.getString("path"));
				g.setLastTimeUsed(rs.getString("lastTimeUsed"));
			}
			return g;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void addGame(Game g) {
		String insert = "INSERT INTO GAMES(gameID, name, thumbnailLink, screenshotLink, steamID, germanDescription, englishDescription, path, lastTimeUsed)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = MariaDBConnection_connect().prepareStatement(insert)) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (!(g.getTaglistlist() == null)) {
			for (int i = 0; i < g.getTaglistlist().size(); i++) {
				for (int j = 0; j < g.getTaglistlist().get(i).size(); j++) {
					addGameTagByID(g.getGameID(), g.getTaglistlist().get(i).get(j));
				}
			}
		}
	}

	private static void addGameTagByID(String gameID, String tagID) {
		String insert = "INSERT INTO gametags(gameID, tagID) values(?,?)";
		try (PreparedStatement pstmt = MariaDBConnection_connect().prepareStatement(insert)) {
			pstmt.setString(1, gameID);
			pstmt.setString(2, tagID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mainmethode zum testen:
	 */
	public static void main(String[] args) throws SQLException {
		try (Connection con = MariaDBConnection_connect()) {
			MySQLConnection_close(con);
		}
	}
}
