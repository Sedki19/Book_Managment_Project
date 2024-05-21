package Application.test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import JDBC.Connection.Conn;

public class Test {
	
	public static void main(String args[]) throws SQLException {
		PreparedStatement ps =Conn.getcon().prepareStatement("Select * from user where userName = ? and password = ? ");

	}
}
