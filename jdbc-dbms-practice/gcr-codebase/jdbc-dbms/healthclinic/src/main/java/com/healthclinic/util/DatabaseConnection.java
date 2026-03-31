package com.healthclinic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {

	//database credentials
	private static final String URL = "jdbc:mysql://localhost:3306/health_clinic";
	private static final String USER = "root";
	private static final String PASSWORD = "dkr@1688";

	//jdbc connection setup
	public static Connection getConnection() {
		Connection connection=null;
		try {
			//load MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			//establishing connection
			connection =DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Database connected successfully.");

		} catch (ClassNotFoundException e) {
			System.err.println("MySQL JDBC driver not found.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Connection failed.");
			e.printStackTrace();
		}
		return connection;
	}

	public static void main(String[] args) {
		Connection connection =getConnection();
		if (connection!=null) {
			try {
				connection.close();
				System.out.println("Connection closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}