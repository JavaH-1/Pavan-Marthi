package com.Retail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ClearCartServlet")
public class ClearCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ID = 1; // TODO: Replace with session user ID

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM cart WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            int rowsAffected = stmt.executeUpdate();

            response.getWriter().write(rowsAffected > 0 ? "Cart cleared successfully!" : "Cart is already empty.");
        } catch (Exception e) {
            response.getWriter().write("Error clearing cart.");
        }
    }
}
