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

	private static Connection MySQLConnection_connect() {
		// Datenbanktreiber fuer ODBC Schnittstellen laden
		try {
			DriverManager.setLogWriter(new PrintWriter(System.out));
			// Verbindung zur Datenbank herstellen
			conn = DriverManager.getConnection("jdbc:mariadb://" + dbHost + ":" + dbPort + "/" + database, dbUser,
					dbPassword);
			System.out.println("Datenbankverbindung hat geklappt");
		} catch (SQLException e) {
			System.out.println("Verbindung nicht moeglich");
		}
		return conn;
	}

	// Datenbearbeitung und Datenbankabfragen
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

		try (Connection con = MySQLConnection_connect(); PreparedStatement stmt = con.prepareStatement(myQuery)) {
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

		try (Connection con = MySQLConnection_connect(); PreparedStatement stmt = con.prepareStatement(myQuery)) {
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
	// public static void leerstellenEntfernen(){
//			// Datenbankabfragen
//			String myQuery = "UPDATE adb_wert "
//					+ "SET txt = REPLACE(txt, ' ','') "
//					+ "WHERE txt LIKE '% %' AND wert_nr IN (SELECT wert_nr FROM adb_mkm_kern WHERE merkmals_nr=72 AND aktion='I')";
//			try (Connection con = MySQLConnection_connect();
//					PreparedStatement stmt = con.prepareStatement(myQuery)) {
//				System.out.println(stmt.executeUpdate() + " rows updated. ");
//			} catch (SQLException ex) { // handle any errors
//				System.out.println("SQLException: " + ex.getMessage());
//				System.out.println("SQLState: " + ex.getSQLState());
//				System.out.println("VendorError: " + ex.getErrorCode());
//			}
	// }
//
//	public static void merkmalRequest() {
//		// Datenbankabfragen
////		   List<Compound_iab>compound_iabList = new ArrayList<Compound_iab>();
////		   Compound_iab c;
//
//		// fdb30.adb_mkm_liste_de.merkmals_nr
//		String myQuery = "SELECT merkmals_nr AS merkmal" + " FROM fdb30.adb_mkm_liste_de" + " LIMIT 10";//noch zu tun: nach
//																										// Test anpassen
//		try (Connection con = MySQLConnection_connect(); PreparedStatement stmt = con.prepareStatement(myQuery)) {
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next()) {
//				System.out.println(rs.getString("merkmal"));
////					c =new Compound_iab();
////					c.setCasrn(rs.getString("casrn"));
////					c.setInchi(rs.getString("inchi"));
////					c.setSmiles_original(rs.getString("smiles_original"));
////					
////					//hier gibts noch was zu tun evtl[...]
////					System.out.println(c.toString());
////					compound_iabList.add(c);
//			}
//		} catch (SQLException ex) { // handle any errors
//			System.out.println("SQLException: " + ex.getMessage());
//			System.out.println("SQLState: " + ex.getSQLState());
//			System.out.println("VendorError: " + ex.getErrorCode());
//		}
////			return compound_iabList;
//	}
	/** Mainmethode zum testen:
  */
public static void main(String[] args) throws SQLException {
	try(Connection con = MySQLConnection_connect()){
		MySQLConnection_close(con);
	}
}
}
