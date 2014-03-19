package test2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class MySQL {
	public static void connection(){
		try { //establish connection with mySQL driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void connectionToMySQL(){
		connection();
		String host = "jdbc:mysql://localhost/test";
		String username = "root";
		String password = "uukraine";
		try {
			Connection connect = DriverManager.getConnection(host, username, password);
			java.sql.Statement s = connect.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM eminem");
			while(r.next()){
				System.out.println(r.getBigDecimal("score", 2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void insertSongName(String songName){
		
	}
	public static void main(String args[]){
		connectionToMySQL();
	}
}
