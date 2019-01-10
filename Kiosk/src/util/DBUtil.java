package util;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;

import domain.Game;
import domain.Tag;
import domain.TagCategory;

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
		ArrayList<Game> gameList = new ArrayList<>();

		try (PreparedStatement stmt = MariaDBConnection_connect().prepareStatement(myQuery)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				gameList.add(new Game(rs.getString("gameID"),
					rs.getString("name"),
					rs.getString("thumbnailLink"),
					getGameTagsByID(rs.getString("gameID"))
				));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gameList;
	}
	
	public static ArrayList<TagCategory> getTagList() {
		String myQuery = "SELECT catID, labelDE, labelEN FROM tagCats";
		ArrayList<TagCategory> tagCats = new ArrayList<>();
		try (PreparedStatement stmt = MariaDBConnection_connect().prepareStatement(myQuery)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				tagCats.add(new TagCategory(
						rs.getString("catID"),
						rs.getString("labelDE"),
						rs.getString("labelEN"),
						getAllTagsOfCat(rs.getString("catID"))
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tagCats;
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

	private static ArrayList<Tag> getGameTagsByID(String gameID) {
		String myQuery = "SELECT tagID FROM gametags WHERE gameID = ?";
		ArrayList<Tag> taglist = new ArrayList<>();
		try (PreparedStatement stmt = MariaDBConnection_connect().prepareStatement(myQuery)) {
			stmt.setString(1, gameID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				taglist.add(new Tag(rs.getString("tagID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taglist;
	}
	
	public static ArrayList<Tag> getAllTagsOfCat(String catID){
		String myQuery = "SELECT tagID, catID, labelDE, labelEN FROM tags WHERE catID = ?";
		ArrayList<Tag> taglist = new ArrayList<>();
		try (PreparedStatement stmt = MariaDBConnection_connect().prepareStatement(myQuery)) {
			stmt.setString(1, catID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				taglist.add(new Tag(rs.getString("tagID"),
    					rs.getString("catID"),
    					rs.getString("labelDE"),
    					rs.getString("labelEN")
    					));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taglist;
	}
		
	public static Tag getTagByID(String ID) {
		String myQuery = "SELECT * FROM tags WHERE tagID = ?";
		try (PreparedStatement pstmt = MariaDBConnection_connect().prepareStatement(myQuery)) {			pstmt.setString(1, ID);
			ResultSet rs = pstmt.executeQuery();
    		if(rs.next() == true) {
    			return new Tag(rs.getString("tagID"),
    					rs.getString("catID"),
    					rs.getString("labelDE"),
    					rs.getString("labelEN")
    					);
    		}
    	}catch(SQLException e) {
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

		if (!(g.getTaglist() == null)) {
			for (int i = 0; i < g.getTaglist().size(); i++) {
				addGameTagByID(g.getGameID(), g.getTaglist().get(i).getTagID());
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

	public static void addTag(Tag t) {
		String insert = "INSERT INTO tags(tagID, catID, labelDE, labelEN) values(?,?,?)";
		try (PreparedStatement pstmt = MariaDBConnection_connect().prepareStatement(insert)){
			pstmt.setString(1, t.getTagID());
			pstmt.setString(2, t.getCatID());
			pstmt.setString(3, t.getLabelDE());
			pstmt.setString(3, t.getLabelEN());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	
	private static void customInsert(String string) {
		String insert = HTMLEntities.encode(string);

		try (PreparedStatement pstmt = MariaDBConnection_connect().prepareStatement(insert)) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Passwort Methoden
	//Hashed und salted das passwort und trägt alles in eine DB ein
	//Beispiel salts: "*K&Jji" "rt%sH" "wwwQ*&%"
	public static void writePassword(String username, String originalPasswort, String salt){
		String insert = "INSERT INTO login(username, password, salt) values(?, ?, ?)";
		String passwort = DigestUtils.sha256Hex(originalPasswort + salt);
		try(PreparedStatement pstmt = MariaDBConnection_connect().prepareStatement(insert)){
			pstmt.setString(1, username);
			pstmt.setString(2, passwort);
			pstmt.setString(3, salt);
			pstmt.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

    	//nimmt username und passwort entgegen und guckt ob es richtig ist
	public static boolean passwordCorrect(String username, String enteredPassword){
	    String query = "SELECT salt, password FROM login WHERE username = ?";
	    String salt = "";
	    String password = "";
	    try(PreparedStatement pstmt = MariaDBConnection_connect().prepareStatement(query)){
	        pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        while(rs.next() == true){
	            salt = rs.getString("salt");
	            password = rs.getString("password");
	        }
	    }catch (SQLException e){
	        e.printStackTrace();
	    }
	
	    enteredPassword = DigestUtils.sha256Hex(enteredPassword + salt);
	    if(enteredPassword.equals(password)){
	        return true;
	    }else{
	        return false;
	    }
	}
	/**
	 * Mainmethode zum Datenbanksetup:
	 */
	public static void main(String[] args) throws SQLException {
		ArrayList<TagCategory> tagList = DBUtil.getTagList();
		System.out.println("hi");
		writePassword("admin", "passwort", "salt");
//		try (Connection con = MariaDBConnection_connect()) {
//			MySQLConnection_close(con);
//		}
//		customInsert("INSERT INTO tagCats (catID,labelDE, labelEN)\r\n" + "VALUES\r\n"
//				+ "	('1','VR-Brille','VR-System'),\r\n" + "	('2','Alter','Age'),\r\n" + "	('3','Genre','Genre'),\r\n"
//				+ "	('5','Sprache','Language'),\r\n"
//				+ "	('6','Spiellänge','Time'),\r\n" + "	('7','Bewegung','Movement')\r\n" + ";");
//		customInsert("INSERT INTO tags (tagID, catID, labelDE, labelEN)\r\n" + "VALUES\r\n"
//				+ "	('1','1','HTC Vive Pro','HTC Vive Pro'),\r\n" + "	('2','1','Oculus Go','Oculus Go'),\r\n"
//				+ "	('3','2','unter 12 Jahre','under 12'),\r\n" + "	('4','2','12 - 16 J.','12 - 16 years'),\r\n"
//				+ "	('5','2','16 und älter','16 and older'),\r\n" + "	('6','3','Film ','Movies'),\r\n"
//				+ "	('7','3','Wissen','Knowledge'),\r\n" + "	('8','3','Medizin','Medicine'),\r\n"
//				+ "	('9','3','Minispiele','Mini Games'),\r\n" + "	('10','3','Abenteuer','Adventure'),\r\n"
//				+ "	('11','3','Simulation','Simulation'),\r\n" + "	('12','3','Geschicklichkeit','Dexterity'),\r\n"
//				+ "	('13','3','Strategie','Strategy'),\r\n" + "	('14','3','Action','Action'),\r\n"
//				+ "	('15','3','Entspannung','Relaxation'),\r\n"
//				+ "	('19','5','Deutsch','German'),\r\n" + "	('20','5','Englisch','English'),\r\n"
//				+ "	('21','5','Andere','Other'),\r\n"
//				+ "	('22','6','kurz (unter 30 Minuten)','short (less than 30 min.)'),\r\n"
//				+ "	('23','6','lang (über 30 Minuten)','long (more than 30 min.)'),\r\n"
//				+ "	('24','7','liegend','lying'),\r\n" + "	('25','7','stehend','standing'),\r\n"
//				+ "	('26','7','sitzend','seated'),\r\n" + "	('27','7','interaktiv','interactive'),\r\n"
//				+ "	('28','7','raumfüllend','Room-Scale'),\r\n" + "	('29','7','passiv','passive')\r\n" + ";");
	}
}
