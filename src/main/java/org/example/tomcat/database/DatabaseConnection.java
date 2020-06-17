/**
 * Copyright 2020 JFC
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.example.tomcat.database;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.sql.ResultSet; 
import java.sql.Statement; 

// This class can be used to initialize the database connection 
public class DatabaseConnection { 
	protected static Connection initializeDatabase() 
		throws SQLException, ClassNotFoundException 
	{ 
		// Initialize all the information regarding 
		// Database Connection 
		String dbURL = "jdbc:postgresql://localhost/";
		// Database name to access 
		String dbName = "test"; 
		String dbUsername = "tomcat"; 
		String dbPassword = "tomcat"; 

                /* The class for the Driver needs to be loaded before connecting */
                String dbDriver = "org.postgresql.Driver";
                Class.forName(dbDriver);

		Connection con = DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword);

		/* Check for the table and create it if needed */
		ResultSet rset = con.getMetaData().getTables(null, null, "demo", null);
                if (!rset.next()) {
			Statement statement = con.createStatement();
			statement.executeUpdate("CREATE TABLE demo (id bigint, summary VARCHAR(255))");
			statement.close();
                }
		rset.close();
		return con; 
	} 
} 
