/**
 * Copyright 2020 JFC
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.example.tomcat.database;

import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.Statement; 

@WebServlet(description = "Demo for database in the cloud", urlPatterns = { "/demo" })
public class TestServlet extends HttpServlet {
    private static final String TEMPLATESTART = "{\n" +
            "  \"server\": \"%s\",\n" +
            "  \"hostname\": \"%s\",\n" +
            "  \"entries\": [\n";
    private static final String TEMPLATEEND = "" +
            "               ]\n" +
            "}\n";
    private static final String TEMPLATEREC = "" +
            "               { \"id\": \"%d\",\n" +
            "                 \"value\": \"%s\"\n" +
            "               }\n";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        String server = InetAddress.getLocalHost().getHostAddress();
        String hostname = InetAddress.getLocalHost().getHostName();

        PrintWriter out = response.getWriter();
        out.println(String.format(TEMPLATESTART, server, hostname));

	try {
            Connection con = DatabaseConnection.initializeDatabase(); 
            Statement statement = con.createStatement();
            ResultSet rset = statement.executeQuery("SELECT id, summary FROM demo");
            Boolean isfirst = true;
            while (rset.next()) {
                if (!isfirst)
                    out.println(",");
                out.println(String.format(TEMPLATEREC, rset.getLong(1), rset.getString(2)));
                isfirst = false;
            }
            rset.close();
            statement.close();
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        out.println(String.format(TEMPLATEEND));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try { 
  
            // Initialize the database 
            Connection con = DatabaseConnection.initializeDatabase(); 
  
            // Create a SQL query to insert data into demo table 
            // demo table consists of two columns, so two '?' is used 
            PreparedStatement st = con 
                   .prepareStatement("insert into demo values(?, ?)"); 
  
            // For the first parameter, 
            // get the data using request object 
            // sets the data to st pointer 
            st.setInt(1, Integer.valueOf(request.getParameter("id"))); 
  
            // Same for second parameter 
            st.setString(2, request.getParameter("string")); 
  
            // Execute the insert command using executeUpdate() 
            // to make changes in database 
            st.executeUpdate(); 
  
            // Close all the connections 
            st.close(); 
            con.close(); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
       doGet(request, response);
    }
}
