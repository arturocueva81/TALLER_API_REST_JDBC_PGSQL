package com.krakedev.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Conexion {
	//parte 6 Clase utilitaria de conexion
	private static final Logger Log = LogManager.getLogger(Conexion.class);
	private static final String URL ="jdbc:postgresql://localhost:5432/apijdbc";
	private static final String USER ="postgres";
	private static final String PASSWORD ="admin1810";
	
	public static Connection getConnection() {
		try {
			Connection con = DriverManager.getConnection(URL,USER, PASSWORD);
			Log.info("Conexion exitosa");
			return con;
		} catch (SQLException e) {
			Log.error("\nError de conexión: " + e.getMessage());
			throw new RuntimeException("No se pudo conectar a PostgreSQL", e);
		}
	}
	
	// Prueba de conexion
		public static void main(String[] args) {
			try {
		        Connection c = getConnection();
		        System.out.println("\nOK: ");
		        c.close();
		    } catch (Exception e) {
		        System.out.println("\nERROR: " + e.getMessage());
		    }
		}
}