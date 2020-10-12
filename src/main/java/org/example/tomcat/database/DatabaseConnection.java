/**
 * Copyright 2020 JFC
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.example.tomcat.database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection; 
import java.sql.SQLException; 
import java.sql.ResultSet; 
import java.sql.Statement; 

// This class can be used to initialize the database connection 
public class DatabaseConnection { 
	protected static Connection initializeDatabase() 
		throws SQLException, ClassNotFoundException 
	{ 
		// Read the connection from the web.xml Resource.
		DataSource data;
	        try {
			Context initialContext = new InitialContext();
			Context envContext = (Context) initialContext.lookup("java:comp/env");
			data = (DataSource) envContext.lookup("jdbc/TestDB");
		} catch (Exception e) {
			throw new SQLException("Could not look up datasource", e);
		}
		Connection con = data.getConnection();

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
