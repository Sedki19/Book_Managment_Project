package JDBC.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
	private static Connection conn = null;
	 
	 public static Connection getcon() {
			if (conn == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				}
				catch(ClassNotFoundException ex) {
				System.out.println("Problème de chargement du Driver!");
				System.exit(1);
				}
			

			try {
			 conn = DriverManager.getConnection("jdbc:mysql://localhost/gestionnaire de livres", "root","");

			}
			catch (SQLException e) {
			System.err.println("Error opening SQL connection:"+ e.getMessage());
			}
			}
			return conn;
		}
}
