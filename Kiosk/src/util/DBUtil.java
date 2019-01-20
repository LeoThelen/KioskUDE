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
			try {
				DriverManager.setLogWriter(new PrintWriter(new FileWriter(new File("tomcat\\wtpwebapps\\Kiosk\\db.log"),true)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
//				System.out.println("\n Verbindung wird getrennt \n");
				conn.close();
			} catch (Exception e) {
				System.out.println("\n Fehler beim Trennen der Verbindung aufgetreten. Siehe db.log. \n");
			}
		}
	}
	public static LinkedList<Game> getGameList() {
		return getGameList(0);
	}
	/**
	 * bekommt ein request.getParameter("...") uebergeben und gibt eine
	 * List<GameEntry> zurueck.
	 * 
	 * @return
	 */
	public static LinkedList<Game> getGameList(int librarySpecifier) {
		String myQuery = "SELECT gameID, name, thumbnailLink FROM games";
		switch(librarySpecifier){ 
		case 1: myQuery+=" WHERE steamID IS NOT NULL"; break;
        case 2: myQuery+=" WHERE oculusID IS NOT NULL"; break;
        case -1: myQuery+=" WHERE steamID IS NULL"; break;
        case -2: myQuery+=" WHERE oculusID IS NULL"; break;
		}
		LinkedList<Game> gameList = new LinkedList<>();

		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
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
	
	public static LinkedList<TagCategory> getTagList() {
		String myQuery = "SELECT catID, labelDE, labelEN FROM tagCats";
		LinkedList<TagCategory> tagCats = new LinkedList<>();
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
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
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, ID);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return g;
	}

	private static LinkedList<Tag> getGameTagsByID(String gameID) {
		String myQuery = "SELECT tagID FROM gametags WHERE gameID = ?";
		LinkedList<Tag> taglist = new LinkedList<>();
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, gameID);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next()) {
				taglist.add(new Tag(rs.getString("tagID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taglist;
	}
	
	public static LinkedList<Tag> getAllTagsOfCat(String catID){
		String myQuery = "SELECT tagID, catID, labelDE, labelEN FROM tags WHERE catID = ?";
		LinkedList<Tag> taglist = new LinkedList<>();
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, catID);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
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
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
    		if(rs.next()) {
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
	
	public static Tag getTagByLabelEN(String labelEN) {
		String myQuery = "SELECT * FROM tags WHERE  labelEN = ?";
		try (PreparedStatement pstmt = MariaDBConnection_connect().prepareStatement(myQuery)) {			
			pstmt.setString(1, labelEN);
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

	
	//angepasst mit checkSteamID
	public static void addGame(Game g) {
		String myQuery = "INSERT INTO GAMES(gameID, name, thumbnailLink, screenshotLink, steamID, oculusID, germanDescription, englishDescription, path, lastTimeUsed)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		if(checkSteamID(g.getSteamID())) {
			try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
				int c=1;
				stmt.setString(c++, g.getGameID());
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
				MySQLConnection_close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (!(g.getTaglist() == null)) {
				for (int i = 0; i < g.getTaglist().size(); i++) {
					addGameTagByID(g.getGameID(), g.getTaglist().get(i).getTagID());
				}
			}
		}else {
			System.out.println("Spiel ist schon in DB.");
		}
	}

	//returns true wenn nicht schon in der Datenbank
	private static boolean checkSteamID(String steamID) {
		String myQuery = "SELECT steamID FROM games WHERE steamID=?";
		
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, steamID);
			ResultSet rs = stmt.executeQuery();
			MySQLConnection_close(conn);
			while (rs.next()) {
				if(rs.getString("steamID").equals(steamID)) {
					return false;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}


	
	private static void addGameTagByID(String gameID, String tagID) {
		String myQuery = "INSERT INTO gametags(gameID, tagID) values(?,?)";
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, gameID);
			stmt.setString(2, tagID);
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void addTagCategory(TagCategory tagCategory) {
		String myQuery = "INSERT INTO tagCats(catID, labelDE, labelEN) values(?,?,?)";
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			int c=1;
			stmt.setString(c++, tagCategory.getCatID());
			stmt.setString(c++, tagCategory.getLabelDE());
			stmt.setString(c, tagCategory.getLabelEN());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		}catch(SQLException e) {
		}
	}
	
	public static void addTag(Tag t) {
		String myQuery = "INSERT INTO tags(catID, labelDE, labelEN) values(?,?,?)";
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			int c = 1;
			stmt.setString(c++, t.getCatID());
			stmt.setString(c++, t.getLabelDE());
			stmt.setString(c, t.getLabelEN());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateTag(Tag t) {
		String myQuery="UPDATE tags SET catID=?,labelDE=?,labelEN=? WHERE tagID=?";
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			int c=1;
			stmt.setString(c++, t.getCatID());
			stmt.setString(c++, t.getLabelDE());
			stmt.setString(c++, t.getLabelEN());
			stmt.setString(c++, t.getTagID());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteGame(Game g) {//Anm.: ON DELETE CASCADE loescht Eintraege in gametags-Tabelle automatisch mit.
		String myQuery = "DELETE FROM games WHERE gameID = ?";
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, g.getGameID());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteTag(Tag t) {//Anm.: ON DELETE CASCADE loescht Eintraege in gametags-Tabelle automatisch mit.
		String myQuery="DELETE FROM tags WHERE tagID = ?";
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, t.getTagID());
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private static void addCustom(String myQuery) {
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Passwort Methoden
	//Hashed und salted das passwort und trägt alles in eine DB ein
	//Beispiel salts: "*K&Jji" "rt%sH" "wwwQ*&%"
	public static void writePassword(String username, String originalPasswort, String salt){
		String myQuery = "INSERT INTO login(username, password, salt) values(?, ?, ?)";
		String passwort = DigestUtils.sha256Hex(originalPasswort + salt);
		try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, username);
			stmt.setString(2, passwort);
			stmt.setString(3, salt);
			stmt.executeUpdate();
			MySQLConnection_close(conn);
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

    	//nimmt username und passwort entgegen und guckt ob es richtig ist
	public static boolean verifyLogin(String username, String enteredPassword){
	    String myQuery = "SELECT salt, password FROM login WHERE username = ?";
	    String salt = "";
	    String password = "";
	    try (Connection conn=MariaDBConnection_connect();PreparedStatement stmt = conn.prepareStatement(myQuery)) {
			stmt.setString(1, username);
	        ResultSet rs = stmt.executeQuery();
	        MySQLConnection_close(conn);
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
	
	private static Boolean testIntegrity() {	//TODO test
		LinkedList<Tag> taglist = new LinkedList<>();
		taglist.add(new Tag("9999", "9998", "testLabelDE", "testLabelEN"));
//		TagCategory tagCategory = new TagCategory("9998", "testCatDE", "testCatEN", taglist);
		Game g = new Game("9997", "testGame", "https://openclipart.org/image/300px/svg_to_png/281016/monoscopio.png", taglist);

//		addTagCategory(tagCategory);
//		System.out.println("TagCategory is added.");
//		addTag(taglist.getLast());
		addGame(g);
//		deleteGame(g);
		return false;
	}

	/**
	 * Mainmethode zum Datenbanksetup:
	 */
	public static void main(String[] args) throws SQLException {
		testIntegrity();

//		writePassword("admin", "0000", "allahuakbar");
//		System.out.println(verifyLogin("admin", "0000"));
//		System.out.println(verifyLogin("admin", "0001"));
		
//		ArrayList<TagCategory> tagList = DBUtil.getTagList();
//		System.out.println("hi");
//		writePassword("admin", "passwort", "salt");
//		try (Connection con = MariaDBConnection_connect()) {
//			MySQLConnection_close(con);
//		}
//		addCustom("INSERT INTO tagCats (catID,labelDE, labelEN)\r\n" + "VALUES\r\n"
//				+ "	('1','VR-Brille','VR-System'),\r\n" + "	('2','Alter','Age'),\r\n" + "	('3','Genre','Genre'),\r\n"
//				+ "	('5','Sprache','Language'),\r\n"
//				+ "	('6','Spiell&auml;nge','Time'),\r\n" + "	('7','Bewegung','Movement')\r\n" + ";");
//		addCustom("INSERT INTO tags (tagID, catID, labelDE, labelEN)\r\n" + "VALUES\r\n"
//				+ "	('1','1','HTC Vive Pro','HTC Vive Pro'),\r\n" + "	('2','1','Oculus Go','Oculus Go'),\r\n"
//				+ "	('3','2','unter 12 Jahre','under 12'),\r\n" + "	('4','2','12 - 16 J.','12 - 16 years'),\r\n"
//				+ "	('5','2','16 und &auml;lter','16 and older'),\r\n" + "	('6','3','Film','Movies'),\r\n"
//				+ "	('7','3','Wissen','Knowledge'),\r\n" + "	('8','3','Medizin','Medicine'),\r\n"
//				+ "	('9','3','Minispiele','Mini Games'),\r\n" + "	('10','3','Abenteuer','Adventure'),\r\n"
//				+ "	('11','3','Simulation','Simulation'),\r\n" + "	('12','3','Geschicklichkeit','Dexterity'),\r\n"
//				+ "	('13','3','Strategie','Strategy'),\r\n" + "	('14','3','Action','Action'),\r\n"
//				+ "	('15','3','Entspannung','Relaxation'),\r\n"
//				+ "	('19','5','Deutsch','German'),\r\n" + "	('20','5','Englisch','English'),\r\n"
//				+ "	('21','5','Andere','Other'),\r\n"
//				+ "	('22','6','kurz (unter 30 Minuten)','short (less than 30 min.)'),\r\n"
//				+ "	('23','6','lang (&uuml;ber 30 Minuten)','long (more than 30 min.)'),\r\n"
//				+ "	('24','7','liegend','lying'),\r\n" + "	('25','7','stehend','standing'),\r\n"
//				+ "	('26','7','sitzend','seated'),\r\n" + "	('27','7','interaktiv','interactive'),\r\n"
//				+ "	('28','7','raumf&uuml;llend','Room-Scale'),\r\n" + "	('29','7','passiv','passive')\r\n" + ";");
	}
}
