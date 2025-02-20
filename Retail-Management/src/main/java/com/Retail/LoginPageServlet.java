package com.Retail;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginPageServlet")
public class LoginPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

     
        String email = request.getParameter("email");
        String password = request.getParameter("password");

     
        PrintWriter out = response.getWriter();
        String jsonResponse = "";

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/RetailStore", "postgres", "root")) {
            
            String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
            	 int ID = rs.getInt("ID");

                 // Create session and store user ID
                 HttpSession session = request.getSession();
                 session.setAttribute("ID", ID);
                 session.setMaxInactiveInterval(30 * 60);
               
                jsonResponse = "{\"status\": \"success\", \"message\": \"Login successful\"}";
            } else {
              
                jsonResponse = "{\"status\": \"error\", \"message\": \"Incorrect email or password\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            jsonResponse = "{\"status\": \"error\", \"message\": \"Internal Server Error\"}";
        }

      
        out.print(jsonResponse);
        out.flush();
    }
}