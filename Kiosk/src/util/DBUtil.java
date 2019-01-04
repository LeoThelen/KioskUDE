package util;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import domain.Game;
import domain.GameList;

public class DBUtil {

/*	static Connection conn = null;
	static String dbHost = "localhost";
	static String database = "kiosk";
	static String dbPort = "3306";
	static String dbUser = "root";
	static String dbPassword = "";

	public static Connection MySQLConnection_connect() {
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
	 * @return 
	 *
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
	 *
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
	
	
	/*Damit das läuft bräuchtest du sqlLite, aber du kannst ja auch einfach nen anderen Driver benutzten
	 * Das ist jetzt JDBC
	 * CREATE TABLE games(gameID TEXT primary key, name TEXT, taglistlist TEXT, thumbnailLink TEXT, screenshotLink TEXT,
	 * steamID TEXT, germanDescription TEXT, englishDescription TEXT, path TEXT, lastTimeUsed TEXT);
	 * 
	 * taglistlist ist da drin, weil ich kurz die Idee hatte taglistlist einfach in nen String umzuwandelen
	 * z.B.
	 * "$ alter, unter 12 $ genre, Simulation, Entspannung" 
	 * $ - für jede neue ArrayList und , als Trennung der einzigen Elemente
	 * Aber das ist glaub ich zu ineffizient
	 * 
	 * Ich hoffe du kannst was damit anfangen
	 * Ich bin noch am arbeiten und will alles in methoden zusammenfassen.
	 * writeGame(Game g)
	 * readGame(String id)
	 * readAllGames()
	 * deleteGame()
	 * saveGameChanges()
	 * 
	 * Tags kommen auch noch, sorry dass das alles etwas dauert
	 */
	private static Connection con = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;
    private static String insert = "";
    private static String query = "";
    private static String connect = "jdbc:sqlite:C:\\Users\\suzan\\git\\KioskUDE\\Kiosk\\database.db";

	//Name des Tabelle ist Games
	public static void main(String[] args) {
		Game g1 = new Game("11");
		g1.setName("BeatSaber");
		
		Game g2 = new Game("22");
		g2.setName("Job Simulator");
		
		ArrayList<Game> games = new ArrayList();
	
		loadDriver();
		
		//Spiel reinschreiben funktioniert
		//auskommentiert da ich mir nicht sicher bin was passiert wenn man ein spiel zum zweiten mal
		//reinschreiben will
		/*try {
			connect();
			openWriteable();
			//writeGame(g1);
			//writeGame(g2);
			
		}finally {
			closeWriteable();
		}*/
		
		//Spiele auslesen funktioniert auch
		try {
			connect();
			openReadableGames();
			Game nextGame = null;
			nextGame = readGame();
			while(nextGame != null) {
				games.add(nextGame);
				nextGame = readGame();
			}
		}finally {
			closeReadable();
		}
		
		for(int i = 0; i < games.size(); i++) {
			System.out.println(games.get(i).getGameID());
		}
		
	}
	
	//Connection kram
	public static void loadDriver(){
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver load complete");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	public static void connect() {
		try {
            con = DriverManager.getConnection(connect);
            System.out.println("Connection established");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//reinschreiben eines songs
	public static void openWriteable() {
		insert = "INSERT INTO GAMES(gameID, name, taglistlist, thumbnailLink, screenshotLink, steamID, germanDescription, englishDescription, path, lastTimeUsed)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}
	
	public static void writeGame(Game g) {
		try {
			pstmt = con.prepareStatement(insert);
			pstmt.setString(1, g.getGameID());
			pstmt.setString(2, g.getName());
			pstmt.setString(3, "muss ich verbessern");
			pstmt.setString(4, g.getThumbnailLink());
			pstmt.setString(5, g.getScreenshotLink());
			pstmt.setString(6, g.getSteamID());
			pstmt.setString(7, g.getGermanDescription());
			pstmt.setString(8, g.getEnglishDescription());
			pstmt.setString(9, g.getPath());
			pstmt.setString(10, g.getLastTimeUsed());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
    public static void closeWriteable() {
        try {
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //auslesen von allen spielen
    public static void openReadableGames() {
    	query = "SELECT * FROM games";
    	try {
    		pstmt = con.prepareStatement(query);
    		rs = pstmt.executeQuery();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public static Game readGame() {
    	Game readGame = null;
    	try {
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
    	
    	return readGame;
    }
    
    public static void closeReadable() {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   

}
