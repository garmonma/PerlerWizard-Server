package com.nni.gamevate.network.gamedata.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
	
	private Connection conn;
	
	public DatabaseConnection(){
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = null;
	}
	
	public void start(){
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","jan@2017");
		props.setProperty("ssl","false");

		String url = "jdbc:postgresql://localhost/PerlerWizard?user=postgres&password=jan@2017&ssl=false";
		
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		if(conn == null){
			throw new NullPointerException("The Database Connection is null!");
		}
		
		return conn;
	}
}
