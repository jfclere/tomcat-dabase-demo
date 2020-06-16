/**
 * Copyright 2020 JFC
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.example.tomcat.database;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

@WebServlet(description = "Demo for database in the cloud", urlPatterns = { "/demo" })
public class TestServlet extends HttpServlet {
    private static final String TEMPLATE = "{\n" +
            "  \"server\": \"%s\",\n" +
            "  \"hostname\": \"%s\"\n" +
            "}";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        String server = InetAddress.getLocalHost().getHostAddress();
        String hostname = InetAddress.getLocalHost().getHostName();

        try (PrintWriter out = response.getWriter()) {
            out.println(String.format(TEMPLATE, server, hostname));
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       doGet(request, response);
    }
}
