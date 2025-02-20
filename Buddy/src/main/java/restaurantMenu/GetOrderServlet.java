package restaurantMenu;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
@WebServlet("/GetOrderServlet")
public class GetOrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/buddydb", "postgres", "root")) {
            String query = "SELECT * FROM orders WHERE order_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, orderId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String customerName = rs.getString("customer_name");
                        String foodItem = rs.getString("food_item");
                        int quantity = rs.getInt("quantity");
                        double totalPrice = rs.getDouble("total_price");

                        request.setAttribute("customerName", customerName);
                        request.setAttribute("foodItem", foodItem);
                        request.setAttribute("quantity", quantity);
                        request.setAttribute("totalPrice", totalPrice);

                        RequestDispatcher dispatcher = request.getRequestDispatcher("/orderDetails.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        response.getWriter().write("Order not found!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

