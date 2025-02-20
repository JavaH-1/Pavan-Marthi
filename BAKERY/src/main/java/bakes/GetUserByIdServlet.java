package bakes;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/getUserById")
public class GetUserByIdServlet extends HttpServlet {

    // Handle GET request to fetch user details by ID
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        
        // Database connection
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Connect to PostgreSQL
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pavan", "postgres", "root");

            // SQL query to fetch user details by ID
            String sql = "SELECT * FROM bakes WHERE id = ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));

            rs = stmt.executeQuery();
            
            // If a user is found, redirect to the HTML page with user data as URL parameters
            if (rs.next()) {
                String userId = String.valueOf(rs.getInt("id"));
                String userName = rs.getString("name");
                String userEmail = rs.getString("email");
                String userPhone = rs.getString("phone");

                // Redirect to the index.html page with the user details in the URL
                response.sendRedirect("index.html?id=" + userId + "&name=" + userName + "&email=" + userEmail + "&phone=" + userPhone);
            } else {
                // If no user is found, redirect with an error message
                response.sendRedirect("index.html?error=User not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html?error=Error fetching data.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
