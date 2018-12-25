package util;

import java.io.PrintWriter;
import java.sql.*;

public class DBUtil {

	static Connection conn = null;
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
