package com.Retail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/RemoveCartItemServlet")
public class RemoveCartItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cartID = Integer.parseInt(request.getParameter("cartID"));
        int ID = 1; // TODO: Replace with session user ID

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM cart WHERE CartID = ? AND UserID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartID);
            stmt.setInt(2, ID);
            int rowsAffected = stmt.executeUpdate();

            response.getWriter().write(rowsAffected > 0 ? "Item removed successfully!" : "Item not found!");
        } catch (Exception e) {
            response.getWriter().write("Error removing item.");
        }
    }
}
