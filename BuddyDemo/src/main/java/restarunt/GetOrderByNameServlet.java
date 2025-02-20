package restarunt;

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
@WebServlet("/getOrderByName")
public class GetOrderByNameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerName = request.getParameter("customerName");

        // Retrieve order data from the database
        List<OrderItem> orderData = getOrderFromDatabase(customerName);

        // Respond with JSON
        response.setContentType("application/json");
        if (orderData != null && !orderData.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(new OrderResponse(customerName, orderData));
            response.getWriter().write(jsonResponse);
        } else {
            response.getWriter().write("{\"message\": \"No order found\"}");
        }
    }

    private List<OrderItem> getOrderFromDatabase(String customerName) {
        List<OrderItem> orderItems = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/buddydb", "postgres", "root")) {
            String query = "SELECT oi.food_item, oi.quantity FROM orders o " +
                           "JOIN order_items oi ON o.order_id = oi.order_id " +
                           "WHERE o.customer_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, customerName);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String foodItem = rs.getString("food_item");
                    int quantity = rs.getInt("quantity");
                    orderItems.add(new OrderItem(foodItem, quantity));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
}


