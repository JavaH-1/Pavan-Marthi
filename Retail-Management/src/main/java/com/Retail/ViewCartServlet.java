package com.Retail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
@WebServlet("/ViewCartServlet")
public class ViewCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ID = -1; // Default invalid ID
        List<CartItem> cartItems = new ArrayList<>();

        try {
            // Get UserID from Session
            HttpSession session = request.getSession(false);
            if (session != null) {
                System.out.println("Session found!");

                // Debug: Print all session attributes
                java.util.Enumeration<String> attributes = session.getAttributeNames();
                while (attributes.hasMoreElements()) {
                    String attributeName = attributes.nextElement();
                    System.out.println("Session Attribute: " + attributeName + " = " + session.getAttribute(attributeName));
                }

                Object idObj = session.getAttribute("ID");
                if (idObj != null) {
                    try {
                        ID = Integer.parseInt(idObj.toString()); // Safe conversion
                    } catch (NumberFormatException e) {
                        response.getWriter().write("Invalid User ID format.");
                        return;
                    }
                } else {
                    response.getWriter().write("User not logged in.");
                    return;
                }
            } else {
                response.getWriter().write("Session does not exist.");
                return;
            }

            // Load PostgreSQL Driver
            Class.forName("org.postgresql.Driver");

            // Connect to PostgreSQL Database
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/RetailStore", "postgres", "root");

            // Query to fetch cart items
            String sql = "SELECT c.CartID, p.ProductName, p.Price, c.Quantity " +
                    "FROM cart c " +
                    "JOIN ProductList p ON c.ProductID = p.ProductID " +
                    "WHERE c.ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ID);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartID(rs.getInt("CartID"));
                item.setProductName(rs.getString("ProductName"));
                item.setPrice(rs.getDouble("Price"));
                item.setQuantity(rs.getInt("Quantity"));
                cartItems.add(item);
            }

            // Close resources
            rs.close();
            pstmt.close();
            conn.close();

            // Convert to JSON and send response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(cartItems));

        } catch (Exception e) {
            e.printStackTrace(); // Debugging purpose
            response.getWriter().write("Error fetching cart items: " + e.getMessage());
        }
    }
}
