package com.dataMiningAlgo.jdbc;
import java.sql.*;
//import java.sql.Connection;

public class JDBCConnection {

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/lahman2016";
		String name = "root";
		String pass = "";
		Connection con = DriverManager.getConnection(url, name, pass);
		return con;
	}
}
